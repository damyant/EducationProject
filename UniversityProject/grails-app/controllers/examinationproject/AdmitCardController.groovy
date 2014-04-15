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
        def programList = ProgramDetail.list()
        def studyCentreList = StudyCenter.list()
        def examinationCentre = ExaminationCentre.list()
        [programList: programList, studyCentreList: studyCentreList, examinationCentre: examinationCentre]
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
            println("Error in getting Semester Number")
        }
    }
    def printPreviewAdmitCard={

    }
}
