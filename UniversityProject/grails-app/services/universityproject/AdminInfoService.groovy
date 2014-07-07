package universityproject

import examinationproject.Bank
import examinationproject.Branch
import examinationproject.City
import examinationproject.CourseSubject
import examinationproject.CustomChallan
import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.RollNoGenerationFixture
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyMaterial
import examinationproject.Subject
import grails.transaction.Transactional
import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class AdminInfoService {
    def studentRegistrationService
def springSecurityService

    def serviceMethod() {

    }

    def provisionalStudentList(params){
        def obj=Student .createCriteria()
        def stuList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            and{
                isNull('rollNo')
            }
        }
//        println("list of students "+ stuList+"-----------------"+params.programId)
        return  stuList



    }

    def subjectList(params){

        def subjectMap=[:]
        def subList=[],totalDateList=[],totalTimeList=[],examDateList=[],newSemesterList=[],semesterNumberList=[],examTimeList=[]
        def dateList=[]
        def programSessionIns=ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterList=Semester.findAllByProgramSession(programSessionIns)
        def count=0
        if(params.sessionTypeId=='1'){
            semesterList.each {
                 if(it.semesterNo%2==0){
                    newSemesterList<<it
                }
            }
        }
        else{
            semesterList.each {
                if(it.semesterNo%2!=0){
                 newSemesterList<<it
                }
            }

        }
         newSemesterList.each{
            dateList<<CourseSubject.findAllByProgramSessionAndSemester(programSessionIns,it)
        }

        dateList.each{
            subList<<it.subject
            semesterNumberList<<it.semester
            examDateList<<it.examDate
            examTimeList<<it.examTime

        }

//        println("??????"+examTimeList)

        for(def i=0;i<dateList.examDate.size();i++){

            for(def j=0;j<examDateList[i].size();j++){
                if(examDateList[i][j]!=null){
                totalDateList<<examDateList[i][j].getDateString()
              ++count
            }
                else{
                    totalDateList<<'noo'
                }
            }
        }
        subjectMap.allSubjects=subList
        subjectMap.semesterNoList=semesterNumberList
        subjectMap.dateList=totalDateList
        subjectMap.examTimeList=examTimeList
//        println("?????????"+subjectMap)

      return subjectMap

    }

    def saveExamDate(params){

        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        def subjectList=params.subjectIdList.split(",")
        def count=0
//        println("+++++++++"+params)

        def sessionObj=ProgramSession.findById(Long.parseLong(params.SessionList))
        subjectList.each{
//            println("@@@@@@@"+Subject.findById(Long.parseLong(it)))
//            println("@@@@@@@"+sessionObj)
         def courseSubjectObj=CourseSubject.findBySubjectAndProgramSession(Subject.findById(Long.parseLong(it)),sessionObj)
//            println("###33333"+courseSubjectObj.programSession+courseSubjectObj.courseDetail)
          def dateList =[]
          def timeList = []
            dateList.addAll(params.examinationDate)
          if(dateList[count]){
//             println("innnnnnnnnnn"+params.examinationDate[count])
            courseSubjectObj.examDate=f1.parse(dateList[count])
//             println("Date is in if"+courseSubjectObj.examDate)
          }
          else{
             courseSubjectObj.examDate=null
          }
            timeList.addAll(params.examinationTime)
            courseSubjectObj.examTime=timeList[count]
            courseSubjectObj.save(failOnError: true)

         ++count
        }

    }

    def saveExamVenue(params){
        def courseIns
        if(params.programList=='All'){
            courseIns=ProgramDetail.list()
        }
        else{
           courseIns=ProgramDetail.findAllById(Long.parseLong(params.programList))
        }

        def examCentreIns=City.findById(Long.parseLong(params.examinationCentre))
        def venueList=params.venueList.split(",")
//        ProgramExamVenue.removeAll(examCentreIns,courseIns)
        def preSavedVenues = ProgramExamVenue.findAllByExamCenterAndCourseDetailInList(examCentreIns,courseIns)
        println('pre saved venue is '+ preSavedVenues)
        preSavedVenues.each{
            it.delete(flush: true)
        }
        venueList.each {
          def venue = it
          courseIns.each{
             def course = it
              println(venue+' venue '+ course)
                   ProgramExamVenue.create course , examCentreIns, ExaminationVenue.findById(Integer.parseInt(venue.toString()))
          }

        }
    }
    def updateStudentList(params){
//        println("==========="+springSecurityService.principal.id)
        def subjectMap=[:]
        def studyCenterId=0
        def status=[]
        def feeStatus=[]
        def statusObj
        if(params.studyCenterId){
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()

            studyCenterId=currentUser.studyCentreId
        }

         def obj=Student .createCriteria()
//        println("session<<<<<<<<<<<"+ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session,ProgramDetail.findById(Long.parseLong(params.programId))))
        def studList = obj.list {
            if (params.session) {

                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                studyCentre {
                    eq('id', Long.parseLong(studyCenterId.toString()))
                }
                and {
                            eq('programSession', ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session,ProgramDetail.findById(Long.parseLong(params.programId))))
                        }
            } else {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                studyCentre {
                    eq('id', Long.parseLong(studyCenterId.toString()))
                }
            }
        }
        subjectMap.studList=studList
        studList.each {
            if(it.status.id<4){
                status<<"UnApproved"
            }
            else{
                status<<"Approved"
            }
            if(it.status.id<3){
                feeStatus<<"Fee Not Paid"
            }
            else if(it.status.id==4){
                feeStatus<<"Fee Approved"
            }
            else{
                feeStatus<<"Fee Paid"
            }

        }
        subjectMap.status=status
        subjectMap.feeStatus=feeStatus
//        println(status)
        return  subjectMap
    }
    def savePayInSlip(params){
        Boolean status=false;
        def feeDetailsInstance = new FeeDetails();
        def student = Student.findByRollNo(params.rollNo);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        feeDetailsInstance.challanNo= student.challanNo
        feeDetailsInstance.paymentDate = df.parse(params.paymentDate)
//        feeDetailsInstance.isAdmission =1
        feeDetailsInstance.bankId = Bank.findById(Long.parseLong(params.bankId))
        feeDetailsInstance.branchId = Branch.findById(Long.parseLong(params.branchId))
        feeDetailsInstance.paymentModeId =PaymentMode.findById(Long.parseLong(params.paymentModeId))
        if (feeDetailsInstance.save(flush: true, failOnError: true)) {
            student.status = Status.findById(3)
            student.save(flush: true, failOnError: true)
            status=true;
        }
        return status
    }
    def saveRollNoGenDate(params){
//        println(params)
        Boolean status=false;
        def rollDateInst = RollNoGenerationFixture.get(1);
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(rollDateInst){
//            println("***************"+rollDateInst.startD)
//            println("***************"+rollDateInst.endD)
            rollDateInst.startD=dFormat.parse(params.startD)
            rollDateInst.endD=dFormat.parse(params.endD)
            if (rollDateInst.save(flush: true, failOnError: true)) {
                status=true;
            }
        }
        else{
            def newRollDateInst=new RollNoGenerationFixture()
            newRollDateInst.startD=dFormat.parse(params.startD)
            newRollDateInst.endD=dFormat.parse(params.endD)
            if (newRollDateInst.save(flush: true, failOnError: true)) {
                status=true;
            }
        }
        def rollDateInst1 = RollNoGenerationFixture.get(1);
        return status
    }

    def studentForStudyMaterial(params){
        def resultMap=[:]

        def subjectsList=[],assignedStudyMaterailList=[]
           def stuList= Student.findAllByRollNo(params.studyMaterialText)
        if(stuList){
        subjectsList<<CourseSubject.findAllBySemesterAndProgramSession(Semester.findBySemesterNoAndProgramSession(stuList.semester,stuList.programSession),stuList.programSession).subject
        resultMap.studentList=stuList
        resultMap.courseDetail=stuList.programDetail[0]
        resultMap.subjectsList=subjectsList
        resultMap.assignedStudyMaterail= StudyMaterial.findAllByStudentId(stuList[0]).subjectId
        }
        return resultMap
    }
    def saveStudentForStudyMaterial(params){
//        println(params)
        def subjectList=[]
        def saveObj
        subjectList.addAll(params.subjectCheckBox)
        def stuIns=Student.findById(Long.parseLong(params.studentListId))
        subjectList
        StudyMaterial.removeAll(stuIns)
        subjectList.each{
         saveObj=   StudyMaterial.create(stuIns,Subject.findById(Long.parseLong(it.toString())))
        }
        return saveObj
    }
    def saveAdmissionPeriod(params){
        Boolean status=false;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        def startAdmission_D = df.parse(params.startAdmission_D)
        def endAdmission_D = df.parse(params.endAdmission_D)
        def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
        programIns.endAdmission_D=endAdmission_D
        programIns.admissionYear=Integer.parseInt(params.admissionYear)
        programIns.startAdmission_D=startAdmission_D
        if(programIns.save(flush: true, failOnError: true)){
            status=true
        }
        return status
    }
    def removeDateLateFee(params){
        Boolean status=false;
        def programIns = ProgramDetail.findById(Integer.parseInt(params.programs))
        programIns.lateFeeDate=null
        if(programIns.save(flush: true, failOnError: true)){
            status=true
        }
        return status
    }
    def deleteTheCourse(params){
        Boolean status=false;
        Boolean dStatus=false;
        def SubjectIns = Subject.findById(Integer.parseInt(params.id))
        def subjMapping=CourseSubject.findAllBySubject(SubjectIns)
        if(subjMapping.size()>0) {
            subjMapping.each {
                it.delete()
                if (it.exists()) {
                    dStatus = true
                }
            }
            if(!dStatus) {
                SubjectIns.delete()
                if (!SubjectIns.exists()) {
                    status = true
                }
            }
        }
        else {
            SubjectIns.delete()
            if (!SubjectIns.exists()) {
                status = true
            }
        }
        return status
    }

}
