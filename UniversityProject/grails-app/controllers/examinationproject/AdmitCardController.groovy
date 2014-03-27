package examinationproject

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

}
