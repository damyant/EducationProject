package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.SimpleDateFormat

@Secured("ROLE_ADMIN")

class AdmitCardController {

    def admitCardService
    def pdfRenderingService

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
        def studentList=params.studentList.split(",")
        def stuList = []
        StringBuilder examDate = new StringBuilder()
        def byte [] logo= new File("web-app/images/gu-logo.jpg").bytes
        studentList.each{
        stuList << Student.findById(Integer.parseInt(it.toString()))
          }

        def list=CourseSubject.findAllByCourseDetailAndSemester(stuList[0].programDetail,Semester.findById(stuList[0].semester))*.subject as Set
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

        def args = [template: "printAdmitCard", model: [studentInstance: stuList,examDate:examDate,guLogo:logo]]
        pdfRenderingService.render(args + [controller: this], response)

    }

    def printPreviewAdmitCard={

    }


}
