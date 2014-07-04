package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

//@Secured("ROLE_ADMIN")

class AdmitCardController {

    def admitCardService
    def pdfRenderingService
    def springSecurityService

    def index() {}

    def viewAdmitCard = {
//        println "here.. "+params
        render(view: "viewAdmitCard")
    }

    def editAdmitCard = {

    }

    def createAdmitCard = {
        def programList = ProgramDetail.list()
        def studyCentreList = StudyCenter.list()
        def examinationCentre = ExaminationVenue.list()
        [programList: programList, studyCentreList: studyCentreList, examinationCentre: examinationCentre]

    }
    @Secured(["ROLE_ADMIN","ROLE_ACCOUNT"])
    def bulkCreationOfAdmitCard = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        def studyCentreList = StudyCenter.list()
        def examinationCenter = City.findAllByIsExamCentre(1, [sort: 'cityName'])
//        def examinationCenter=ExaminationVenue.list()*.city as Set
//        def finalExaminationCenterList= examinationCenter.sort{a,b->
//            a.cityName<=>b.cityName
//        }


        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: examinationCenter]
    }


    def getSemesterList = {
//        println("gettinf semsster wise subjects"+params)
        try {
            if (params.data == 'allProgram') {
                def course = ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
                def sessions = ProgramSession.executeQuery("select distinct  programSession.sessionOfProgram from ProgramSession programSession");
                def resultMap = [:]
                resultMap.totalSem = course[0]
                resultMap.session = sessions
                render resultMap as JSON
            } else {
                def course = ProgramDetail.findById(Integer.parseInt(params.data))
                def resultMap = [:]
                if (course != null) {
                    def programSession = ProgramSession.findAllByProgramDetailId(course)
                    resultMap.totalSem = course.noOfTerms
                    resultMap.session = programSession

                    render resultMap as JSON
                } else {
                    render null
                }
            }
        } catch (Exception e) {
            println("Error in getting Semester Number" + e)

        }

    }
    def getSemesterListOnly = {
        def resultMap = [:]
        def course = ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
        resultMap.totalSem = course[0]
        render resultMap as JSON
    }


    def examVenueCapacity = {
        try {
            def examVenueMap = [:]

            def examVenue = ExaminationVenue.findById(Long.parseLong(params.examVenueId))
            examVenueMap.capacity = examVenue.capacity
            def studentAllocated = Student.findAllByExaminationVenue(examVenue).size()
            examVenueMap.availabelCapacity = examVenue.capacity - studentAllocated

            render examVenueMap as JSON
        }
        catch (Exception e) {
            println("Error in getting Examination Center capacity" + e)
        }

    }
    def getTermListByCatagory = {
        def resultMap = [:]
        def programlist
        def catagory = ProgramType.findById(params.catagory)
        if (params.catagory == '1') {
            programlist = ProgramDetail.findByIdAndProgramType(params.data, catagory).noOfAcademicYears
        } else {
            programlist = ProgramDetail.findByIdAndProgramType(params.data, catagory).noOfTerms
        }
        println("catagory>>>> " + catagory.type)
        println("programlist>>>> " + programlist)
        resultMap.programlist = programlist
        render resultMap as JSON

    }

    def getStudentsForAdmitCard = {
        def studentList = admitCardService.getStudents(params)
        if (studentList) {
            render studentList as JSON
        } else {
            def resultMap = [:]
            resultMap.status = false
            render resultMap as JSON
        }


    }

    def printAdmitCard = {
//        println("?????????????????========" + params)

        def stuList = [], mode = [], examType = [], courseName = []
        def status
        def admitInst=null
        def user = springSecurityService.currentUser
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder examDate = new StringBuilder()
        StringBuilder examTime = new StringBuilder()
        def webRootDir = servletContext.getRealPath("/")
//        println('*******************************************************' + webRootDir)
        def byte[] logo = new File(webRootDir + "/images/gu-logo.jpg").bytes
//        println("these are the logo bytes " + logo);

        if (params.rollNumber && springSecurityService.currentUser) {
            stuList = admitCardService.getStudentByRollNo(user, params)
        } else if (params.rollNumber) {
            stuList = Student.findAllByRollNoAndDobAndAdmitCardGenerated(params.rollNumber.trim(), df.parse(params.dob), true)
        } else if (params.studyCenterId) {
            stuList = admitCardService.getStudentByStudyCenter(user)
        } else {
            def studentList = params.studentList.split(",")
            studentList.each {
                def studentInst = Student.findById(Integer.parseInt(it.toString()))
//                println("AAAAAAAAAAAAAAAAAAAA"+studentInst.studyCentre[0].centerCode)
                Set <City> cityInst=City.findAllById(8)
                if(studentInst.city==cityInst){
                    admitInst = AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examinationVenue))
                }
                stuList << Student.findById(Integer.parseInt(it.toString()))

            }
            status = admitCardService.updateStudentRecord(stuList, params.examinationVenue)
        }
        if (stuList[0]) {
            def programSessionIns
            if (params.programSessionId) {
                programSessionIns = ProgramSession.findById(Long.parseLong(params.programSessionId))
            } else {
                programSessionIns = ProgramSession.findById(stuList[0].programSession.id)
            }
            def subjectList = CourseSubject.findAllBySemesterAndProgramSession(Semester.findBySemesterNoAndProgramSession(stuList[0].semester, stuList[0].programSession), programSessionIns)*.subject

            def dateList = [], timeList = []
            subjectList.each {
                dateList << CourseSubject.findBySubjectAndProgramSession(it, programSessionIns).examDate
                timeList << CourseSubject.findBySubjectAndProgramSession(it, programSessionIns).examTime
            }
//            println("WWWWWWWWWWWWWWWWW"+timeList)
            if (dateList.size() == 0) {
                flash.message = "Examination Date Not Assigned Yet"
                redirect(controller: 'admitCard', action: 'bulkCreationOfAdmitCard')
            }
            def count = 1
            def total = dateList.size()
            dateList.each {
                if (it) {
                    examDate.append(it.format("dd/MM/yyyy"))
                    if (count != total)
                        examDate.append(", ")
                    count++
                }
            }
            def count1 = 1
            def total1 = timeList.size()

            timeList.each {
                if (it) {
                    examTime.append(it)
                    if (count1 != total1)
                        examTime.append(", ")
                    count1++
                }

            }
//            println("ffffffffffffff"+examTime)
            def month = ""
            if (stuList[0].semester % 2 == 0) {
                month = "July"
            } else {
                month = "December"
            }

            def session = stuList[0].programSession.sessionOfProgram.split("-")
            def fileName = stuList[0].programDetail[0].courseName + " " + month + " " + session[0]

            def year = new Date().format("yyyy")
            def args = [template: "printAdmitCard", model: [studentInstance: stuList,examTime:examTime, courseName: courseName, examType: examType, examDate: examDate, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else if (params.studyCenterId) {
            flash.message = "Admit Card Not Generated yet"
            redirect(controller: 'admitCard', action: 'studyCenterAdmitCard')
        } else {
            flash.message = "Admit Card Not Generated yet"
            redirect(controller: 'student', action: 'downloadAdmitCard')
        }

    }

    def printPreviewAdmitCard = {

    }

    def studyCenterAdmitCard = {
        def programList = ProgramDetail.list(sort: 'courseName')
        def studyCentreList = StudyCenter.list()
        def examinationCenter = ExaminationVenue.list()*.city as Set
        def finalExaminationCenterList = examinationCenter.sort { a, b ->
            a.cityName<=>b.cityName
        }

        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: finalExaminationCenterList]

    }
    def loadIdolSignatureInAdmit = {
        def admitInst = null
        if (AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))) {
            admitInst = AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))
        }
        [admitInst: admitInst]

    }
    def loadIdolSignature = {
        def admitInst = null
        if (AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))) {
            admitInst = AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))
        }
        def resultMap=[:]
        resultMap.admitInst=admitInst
        render resultMap as JSON
    }
    def submitSignatureImage = {
        println("---------------------------------------------------->"+params)
        def signature = request.getFile('signature')
        def admitInst
        if (AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))) {
            admitInst = AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examVenue))
        } else {
            admitInst = new AdmitCard()
        }
        if (signature) {
            admitInst.examVenue=ExaminationVenue.findById(params.examVenue)
            admitInst.signatureImg = signature.bytes
        }
        if (admitInst.save(flush: true, failOnError: true)) {
            flash.message = "New Signature For Admit Card is Uploaded."
        }
        redirect(controller: 'admitCard', action: 'loadIdolSignatureInAdmit')
    }
    def showSignature = {
        def id = Integer.parseInt(params.id)
        def something = AdmitCard.get(id)
        byte[] image = something.signatureImg
        response.setContentType(params.mime)
        response.outputStream << image
    }
    def SingleAdmitCardGenerate={
    }
    def generateSingleAdmitCard={
        println("-------------"+params)
        def stuList = [], mode = [], examType = [], courseName = []
        def status
        def admitInst=null
        def user = springSecurityService.currentUser
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder examDate = new StringBuilder()
        StringBuilder examTime = new StringBuilder()
        def webRootDir = servletContext.getRealPath("/")
        def byte[] logo = new File(webRootDir + "/images/gu-logo.jpg").bytes
          def  student = Student.findByRollNoAndSemester(params.rollNoForFeeStatus,params.semesterList)
        def stuAdmissionFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(student, Integer.parseInt(params.semesterList), FeeType.findById(3), Status.findById(4))
        if (stuAdmissionFeeInst) {
            def stuExamFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(student, Integer.parseInt(params.semesterList), FeeType.findById(1), Status.findById(4))
            if (stuExamFeeInst || params.feeExempt) {
                admitInst = AdmitCard.findByExamVenue(ExaminationVenue.findById(params.examinationVenue))
                stuList<<student
                status = admitCardService.updateStudentRecord(stuList, params.examinationVenue)
            }
            else{
                flash.message = "Examination Fee Not Paid or Approved."
//                redirect(controller: 'admitCard', action: 'SingleAdmitCardGenerate')
            }
        }
        else{
            flash.message = "Admission and Examination Fee Not Paid or Approved."
//            redirect(controller: 'admitCard', action: 'SingleAdmitCardGenerate')
        }
        println("+++++++++++++++++++++++++"+stuList)
        if (stuList[0]) {
            def programSessionIns= ProgramSession.findById(stuList[0].programSession.id)
            def subjectList = CourseSubject.findAllBySemesterAndProgramSession(Semester.findBySemesterNoAndProgramSession(stuList[0].semester, stuList[0].programSession), programSessionIns)*.subject

            def dateList = [], timeList = []
            subjectList.each {
                dateList << CourseSubject.findBySubjectAndProgramSession(it, programSessionIns).examDate
                timeList << CourseSubject.findBySubjectAndProgramSession(it, programSessionIns).examTime
            }
            if (dateList.size() == 0) {
                flash.message = "Examination Date Not Assigned Yet"
//                redirect(controller: 'admitCard', action: 'SingleAdmitCardGenerate')
            }
            def count = 1
            def total = dateList.size()
            dateList.each {
                if (it) {
                    examDate.append(it.format("dd/MM/yyyy"))
                    if (count != total)
                        examDate.append(", ")
                    count++
                }
            }
            def count1 = 1
            def total1 = timeList.size()

            timeList.each {
                if (it) {
                    examTime.append(it)
                    if (count1 != total1)
                        examTime.append(", ")
                    count1++
                }

            }
            def month = ""
            if (stuList[0].semester % 2 == 0) {
                month = "July"
            } else {
                month = "December"
            }

            def session = stuList[0].programSession.sessionOfProgram.split("-")
            def fileName = stuList[0].programDetail[0].courseName + " " + month + " " + session[0]

            def year = new Date().format("yyyy")
//            println("year"+year)
        def args = [template: "printAdmitCard", model: [studentInstance: stuList,examTime:examTime, courseName: courseName, examType: examType, examDate: examDate, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    } else {
            println("errrrrrrrrrr")
//        flash.message = "Roll Number or Term must be wrong or Admit Card Already Generated."
        redirect(controller: 'admitCard', action: 'SingleAdmitCardGenerate')
    }
    }
}
