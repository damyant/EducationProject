package examinationproject

import grails.converters.JSON

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

    def editAdmitCard={

    }

    def createAdmitCard ={
        def programList = ProgramDetail.list()
        def studyCentreList = StudyCenter.list()
        def examinationCentre = ExaminationVenue.list()
        [programList: programList, studyCentreList: studyCentreList, examinationCentre: examinationCentre]

    }
    def bulkCreationOfAdmitCard ={
        def programList = ProgramDetail.list(sort:'courseName')
        def studyCentreList = StudyCenter.list()
        def examinationCenter=City.findAllByIsExamCentre(1,[sort: 'cityName'])
//        def examinationCenter=ExaminationVenue.list()*.city as Set
//        def finalExaminationCenterList= examinationCenter.sort{a,b->
//            a.cityName<=>b.cityName
//        }


        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: examinationCenter]
    }


    def getSemesterList={
//        println("gettinf semsster wise subjects"+params)
        try{
            if(params.data=='allProgram'){
                def course=ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
                def sessions= ProgramSession.executeQuery( "select distinct  programSession.sessionOfProgram from ProgramSession programSession" );
                def resultMap = [:]
                resultMap.totalSem = course[0]
                resultMap.session =  sessions
                render resultMap as JSON
            }
            else{
                def course=ProgramDetail.findById(Integer.parseInt(params.data))
                def resultMap = [:]
                if(course!=null){
                    def programSession = ProgramSession.findAllByProgramDetailId(course)
                    resultMap.totalSem = course.noOfTerms
                    resultMap.session=programSession

                    render resultMap as JSON
                }
                else {
                    render null
                }
            }
        }catch (Exception e){
            println("Error in getting Semester Number"+e)

        }

    }

    def examVenueCapacity={
        try{
        def examVenueMap=[:]

            def examVenue=ExaminationVenue.findById(Long.parseLong(params.examVenueId))
            examVenueMap.capacity=examVenue.capacity
            def studentAllocated=Student.findAllByExaminationVenue(examVenue).size()
            examVenueMap.availabelCapacity=examVenue.capacity-studentAllocated

        render examVenueMap as JSON
        }
        catch (Exception e){
            println("Error in getting Examination Center capacity"+e)
        }

    }

    def getStudentsForAdmitCard={
     def studentList=admitCardService.getStudents(params)
      if(studentList){
         render studentList as JSON
      }
      else{
          def resultMap=[:]
          resultMap.status=false
          render resultMap as JSON
      }



    }
    def printAdmitCard={
//        println("?????????????????========"+params)

        def stuList = []
        def status
        def user=springSecurityService.currentUser
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder examDate = new StringBuilder()
        def byte [] logo= new File("web-app/images/gu-logo.jpg").bytes

        if(params.rollNumber && springSecurityService.currentUser){
        stuList=admitCardService.getStudentByRollNo(user,params)
        }
        else if(params.rollNumber){
         stuList=   Student.findAllByRollNoAndDobAndAdmitCardGenerated(params.rollNumber.trim(),df.parse(params.dob),true)
        }
        else if(params.studyCenterId){
           stuList=admitCardService.getStudentByStudyCenter(user)
        }
        else{
            def studentList=params.studentList.split(",")
            studentList.each{
                stuList << Student.findById(Integer.parseInt(it.toString()))
            }
          status=  admitCardService.updateStudentRecord(stuList,params.examinationVenue)
        }
        if(stuList[0]){
            def programSessionIns=ProgramSession.findById(Long.parseLong(params.programSessionId))

//            println(Semester.findBySemesterNoAndProgramSession(stuList[0].semester,stuList[0].programSession))
        def subjectList=CourseSubject.findAllBySemesterAndProgramSession(Semester.findBySemesterNoAndProgramSession(stuList[0].semester,stuList[0].programSession),programSessionIns)*.subject

            def dateList=[]
            subjectList.each{
               dateList<< CourseSubject.findBySubjectAndProgramSession(it,programSessionIns).examDate
            }

            dateList.each{
            if(it){
            examDate.append(it.format("dd/MM/yyyy"))
            examDate.append(", ")
            }
        }

        def month=""
        if(stuList[0].semester%2==0){
            month="July"
        }
        else{
            month="December"
        }

        def session=stuList[0].programSession.sessionOfProgram.split("-")
        def fileName=stuList[0].programDetail[0].courseName+" "+month+" "+session[0]

        def args = [template: "printAdmitCard", model: [studentInstance: stuList,examDate:examDate,guLogo:logo],filename:fileName+".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
        }
        else if (params.studyCenterId){
            flash.message="Admit Card Not Generated yet"
            redirect(controller:'admitCard', action: 'studyCenterAdmitCard')
        }
        else{
            flash.message="Admit Card Not Generated yet"
            redirect(controller:'student', action: 'downloadAdmitCard')
        }

    }

    def printPreviewAdmitCard={

    }

    def studyCenterAdmitCard={
        def programList = ProgramDetail.list(sort:'courseName')
        def studyCentreList = StudyCenter.list()
        def examinationCenter=ExaminationVenue.list()*.city as Set
        def finalExaminationCenterList= examinationCenter.sort{a,b->
            a.cityName<=>b.cityName
        }

        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: finalExaminationCenterList]

    }


}
