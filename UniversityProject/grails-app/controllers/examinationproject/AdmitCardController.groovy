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
        println "here.. "+params
        render(view: "viewAdmitCard")
    }

    def editAdmitCard={

    }

    def createAdmitCard ={
        def programList = ProgramDetail.list()
        def studyCentreList = StudyCenter.list()
        def examinationCentre = ExaminationCentre.list()
        [programList: programList, studyCentreList: studyCentreList, examinationCentre: examinationCentre]

    }
    def bulkCreationOfAdmitCard ={
        def programList = ProgramDetail.list(sort:'courseName')
        def studyCentreList = StudyCenter.list()
        def examinationCenter=ExaminationCentre.list()*.city as Set
        def finalExaminationCenterList= examinationCenter.sort{a,b->
            a.cityName<=>b.cityName
        }

        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: finalExaminationCenterList]
    }

    def getSemesterList={
        try{

        def course=ProgramDetail.findById(params.data)
            def resultMap = [:]

            if(course!=null){
                resultMap.totalSem = course.noOfTerms
                resultMap.session=course.programSession

                render resultMap as JSON
            }
        else {
            render null
        }
        }catch (Exception e){
            println("Error in getting Semester Number"+e)
        }
    }

    def examVenueCapacity={
        try{
        def examCenterMap=[:]

            def examCenter=ExaminationCentre.findById(Long.parseLong(params.examCenterId))
            examCenterMap.capacity=examCenter.capacity

            def obj=Student .createCriteria()
            def stuList= obj.list{
                examinationCentre{
                    eq('id', Long.parseLong(params.examCenterId))
                }
                and{
                    eq('admitCardGenerated',true)
                }

            }

        examCenterMap.availabelCapacity=examCenter.capacity-stuList.size()

        render examCenterMap as JSON
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
        println("?????????????????========"+params)
        println("user===="+springSecurityService.currentUser)
        def stuList = []
        StringBuilder examDate = new StringBuilder()
        def byte [] logo= new File("web-app/images/gu-logo.jpg").bytes
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if(params.rollNumber){
         stuList=   Student.findAllByRollNoAndDobAndAdmitCardGenerated(Integer.parseInt(params.rollNumber.trim()),df.parse(params.dob),true)

        }
        if(params.studyCenterId){
            def user=springSecurityService.currentUser
              def obj=Student .createCriteria()
            stuList= obj.list{
                studyCentre{
                    eq('id', Long.parseLong(user.studyCentreId.toString()))
                }
                and{
                    eq('admitCardGenerated', true)

                }

            }
        }
        else{

            def studentList=params.studentList.split(",")
            studentList.each{
                stuList << Student.findById(Integer.parseInt(it.toString()))
            }
        }
        if(stuList[0]){

        def list=CourseSubject.findAllByCourseDetailAndSemester(stuList[0].programDetail,Semester.findBySemesterNoAndCourseDetail(stuList[0].semester,stuList[0].programDetail))*.subject as Set
        def finalList=list.sort{a,b->
            a.examDate<=>b.examDate
        }

        finalList.each{
            examDate.append(it.examDate.format("dd/MM/yyyy"))
            examDate.append(", ")
        }
        stuList.each{
            it.admitCardGenerated=true
            it.save(failOnError: true)
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
        def examinationCenter=ExaminationCentre.list()*.city as Set
        def finalExaminationCenterList= examinationCenter.sort{a,b->
            a.cityName<=>b.cityName
        }

        [programList: programList, studyCentreList: studyCentreList, examinationCenterList: finalExaminationCenterList]

    }


}
