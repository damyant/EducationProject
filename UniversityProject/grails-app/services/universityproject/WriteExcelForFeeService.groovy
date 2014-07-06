package universityproject

import grails.transaction.Transactional
import grails.transaction.Transactional
import jxl.format.Colour
import jxl.format.UnderlineStyle
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle
import jxl.write.Alignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException

import java.text.SimpleDateFormat;

@Transactional
class WriteExcelForFeeService {
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat times1;
    Boolean  excelReport(params, finalList, course, sheetNo, WritableWorkbook workbook, studyCentreName, session){
        def feeType = finalList[0].feeType.type
        WritableSheet sheet= null
        WritableSheet excelSheet=null
        String courseName= course.courseName
        if(courseName.contains('/')){
            courseName = courseName.replace('/', ' ')
        }
        sheet = workbook.createSheet(""+courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabel(excelSheet, params, course, studyCentreName, session,feeType);
        createContent(excelSheet, finalList);
//       workbook.write();
//      workbook.close();
        return true

    }


    private void createLabel(WritableSheet sheet, params, course, studyCentreName, session, feeType )
    throws WriteException {
//        println("calling this method")
        def formatSession = Integer.parseInt(session)+1
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i=0;i< 6;i++){
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }

        cv.setAutosize(true);
        int row = 0
        int cols = 6
        WritableCell titleCell = new Label(0, row, "Total Students In "+course.courseName +" For "+ session+"-"+formatSession +" Session In "+(studyCentreName? studyCentreName:'All Study Centres')+' Who\'s '+ feeType +" "+(params.value=='sessionProgramWiseFeePaid'? 'Is Paid': 'Is Unpaid'));
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);

        // Write a few headers
        addCaption(sheet, 0, 1, "Roll No ");
        addCaption(sheet, 1, 1, "Name ");
        addCaption(sheet, 2, 1, "Study Centre ");
        addCaption(sheet, 3, 1, "Examination Centre ");
        addCaption(sheet, 4, 1, "Challan No.");
        addCaption(sheet, 5, 1, "Mobile No.");
        addCaption(sheet, 6, 1, "Semester");
    }

    void addCaption(WritableSheet sheet, int column, int row, String s)
    throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }


    void createContent(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
//        println("now creating contents")
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addLabel(sheet, j, i + 2, (finalList[i].student.rollNo? finalList[i].student.rollNo:'Not Generated' ));
            addLabel(sheet, j + 1, i + 2, finalList[i].student.firstName+' '+(finalList[i].student.middleName? finalList[i].student.middleName:'' )+' '+finalList[i].student.lastName);
            addLabel(sheet, j + 2, i + 2, finalList[i].student.studyCentre[0].name);
            addLabel(sheet, j + 3, i + 2, finalList[i].student.city[0]?.cityName);
            addLabel(sheet, j + 4, i + 2, finalList[i].student.challanNo);
            addLabel(sheet, j + 5, i + 2, "91"+finalList[i].student.mobileNo);
            addLabel(sheet, j + 6, i + 2, ''+finalList[i].semesterValue);
        }

    }


    void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    void addLabel(WritableSheet sheet, int column, int row, String s)
    throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

}
