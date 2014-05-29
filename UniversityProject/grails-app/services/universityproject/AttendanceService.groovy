package universityproject

import examinationproject.CourseSubject
import examinationproject.ExaminationVenue
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings
import jxl.format.Colour;
import jxl.format.UnderlineStyle
import jxl.write.Alignment
import jxl.write.Label;
import jxl.write.Number
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException

import java.text.SimpleDateFormat;

@Transactional
class AttendanceService {
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat times1;

    Boolean getStudentList(params, excelPath) {
        File file
        boolean status= false
        try{

         file = new File(excelPath);
        }catch(Exception e){
            e.printStackTrace()
        }
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        def  sheetNo=0
        def examinationVenueIns = ExaminationVenue.findById(Long.parseLong(params.examinationCentre))
        if(params.programList=='allProgram' && params.programSession=='allSession' && params.programTerm=='allSemester'){
            def programExamVenue = ProgramExamVenue.findAllByExamVenue(examinationVenueIns)
            def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
               for(int i=1;i<=course[0]; i++){
                   programExamVenue.each{
                        def pid= it.courseDetail.id
                        def session=ProgramSession.findAllByProgramDetailId(it.courseDetail)
//                        println("---------------------------------------------"+session)
                         session.each{
                            def sessionId=it.id
//                             println("-------------------------"+sessionId)
                            def obj = Student.createCriteria()
                            def studentList = obj.list {
                            programDetail {
                                eq('id', pid)
                            }
                            examinationVenue {
                                eq('id', examinationVenueIns.id)
                            }
                            and {
                                eq('programSession', ProgramSession.findById(sessionId))
                            }
                            and {
                                eq('status', Status.findById(4))
                            }
                            and {
                                    eq('semester', i)
                            }
                        }
                        println("this is the semester "+i+" this is the course "+pid+' this is the session '+ sessionId+' student list is '+studentList)
//                        def examinationCentre = ExaminationVenue.findById(Long.parseLong(params.examinationCentre))
                        def programDetail = ProgramDetail.findById(pid)
                             def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findById(sessionId), i)
                        def courseSubject = CourseSubject.findAllByCourseDetailAndSemester(programDetail, semester)
                        if(studentList){
                        status = writeAttendanceSheet(studentList, params, semester, courseSubject, examinationVenueIns, workbook, sheetNo)
                        sheetNo= sheetNo+1;
                        }

                    }
                }

            }
            if(sheetNo>0){
            workbook.write();
            workbook.close();
            }

            return status

        }
        else if(params.programList=='allProgram' && params.programSession!='allSession' && params.programTerm=='allSemester'){
            def program = ProgramExamVenue.findAllByExamVenue(examinationVenueIns)
            def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
//            def sessions= ProgramSession.executeQuery( "select distinct  programSession.sessionOfProgram from ProgramSession programSession" );
            program.each{
                def pid= it.courseDetail.id
                println("-----------------------------------********"+ pid)
                for(int i=1;i<=course[0]; i++){
                        def session=it
                        def obj = Student.createCriteria()
                        def studentList = obj.list {
                            programDetail {
                                eq('id', pid)
                            }
                            examinationVenue {
                                eq('id', examinationVenueIns.id)
                            }
                            and {
                                eq('programSession', ProgramSession.findBySessionOfProgram(params.programSession))
                            }

                            and {
                                eq('status', Status.findById(4))
                            }
                            and {
                                eq('semester', i)
                            }
                        }
//                        def examinationCentre = ExaminationVenue.findById(Long.parseLong(params.examinationCentre))
                        def programDetail = ProgramDetail.findById(pid)
                        def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findBySessionOfProgram(params.programSession), i)
                        def courseSubject = CourseSubject.findAllByCourseDetailAndSemester(programDetail, semester)
                        if(studentList){
                            status = writeAttendanceSheet(studentList, params, semester, courseSubject, examinationVenueIns, workbook, sheetNo)
                            sheetNo= sheetNo+1;
                        }
                }
            }
            if(sheetNo>0){
                workbook.write();
                workbook.close();
            }
            return status
        }

        else if(params.programList=='allProgram' && params.programSession=='allSession' && params.programTerm!='allSemester'){
            def program = ProgramExamVenue.findAllByExamVenue(examinationVenueIns)
//            def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
            program.each{
                def pid= it.courseDetail.id
                def sessions =ProgramSession.findAllById(pid)
                println("-----------------------------------********"+ pid)
                sessions.each{
                    def sessionId=it.id
                    def obj = Student.createCriteria()
                    def studentList = obj.list {
                        programDetail {
                            eq('id', pid)
                        }
                        examinationVenue {
                            eq('id', examinationVenueIns.id)
                        }
                        and {
                            eq('programSession', ProgramSession.findById(sessionId))
                        }

                        and {
                            eq('status', Status.findById(4))
                        }
                        and {
                            eq('semester', Integer.parseInt(params.programTerm))
                        }
                    }
                    def programDetail = ProgramDetail.findById(pid)
                    def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findById(sessionId), Integer.parseInt(params.programTerm))
                    def courseSubject = CourseSubject.findAllByCourseDetailAndSemester(programDetail, semester)
                    if(studentList){
                        status = writeAttendanceSheet(studentList, params, semester, courseSubject, examinationVenueIns, workbook, sheetNo)
                        sheetNo= sheetNo+1;
                    }
                }
            }
            if(sheetNo>0){
                workbook.write();
                workbook.close();
            }
            return status
        }
        else if(params.programList=='allProgram' && params.programSession!='allSession' && params.programTerm!='allSemester'){
            def program = ProgramExamVenue.findAllByExamVenue(examinationVenueIns)
//            def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
            program.each{
                def pid= it.courseDetail.id
                println("-----------------------------------********"+ pid)
                    def obj = Student.createCriteria()
                    def studentList = obj.list {
                        programDetail {
                            eq('id', pid)
                        }
                        examinationVenue {
                            eq('id', examinationVenueIns.id)
                        }
                        and {
                            eq('programSession', ProgramSession.findBySessionOfProgram(params.programSession))
                        }

                        and {
                            eq('status', Status.findById(4))
                        }
                        and {
                            eq('semester', Integer.parseInt(params.programTerm))
                        }
                    }
                    def programDetail = ProgramDetail.findById(pid)
                    def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findBySessionOfProgram(params.programSession), Integer.parseInt(params.programTerm))
                    def courseSubject = CourseSubject.findAllByCourseDetailAndSemester(programDetail, semester)
                    if(studentList){
                        status = writeAttendanceSheet(studentList, params, semester, courseSubject, examinationVenueIns, workbook, sheetNo)
                        sheetNo= sheetNo+1;
                    }

            }
            if(sheetNo>0){
                workbook.write();
                workbook.close();
            }
            return status
        }

        else{
                println("************************************************88")
                def obj = Student.createCriteria()
                def studentList = obj.list {
                    programDetail {
                        eq('id', Long.parseLong(params.programList))
                    }
                    examinationVenue {
                        eq('id', examinationVenueIns.id)
                    }
                    and {
                        eq('programSession', ProgramSession.findById(Integer.parseInt(params.programSession)))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }

                    and {
                        eq('semester', Integer.parseInt(params.programTerm))
                    }
                }
            println("this is the semester "+params.programTerm+" this is the course "+params.programList+' this is the session '+ params.programSession+' student list is '+studentList)

//              def examinationCentre = ExaminationVenue.findById(Long.parseLong(params.examinationCentre))
                def programDetail = ProgramDetail.findById(Long.parseLong(params.programList))
                def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findById(Integer.parseInt(params.programSession)), Integer.parseInt(params.programTerm))
                def courseSubject = CourseSubject.findAllByCourseDetailAndSemester(programDetail, semester)
                status = writeAttendanceSheet(studentList, params, semester, courseSubject,  examinationVenueIns, workbook, sheetNo)
                workbook.write();
                workbook.close();
                return status
        }

    }

    boolean writeAttendanceSheet(studentList, params, semester, courseSubject,  ExaminationVenue examinationCentre, WritableWorkbook workbook, int sheetNo) {
        try {

            WritableSheet sheet = workbook.createSheet("Report", sheetNo);
            WritableSheet excelSheet = workbook.getSheet(sheetNo);
            createLabel(excelSheet, courseSubject, examinationCentre);
            createContent(excelSheet, studentList);

            return true
        }
        catch (Exception e) {

//            println("this is the exception " + e)
            return false
        }
        return false
    }


    void createLabel(WritableSheet sheet, courseSubject, examinationCentre)
            throws WriteException {
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
        int col = 2;
        int widthInChars = 20;
        sheet.setColumnView(col, widthInChars);
        col = 1;
        widthInChars = 25;
        sheet.setColumnView(col, widthInChars);
        col = 3;
        widthInChars = 12;
        sheet.setColumnView(col, widthInChars);
        col = courseSubject.size + 4;
        widthInChars = 20;
        sheet.setColumnView(col, widthInChars);
        cv.setAutosize(true);
        int row = 0
        int cols = courseSubject.size + 4
        WritableCell titleCell = new Label(0, row, "GAUHATI UNIVERSITY\n" +
                "M.A./M.Sc./M.Com./MCJ/M.Sc(IT)/MCA/BSc(IT)/BCA Previous, PG Diploma & Semester Examination - 2014, held in January 2014\n" +
                "under Institute of Distance and Open Learning, G.U.\n" +
                "ATTENDANCE REGISTER");
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        row = 1;
        titleCell = new Label(0, row, "Examination Centre: " + examinationCentre.name);
        titleCell.setCellFormat(times1)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);

        // Write a few headers
        addCaption(sheet, 0, 2, "SI NO");
        addCaption(sheet, 1, 2, "Register No With Year");
        addCaption(sheet, 2, 2, "Exam Roll No");
        addCaption(sheet, 3, 2, "Name");
        int i = 4;
        courseSubject.each {
            println("---------------------"+it.subject)
            String date = it.examDate.getDateString()
//            println("hello " + i + " date " + date.getClass())
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdfDestination.parse(date);
//            println("----------------" + date1)
            addCaption(sheet, i, 2, " " + date);
            i++;
        }
        addCaption(sheet, i, 2, 'Full/Back')
    }

    void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }


    void createContent(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addNumber(sheet, j, i+3, i+1);
            addNumber(sheet, j+1, i+3, finalList[i].registrationYear);
            addNumber(sheet, j+2, i+3, Integer.parseInt(finalList[i].rollNo));
            addLabel(sheet, j+3, i+3, finalList[i].firstName+' ');
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
