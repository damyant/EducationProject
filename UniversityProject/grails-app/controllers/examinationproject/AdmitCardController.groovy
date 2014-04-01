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
    }

    def getSemesterList={
        try{
        println("program is "+ params.data)
        def course=ProgramDetail.findById(params.data)
        if(course!=null){
        def semesterId=course.semester
        def semesterList=semesterId.semesterNo
        semesterList.sort()
        println(semesterList)
        render semesterList as JSON
        }
        else {
            render null
        }
        }catch (Exception e){
            println("Error in getting SemesterList")
        }
    }

}
