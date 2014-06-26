package universityproject

import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.MiscellaneousFeeChallan
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.ProgramSession
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyCenter
import grails.transaction.Transactional
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.Workbook
import org.codehaus.groovy.classgen.genDgmMath

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
        def session =params.courseSession
        println("these are the parameters "+ params)
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
                    and{
                        ne('status', Status.findById(4))
                    }
                }
                println("--------------"+count)
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
        println("hello kuldeep u r in this ")
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
                    projections {
                        rowCount()
                    }
                }

                totalForSession+=count
    //            println('final student map is '+ finalStudentMap)
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            println("oye kuldeep "+ params)
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
                        ne('status', Status.findById(4))
                    }

                order('programDetail', 'asc')
            }
                   println("this is the result of query "+ studentList)
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
            println("this is the result of query----------- "+ studentList)
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
        println("this is the list--------------- "+studentList)
        return studentList
    }
    def getReportDataStudyCentreFeePaid(params){
       def finalMap =[]
       def studentList
       println("these "+ params)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
        println('now going to fetch the fee records *** '+ fromDate+ ' and the to date is '+ toDate)
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

                 }
                println(feeTypeIns.type+"hello kuldeep ************************************************"+musFeeList)
                int totalForList =0
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
        println('finalMap is ' + finalMap)
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
            println("--------------"+count)
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
                println('hello kuldeep in this')
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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         feeFromDate = df.parse(params.feeFromDate)
         feeToDate = df.parse(params.feeToDate)
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
                println("this is the list of students "+ stuList)
                if(stuList){
                    if(params.value =='sessionProgramWiseFeePaid'){
                            def count = FeeDetails.findAllByStudentInListAndFeeTypeAnd(stuList,FeeType.findById(Integer.parseInt(params.sessionProgramFeePaidFeeType)))
                            println("--------------"+count)
                            if(count){
                                status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName , session)
                                sheetNo= sheetNo+1
                            }
                    }
                    else if(params.value =='sessionProgramWiseFeeNotPaid'){
                            def feeDetailsStudentList = FeeDetails.findAllByFeeType(FeeType.findById(Integer.parseInt(params.sessionProgramFeePaidFeeType))).student.unique()
                            def count= Student.findAllByIdNotInList(feeDetailsStudentList)
                            println("--------------"+count)
                            if(count){
                                status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName , session)
                                sheetNo= sheetNo+1
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
}

