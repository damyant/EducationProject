package universityproject

import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyCenter
import grails.transaction.Transactional
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.Workbook

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class ReportService {
    def writeExcelForFeeService
    def writeExcelService
    def springSecurityService
    def getReportDataSession(params, excelPath) {
        def session = params.session
         def finalStudentMap = [:]
         def totalForSession=0
         def studyCenterId=0
         def currentUser=springSecurityService.getCurrentUser()
         println("-------------------------------"+currentUser)
         def programList = ProgramDetail.list(sort: 'courseCode')
         if(currentUser.studyCentreId && params.inExcel){
             def status
             File file = new File(''+excelPath);
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
                status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCenter.name, session)
                sheetNo= sheetNo+1
             }
             workbook.write();
             workbook.close();
             return status
        }
         else if(currentUser.studyCentreId){
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
                     projections {
                         rowCount()
                     }
                 }

                 totalForSession+=count
                 finalStudentMap.put(it.courseName, count.getAt(0))
             }
             finalStudentMap.put("TOTAL STUDENTS", totalForSession)
             return finalStudentMap
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
             finalStudentMap.put("TOTAL STUDENTS", totalForSession)
             return finalStudentMap
        }

    }
    def getReportDataSessions(startSession, endSession) {
        def categoryList =[]
        categoryList
        def totalByCategory=[]
        int j=0
        for(int i=startSession; i<=endSession;i++){
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
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }
    Boolean getReportDataCourse(params, excelPath){
        def session =params.courseSession
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

//        return studentList
       def status=  writeExcelService.excelReport(params, studentList, course, sheetNo, workbook, null, session)
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
            def session = params.studyCentreSession
            File file = new File(excelPath);
            WorkbookSettings  wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            def studyCentreName = StudyCenter.findById(Long.parseLong(params.studyCentre))
            println("this is the name of studtycentre " + studyCentreName.name)
            int sheetNo=0
            programList.each {
                def pId= it.id
                println(params.studyCentreSession+" course name "+ it.courseName)
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
                    and{
                        eq('status', Status.findById(4))
                    }
                }
                println(pId+ "--"+params.studyCentre +"------------------"+count)
                 status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCentreName.name, session)
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
                and{
                    eq('status', Status.findById(4))
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
            def session = params.examinationCentreSession
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
                    city{
                        eq('id' , Long.parseLong(params.examCity))
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.examinationCentreSession))
                    }
                    and{
                        eq('status', Status.findById(4))
                    }
                }
                println("--------------"+count)
                status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook, null, session)
                sheetNo= sheetNo+1
            }
            workbook.write();
            workbook.close();
            return status
        }
        else{
            println("in else")
            programList.each {
                def pId= it.id
                def stuObj= Student.createCriteria()
                def count = stuObj.list{
                    programDetail{
                        eq('id', pId)
                    }
                    city{
                        eq('id' , Long.parseLong(params.examCity))
                    }
                    and{
                        eq('registrationYear' , Integer.parseInt(params.examinationCentreSession))
                    }
                    and{
                        eq('status', Status.findById(4))
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
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }
    def getReportDataCategoryGender(params){
        def categoryList=['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        def genderList=['Male', 'Female']
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
            finalStudentMap.put(it.courseName, sizeList)
        }
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
                        ne('status', Status.findById(4))
                    }

                order('programDetail', 'asc')
            }
            return studentList
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
                    eq('status', Status.findById(4))
                }

                order('programDetail', 'asc')
            }
            return studentList
        }


    }
    def getReportDataAdmissionSelfRegistration(params){
        def stuObj= Student.createCriteria()
        def studentList = stuObj.list{
            ne('referenceNumber', '0')
            and{
                eq('registrationYear' , Integer.parseInt(params.admissionSelfRegistrationSession))
            }
//            and{
//                ne('referenceNumber', 0)
//            }

//             and{
//                eq('status', Status.findById(1))
//             }
//            and{
//              isNotNull('referenceNumber')
//            }

        }
        return studentList
    }
    def getReportDataStudyCentreFeePaid(params){
       def finalMap =[]
       def studentList
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def fromDate
        def toDate
        def stuObj = Student.createCriteria()
        if(params.value=='dailyFeePaid') {
                        studentList = Student.list()

              fromDate = df.parse(params.feeFromDate)
              toDate = df.parse(params.feeToDate)
        }
        else{
                       studentList = stuObj.list{
                       studyCentre{
                           eq('id' , Long.parseLong(params.feePaidStudyCentre))
                       }
                   }
              fromDate = df.parse(params.studyCentreFeeFromDate)
              toDate = df.parse(params.studyCentreFeeToDate)
        }
        toDate.setHours(24)
        println(fromDate)
        println(" and to date is "+ toDate)
        def feeType = FeeType.list(sort:'type');
        int finalTotal =0
        int i=1
            feeType.each{
                def feeTypeIns = it
                def feeDetails = FeeDetails.createCriteria()
                def musFeeList = feeDetails.list{
                 'in'('student', studentList)
                    and{
                        between('paymentDate', fromDate, toDate)
                        eq('feeType', feeTypeIns)
                    }
                    and{
                        eq('isApproved', Status.findById(4))
                    }

                 }
                int totalForList =0
                println("this is the count "+  musFeeList)
                if(musFeeList){
                    finalMap.add(musFeeList)
                    musFeeList.each{
                        totalForList = totalForList + it.paidAmount
                    }
                }
              finalTotal = finalTotal+ totalForList
              if(totalForList > 0)
              finalMap.add(totalForList)
                i=i+1
            }
//        finalMap.add(finalTotal)
        return finalMap
    }

    def getReportDataPaymentMode(params){
        println('these are the parameterssssssssssss '+params)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def fromDate = df.parse(params.paymentModeFromDate)
        def toDate = df.parse(params.paymentModeToDate)
        toDate.setHours(24)
        def feeDetails = FeeDetails.createCriteria()
        def feeList = feeDetails.list{
            eq('paymentModeId', PaymentMode.findById(Integer.parseInt(params.paymentMode)))
            and{
                between('paymentDate', fromDate, toDate)

            }
            and{
                eq('isApproved', Status.findById(4))
            }

        }
        println(",.,.,,.,.,.,.,.,.,..,.,.,. "+feeList)
        return feeList
    }



    def getReportDataComparative(startSession, endSession){
        def categoryList =[]
        categoryList
        def totalByCategory=[]
        int j=0
        for(int i=startSession; i<=endSession;i++){
            categoryList.add(j, i)
            totalByCategory.add(j,0)
            j++
        }

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
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }
    def getReportDataStudentCategory(params, excelPath){
        println(params.categoryStudentListSession+"--------------------------"+params.studentCategory)
        def session =  params.categoryStudentListSession
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
                    eq('category', ''+params.studentCategory)
                }
            }
            status=  writeExcelService.excelReport(params, count, it, sheetNo, workbook, null, session)
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
                        ne('status', Status.findById(4))
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
                        eq('status', Status.findById(4))
                    }
                }

                return studentList
            }
        }
    }    //Added By Digvijay...
    def getReportDataDailyFeePaid(params){
        def finalMap =[:]
        def feeFromDate
        def feeToDate
        println("Report Service --> getReportDataDailyFeePaid--> Parameters Values :: "+ params)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
         feeFromDate = df.parse(params.feeFromDate)
         feeToDate = df.parse(params.feeToDate)
        feeToDate.setHours(24)
        def feeType = FeeType.list(sort:'type')
        int i=0
        int finalTotal = 0
        feeType.each{
                def feeTypeIns = it
                def feeObj = FeeDetails.createCriteria()
                    def feeList = feeObj.list{
                        eq('feeType', feeTypeIns)
                        and{
                            between('paymentDate', feeFromDate, feeToDate)
                        }
                    }
            int totalForList =0
            if(feeList){
                feeList.each{
                    totalForList = totalForList + it.paidAmount
                }
            }
            finalTotal = finalTotal+ totalForList
            finalMap.put("a"+i, feeList)
            i=i+1
            finalMap.put(it.type+' Total', totalForList)
                println("List Value --> "+ feeList)
                return feeList
        }

    }
    def getReportDataExaminationCentreCumulative(params){
        def examinationVenueIns = ExaminationVenue.findById(Long.parseLong(params.examinationCentreCumulative))
        def program = ProgramExamVenue.findAllByExamVenue(examinationVenueIns)
        def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
        println('max no of course '+ course)

        if(params.examinationCentreCumulativeSchedule=='July'){
            program.each{
                def pid= it.courseDetail.id
                for(int i=2; i<=course[0]; i+=2){
                    def obj = Student.createCriteria()
                        def  studentList = obj.list {
                        programDetail {
                            eq('id', pid)
                        }
                        examinationVenue {
                            eq('id', examinationVenueIns.id)
                        }
                        and {
                            eq('status', Status.findById(4))
                        }
                        and {
                            eq('semester', i)
                        }
                            projections{
                                rowCount()
                                groupProperty('semester')
                            }
                    }
                    println("this is the result of the query "+studentList+' for the course '+pid+' for the semester '+i)
                }
            }
        }
        else if(params.examinationCentreCumulativeSchedule=='January'){
            program.each{
                def pid= it.courseDetail.id
                for(int i=1; i<=course[0]; i+=2){
                    def obj = Student.createCriteria()
                    def  studentList = obj.list {
                        programDetail {
                            eq('id', pid)
                        }
                        examinationVenue {
                            eq('id', examinationVenueIns.id)
                        }
                        and {
                            eq('status', Status.findById(4))
                        }
                        and {
                            eq('semester', i)
                        }
                        projections{
                            rowCount()
                            groupProperty('semester')
                        }
                    }
                    println("this is the result of the query "+studentList+' for the course '+pid+' for the semester '+i)
                }
            }
        }
    }
    def getReportDataSessionProgramWiseFee(params, excelPath){
        def session =  params.categoryStudentListSession
        File file = new File(excelPath);
        WorkbookSettings  wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo=0
        def status
        def stuList
        def studyCentreName
            if(params.sessionProgramFeePaidStudyCentre!='All')
                 studyCentreName = StudyCenter.findById(Long.parseLong(params.sessionProgramFeePaidStudyCentre)).name
            else
                 studyCentreName = null
            def programList = ProgramDetail.list(sort: 'courseCode')
            programList.each {
                if(it.noOfTerms>= Integer.parseInt(params.programTerm)) {
                        def stuObj = Student.createCriteria()
                        def programIns = it
                        if(params.sessionProgramFeePaidStudyCentre=='All'){
                            stuList = stuObj.list{
                                programDetail{
                                    eq('id', programIns.id)
                                }
                                and{
                                    eq('registrationYear' , Integer.parseInt(params.sessionProgramFeePaidSession))
                                }
                            }
                        }
                        else{
                            stuList = stuObj.list{
                                studyCentre{
                                    eq('id' , Long.parseLong(params.sessionProgramFeePaidStudyCentre))
                                }
                                programDetail{
                                    eq('id', programIns.id)
                                }
                                and{
                                    eq('registrationYear' , Integer.parseInt(params.sessionProgramFeePaidSession))
                                }
                            }
                        }
                        if(stuList){
                            if(params.value =='sessionProgramWiseFeePaid'){
                                    def count = FeeDetails.findAllByStudentInListAndFeeTypeAndSemesterValueAndIsApproved(stuList,FeeType.findById(3), Integer.parseInt(params.programTerm), Status.findById(4))
                                println("hello kuldeeppppppppppppppp "+count)
                                    if(count){
                                        status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName , session)
                                        sheetNo= sheetNo+1
                                    }
                            }
                            else if(params.value =='sessionProgramWiseFeeNotPaid'){
                                def count = FeeDetails.findAllByStudentInListAndFeeTypeAndSemesterValueAndIsApprovedNotEqual(stuList,FeeType.findById(3), Integer.parseInt(params.programTerm), Status.findById(4))
                                if(count){
                                        status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName , session)
                                        sheetNo= sheetNo+1
                                }
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

    def getReportDataDailyAdmissionReport(params){
        def stuObj = Student.createCriteria()
        def stuList
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def  fromDate = df.parse(params.dailyAdmissionFromDate)
        def toDate = df.parse(params.dailyAdmissionToDate)
        toDate.setHours(24)
        if(params.dailyAdmissionStudyCentre=='All')   {
            stuList = stuObj.list{
                and{
                    between('admissionDate', fromDate, toDate)
                }
//                and{
//                    eq('admissionDate', fromDate)
//                    eq('admissionDate', toDate)
//                }
                and{
                    ne('rollNo', IS_NULL)
                }
            }
        }
        else{
            println('in else' +params.dailyAdmissionStudyCentre)
            stuList = stuObj.list{
                studyCentre{
                    eq('id' , Long.parseLong(params.dailyAdmissionStudyCentre))
                }
                and{
                    between('admissionDate', fromDate, toDate)
                }
                and{
                    ne('rollNo', IS_NULL)
                }
            }
        }
        println("this is the list of students "+ stuList)
        return stuList
    }
}

