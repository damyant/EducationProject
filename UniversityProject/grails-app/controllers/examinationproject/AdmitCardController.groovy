package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

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
//        def examinationCentre = ExaminationCentre.list()
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
        render examCenterMap as JSON
        }
        catch (Exception e){
            println("Error in getting Examination Center capacity"+e)
        }

    }

    def getStudentsForAdmitCard={

     def studentList=admitCardService.getStudents(params)
        println("list====="+studentList)
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

        println("params" + params)
                def studentList=params.studentList.split(",")
//        println("params" + studentList[1])
        def stuList = []
//        def studentList=params.studentList.split(",")
//
        studentList.each{
        stuList << Student.findById(Integer.parseInt(it.toString()))

        }
//        println("students are===="+stuList)

        def args = [template: "printAdmitCard", model: [studentInstance: stuList]]
        pdfRenderingService.render(args + [controller: this], response)
//        println("Student Name is " + student.name)
    }

    def printPreviewAdmitCard={

    }


}
