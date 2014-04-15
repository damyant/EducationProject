package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured("ROLE_ADMIN")
class AdmitCardController {

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
        println("program is "+ params.data)
        def course=ProgramDetail.findById(params.data)
            def semesterNo = [:]
            if(course!=null){
                semesterNo.totalSem = course.noOfTerms
                println("sem no" + semesterNo)
                render semesterNo as JSON
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
        println("??????????????"+params)


    }

}
