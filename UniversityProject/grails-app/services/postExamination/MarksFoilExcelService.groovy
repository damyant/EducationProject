package postExamination

import grails.transaction.Transactional
import jxl.CellView
import jxl.format.Colour
import jxl.format.UnderlineStyle
import jxl.write.Alignment
import jxl.write.Label
import jxl.write.WritableCell
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException

@Transactional
class MarksFoilExcelService {


    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat times1;
    private WritableCellFormat times2;
    def serviceMethod() {

    }

    // Start From Here
    Boolean excelReport(params, finalList, course, sheetNo, WritableWorkbook workbook,currentYear,semesterNo){
        println("creating this sheet "+ sheetNo)
        WritableSheet sheet= null
        WritableSheet excelSheet=null
        sheet = workbook.createSheet(""+course, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        println("after excel sheet...")
        createLabel(excelSheet, course, currentYear,semesterNo);
        createContent(excelSheet, finalList);
//       workbook.write();
//      workbook.close();
        println("Coming out from excelReports Action..")
        return true

    }

    private void createLabel(WritableSheet sheet, course, currentYear,semesterNo)
            throws WriteException {

        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        times2 = new WritableCellFormat(times10pt);
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        times2.setAlignment(Alignment.LEFT)

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        WritableFont times10ptBoldUnderline1 = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        times2 = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        times2.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);

        cv.setFormat(timesBoldUnderline);
        for (int i=0;i< 6;i++){
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }

        cv.setAutosize(true);
        int row = 0
        int cols = 1
        WritableCell titleCell = new Label(0, row, "GAUHATI UNIVERSITY ");
        WritableCell titleCell1 = new Label(0, 1, "IDOL (Semester) Examination, "+currentYear);
        WritableCell titleCell2 = new Label(0, 2, "Subject: "+course+"                    Semester: "+semesterNo);
        WritableCell titleCell3 = new Label(0, 3, "Paper: "+course);
        WritableCell titleCell4 = new Label(0, 4, "1st Half/2nd Half                      Total Marks:..........");
        titleCell.setCellFormat(timesBoldUnderline)
        titleCell1.setCellFormat(times1)
        titleCell2.setCellFormat(times2)
        titleCell3.setCellFormat(times2)
        titleCell4.setCellFormat(times1)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        sheet.addCell(titleCell1);
        sheet.mergeCells(0, 1, cols, 1);
        sheet.addCell(titleCell2);
        sheet.mergeCells(0, 2, cols, 2);
        sheet.addCell(titleCell3);
        sheet.mergeCells(0, 3, cols, 3);
        sheet.addCell(titleCell4);
        sheet.mergeCells(0, 4, cols, 4);

        int row1 = 0
        int cols1 = 4
        WritableCell nextTitleCell = new Label(3, row1, "GAUHATI UNIVERSITY ");
        WritableCell nextTitleCell1 = new Label(3, 1, "IDOL (Semester) Examination, "+currentYear);
        WritableCell nextTitleCell2 = new Label(3, 2, "Subject: "+course+"                    Semester: "+semesterNo);
        WritableCell nextTitleCell3 = new Label(3, 3, "Paper: "+course);
        WritableCell nextTitleCell4 = new Label(3, 4, "1st Half/2nd Half                      Total Marks:..........");
        nextTitleCell.setCellFormat(timesBoldUnderline)
        nextTitleCell1.setCellFormat(times1)
        nextTitleCell2.setCellFormat(times2)
        nextTitleCell3.setCellFormat(times2)
        nextTitleCell4.setCellFormat(times1)
        sheet.addCell(nextTitleCell);
        sheet.mergeCells(3, row1, cols1, row1);
        sheet.addCell(nextTitleCell1);
        sheet.mergeCells(3, 1, cols1, 1);
        sheet.addCell(nextTitleCell2);
        sheet.mergeCells(3, 2, cols1, 2);
        sheet.addCell(nextTitleCell3);
        sheet.mergeCells(3, 3, cols1, 3);
        sheet.addCell(nextTitleCell4);
        sheet.mergeCells(3, 4, cols1, 4);

        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);

    }




    void createContent(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
       // Write a few number
        def counter=0
        for (int i = 0; i < finalList.size(); i++) {
            ++counter;
            addLabel(sheet, 0, i + 5, finalList[i].toString());
            addLabel(sheet, 3, i + 5, finalList[i].toString());
        }
        addCaption(sheet,counter)

    }


    void addCaption(WritableSheet sheet,counter)
            throws RowsExceededException, WriteException {

        addLabel(sheet, 0, counter + 7, "Examined by:");
        addLabel(sheet, 0, counter + 8, "Scrutinised by:");
        addLabel(sheet, 0, counter + 9, "Head examiner's signature:");

        addLabel(sheet, 3, counter + 7, "Examined by:");
        addLabel(sheet, 3, counter + 8, "Scrutinised by:");
        addLabel(sheet, 3, counter + 9, "Head examiner's signature:");


    }



    void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);

    }


    //End Services
}
