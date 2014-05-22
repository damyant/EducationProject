package universityproject

import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyCenter
import grails.transaction.Transactional
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.Workbook
import org.codehaus.groovy.classgen.genDgmMath

@Transactional
class ReportService {
    def writeExcelService
    def springSecurityService
    def getReportDataSession(params, excelPath) {
         def finalStudentMap = [:]
         def totalForSession=0
         def studyCenterId=0
         def currentUser=springSecurityService.getCurrentUser()
         println("-------------------------------"+currentUser)
         def programList = ProgramDetail.list(sort: 'courseCode')
         if(currentUser.studyCentreId){
             println("oh yes abhi------------->")
             def status
             File file = new File(excelPath);
             WorkbookSettings  wbSettings = new WorkbookSettings();
             wbSettings.setLocale(new Locale("en", "EN"));
             WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
             int sheetNo=0
            studyCenterId = currentUser.studyCentreId
            def studyCenter= StudyCenter.findById(studyCenterId)
            programList.each {
                def pId= it.id
                def stuObj= Student.createCriteria()
                def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    studyCentre{
                        eq('id' , studyCenter.id)
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.session))
                    }
                }
                status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook)
                sheetNo= sheetNo+1
            }
             workbook.write();
             workbook.close();
             return status
        }
        else{
             programList.each {
                  def pId= it.id
                  def stuObj= Student.createCriteria()
                  def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.session))
                    }
                    projections {
                        rowCount()
                    }
                }

             totalForSession+=count
             finalStudentMap.put(it.courseName, count.getAt(0))
            }
        }
        finalStudentMap.put("TOTAL STUDENTS", totalForSession)
        return finalStudentMap
    }


    def getReportDataSessions(startSession, endSession) {

        def categoryList =[]
        categoryList
        def totalByCategory=[]
        int j=0
        println("value of start session "+startSession)
        println("value of start session "+endSession)
        for(int i=startSession; i<=endSession;i++){
            println("adding on this index "+j)
            println("adding on this index "+i)
            categoryList.add(j, i)
            totalByCategory.add(j,0)
            j++
        }
        println("---------------------------------------------------------------"+ categoryList)
        println("---------------------------------------------------------------"+ totalByCategory)

        def finalStudentMap = [:]
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def sizeList=[]
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                and{
                    between('registrationYear' ,startSession, endSession)
                }
                projections{
                    groupProperty('registrationYear')
                    rowCount()
                }

            }
            for(int i=0; i<categoryList.size(); i++){
                if(count.size()){
                    boolean flag = false
                    for(j=0;j<count.size();j++){

                        if(count[j]?.getAt(0)==categoryList[i]){
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i]+count[j]?.getAt(1))
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                }
                else{
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        println('totalByCategory is '+ totalByCategory)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }

    Boolean getReportDataCourse(params, excelPath){
        def course = ProgramDetail.findById(Long.parseLong(params.course))
        def stuObj= Student.createCriteria()
        File file = new File(excelPath);
        WorkbookSettings  wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo=0
        def studentList = stuObj.list{
            programDetail{
                eq('id', Long.parseLong(params.course))
            }
            and{
                eq('registrationYear' , Integer.parseInt(params.courseSession))
            }
        }

        println('--------------------------'+studentList)
//        return studentList
       def status=  writeExcelService.excelReport(params, studentList, course, sheetNo, workbook)
        workbook.write();
        workbook.close();
        return status

    }


    def getReportDataStudyCentre(params, excelPath){
        def status
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseCode')
        if(params.inExcel){
            File file = new File(excelPath);
            WorkbookSettings  wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            int sheetNo=0
            programList.each {
                def pId= it.id
                def stuObj= Student.createCriteria()
                def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    studyCentre{
                        eq('id' , Long.parseLong(params.studyCentre))
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.studyCentreSession))
                    }
                }
                println("--------------"+count)
                 status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook)
                sheetNo= sheetNo+1
            }
          workbook.write();
          workbook.close();
            return status
        }
      else{
        programList.each {
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                studyCentre{
                    eq('id' , Long.parseLong(params.studyCentre))
                }
                and{
                    eq('registrationYear' , Integer.parseInt(params.studyCentreSession))
                }
                projections {
                    rowCount()
                }
            }

            totalForSession+=count
//            println('final student map is '+ finalStudentMap)
            finalStudentMap.put(it.courseName, count.getAt(0))
        }
        finalStudentMap.put("TOTAL STUDENTS", totalForSession)
        return finalStudentMap
      }

    }
    def getReportDataExaminationCentre(params, excelPath){
        def status
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseCode')
        if(params.inExcel){
            File file = new File(excelPath);
            WorkbookSettings  wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            int sheetNo=0
            programList.each {
                def pId= it.id
                def stuObj= Student.createCriteria()
                def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    examinationCentre{
                        eq('id' , Long.parseLong(params.examCity))
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.examinationCentreSession))
                    }
                }
                println("--------------"+count)
                status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook)
                sheetNo= sheetNo+1
            }
            workbook.write();
            workbook.close();
            return status
        }
        else{
            programList.each {
                def pId= it.id
                def stuObj= Student.createCriteria()
                def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    examinationCentre{
                        eq('id' , Long.parseLong(params.examCity))
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.examinationCentreSession))
                    }
                    projections {
                        rowCount()
                    }
                }

                totalForSession+=count
    //            println('final student map is '+ finalStudentMap)
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            return finalStudentMap
        }
    }

    def getReportDataCategory(params){
        def categoryList=['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        println("this function is called "+ params)
        def finalStudentMap = [:]
        def  totalByCategory= [0,0,0,0,0,0]
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def sizeList=[]
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                and{
                    eq('registrationYear' , 2014)
                }
                projections {
                    groupProperty("category")
                    rowCount()
                }
            }

            println('count is '+ count)
           for(int i=0; i<categoryList.size(); i++){
                if(count.size()){
                    boolean flag = false
                    for(int j=0;j<count.size();j++){

                        if(count[j]?.getAt(0)==categoryList[i]){
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i]+count[j]?.getAt(1))
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                }
                else{
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        println('totalByCategory is '+ totalByCategory)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }



    def getReportDataCategoryGender(params){
        println('params in service '+params)

        def categoryList=['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        def genderList=['Male', 'Female']
        println("this function is called "+ params)
        def finalStudentMap = [:]
        def  totalByCategoryGender= [0,0,0,0,0,0,0,0,0,0,0,0]
        def programList = ProgramDetail.list(sort: 'courseCode')

        programList.each {
            def sizeList=[]
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                and{
                    eq('registrationYear' , 2014)
                }
                projections {
                    groupProperty("category")
                    groupProperty("gender")
                    rowCount()
                }
            }
//            println("result of count is "+count)
            int l=0
            for(int i=0; i<categoryList.size(); i++){

                    for(int k=0; k< genderList.size() ;k++){
                          if(count.size()){
                              boolean flag = false
                                for(int j=0;j<count.size();j++){

                                    if(count[j]?.getAt(0)==categoryList[i] && count[j].getAt(1)==genderList[k]){
                                        sizeList.add(l, count[j]?.getAt(2))
//                                        println("match value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                                        totalByCategoryGender.set(l, totalByCategoryGender[l]+count[j]?.getAt(2))
                                        l=l+1
                                        flag=true;
                                        break;
                                    }
                                }
                                if(flag==false){
                                    sizeList.add(l, 0)
//                                    println("flag value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                                    l=l+1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                                }
                          }
                          else{
                              sizeList.add(l, 0)
//                              println("else value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                              l=l+1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                          }
//                     println("value of l is "+ l)
                    }
            }
            println("this is the sizeList "+ sizeList)
            finalStudentMap.put(it.courseName, sizeList)
        }
        println("total  "+ totalByCategoryGender)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategoryGender)
        return finalStudentMap
    }


    def getReportDataAdmissionApprovedUnapproved(params){
            def stuObj= Student.createCriteria()
           def studentList
        if(params.value=='admissionUnapproved'){
                studentList = stuObj.list{
                studyCentre{
                    eq('id' , Long.parseLong(params.admissionUnapprovedStudyCentre))
                }
                and{
                    eq('registrationYear' , Integer.parseInt(params.admissionUnapprovedSession))
                }
                and{
                    eq('status', Status.findById(2))
                }

                order('programDetail', 'asc')
            }
                   println("this is the result of query "+ studentList)
        }
       else if(params.value=='admissionApproved'){
                studentList = stuObj.list{
                studyCentre{
                    eq('id' , Long.parseLong(params.admissionApprovedStudyCentre))
                }
                and{
                    eq('registrationYear' , Integer.parseInt(params.admissionApprovedSession))
                }
                and{
                    eq('status', Status.findById(3))
                }

                order('programDetail', 'asc')
            }
            println("this is the result of query "+ studentList)
        }
        return studentList

    }


    def getReportDataAdmissionSelfRegistration(params){
        def stuObj= Student.createCriteria()
        def studentList = stuObj.list{
                eq('registrationYear' , Integer.parseInt(params.admissionSelfRegistrationSession))

             and{
                eq('status', Status.findById(1))
             }
            and{
              isNotNull('referenceNumber')
            }
        }

        println("this is the list--------------- "+studentList)

    }





    def getReportDataStudyCentreFeePaid(params){
       def finalMap =[]
       println("these are the parameters "+ params)
       def stuObj = Student.createCriteria()
       def studentList = stuObj.list{
           studyCentre{
               eq('id' , Long.parseLong(params.feePaidStudyCentre))
           }
           and{
               eq('registrationYear' , Integer.parseInt(params.studyCentreFeePaidSession))
           }
           and{
               eq('status', Status.findById(3))
           }
       }

        println("this is the list of students "+ studentList)
        studentList.each{
            println("----------"+it.studentName)
        }
        def feeType= FeeType.list(sort: 'type')
        feeType.each{
        def feeList = FeeDetails.findAllByStudentIdInListAndFeeTypeId(studentList, it)
            println("this is the list of fee paid "+ feeList)

           if(feeList){
               feeList.each {
                   finalMap.add(it)
               }
           }

        }

        println("this is the final map "+ finalMap)
        return finalMap
    }

    def getReportDataComparative(startSession, endSession){

        def categoryList =[]
        categoryList
        def totalByCategory=[]
        int j=0
        println("value of start session "+startSession)
        println("value of start session "+endSession)
        for(int i=startSession; i<=endSession;i++){
            println("adding on this index "+j)
            println("adding on this index "+i)
            categoryList.add(j, i)
            totalByCategory.add(j,0)
            j++
        }
        println("---------------------------------------------------------------"+ categoryList)
        println("---------------------------------------------------------------"+ totalByCategory)

        def finalStudentMap = [:]
        def programList = StudyCenter.list()
        programList.each {
            def sizeList=[]
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                studyCentre{
                    eq('id', pId)
                }
                and{
                    between('registrationYear' ,startSession, endSession)
                }
                projections{
                    groupProperty('registrationYear')
                    rowCount()
                }

            }
            for(int i=0; i<categoryList.size(); i++){
                if(count.size()){
                    boolean flag = false
                    for(j=0;j<count.size();j++){

                        if(count[j]?.getAt(0)==categoryList[i]){
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i]+count[j]?.getAt(1))
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                }
                else{
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.name, sizeList)
        }
        println('totalByCategory is '+ totalByCategory)
        println("final Student map "+ finalStudentMap)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }


    def getReportDataStudentCategory(params, excelPath){
        File file = new File(excelPath);
        WorkbookSettings  wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo=0
        def status
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                and{
                    eq('registrationYear' , Integer.parseInt(params.categoryStudentListSession))
                }
                and{
                    eq('category', params.studentCategory)
                }
            }
            println("--------------"+count)
            status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook)
            sheetNo= sheetNo+1
        }
        workbook.write();
        workbook.close();
        return status
    }

    def getReportDataCourseApprovedUnapproved(params){
        def currentUser=springSecurityService.getCurrentUser()
        def studyCenterId
        if(currentUser){
            studyCenterId = currentUser.studyCentreId
            def studyCenter= StudyCenter.findById(studyCenterId)
            def stuObj= Student.createCriteria()
            if(params.value=='courseUnapproved'){
                def studentList = stuObj.list{
                    programDetail{
                        eq('id', Long.parseLong(params.courseUnapproved))
                    }
                    studyCentre{
                        eq('id' , studyCenter.id)
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.courseUnapprovedSession))
                    }
                    and{
                        eq('status', Status.findById(2))
                    }
                }

                return studentList
            }
            else if(params.value=='courseApproved'){
                def studentList = stuObj.list{
                    programDetail{
                        eq('id', Long.parseLong(params.courseApproved))
                    }
                    studyCentre{
                        eq('id' , studyCenter.id)
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.courseApprovedSession))
                    }
                    and{
                        eq('status', Status.findById(3))
                    }
                }

                return studentList
            }
        }
    }

    //Added By Digvijay...
    def getReportDataDailyFeePaid(params){
        def finalMap =[]
        def feeFromDate
        def feeToDate
        println("Report Service --> getReportDataDailyFeePaid--> Parameters Values :: "+ params)

        def feeObj = FeeDetails.createCriteria()
        def feeList = feeObj.list{
            feeFromDate = new Date().parse("dd/MM/yyyy", params.feeFromDate)
            feeToDate = new Date().parse("dd/MM/yyyy", params.feeToDate)
            between('paymentDate', feeFromDate, feeToDate)
        }
        println("List Value --> "+ feeList)
        feeList.each{
            println("----------"+it.paymentReferenceNumber)
            println("----------"+it.paymentModeId)
        }
        println("Returned List Value --> "+ feeList)
        return feeList
    }


}