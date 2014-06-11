package universityproject

import grails.transaction.Transactional
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook

@Transactional
class MarksFoilExcelService {

    def serviceMethod() {

    }

    // Start From Here
    Boolean excelReport(params, finalList, course, sheetNo, WritableWorkbook workbook){
        println("creating this sheet "+ sheetNo)
        WritableSheet sheet= null
        WritableSheet excelSheet=null
        sheet = workbook.createSheet(""+course.courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        println("after excel sheet...")
        createLabel(excelSheet, params, course);
        createContent(excelSheet, finalList);
//       workbook.write();
//      workbook.close();
        println("Coming out from excelReports Action..")
        return true

    }

    //End Services
}
