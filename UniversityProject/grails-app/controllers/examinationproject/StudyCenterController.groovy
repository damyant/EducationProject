package examinationproject

import grails.converters.JSON

class StudyCenterController {

    def studyCenterInfoService

    def index() {}

    def createNewStudyCenter() {
        try {
            studyCenterInfoService.studyCenterDetailInfo(params)
        }
        catch (Exception e) {
            println("<<<<<<<<<<<There is some problem in saving new study center" + e)
        }

    }

    def saveStudyCenter() {
        try {
           def objStatus= studyCenterInfoService.saveInfo(params)
            if(objStatus=='created'){
                redirect(action: "createNewStudyCenter", params:['status':objStatus])
            }
            else if (objStatus=='updated'){
                redirect(action: "createNewStudyCenter", params:['status':objStatus,'studyCenterId':params.studyCenterId,'type':'edit'])
            }
        }
        catch (Exception e) {
            println("<<<<<<<<<<<There is some problem in saving new study center" + e)
        }


    }

    def deleteStudyCenter() {
        try{
            println('in delete Centre')
            StudyCenter studyCenter = StudyCenter.get(params.int('data'))
            studyCenter.delete(flush: true)
            flash.message = "${message(code: 'centre.deleted.message')}"
            redirect(action: "updateStudyCentre")
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in deleting study center" + e)
        }

    }

    def viewStudyCentre() {

    }

    def updateStudyCentre(){
        redirect(action: "viewStudyCentre", params:['type':"update"])
    }



    def getStudyCenterList(){

        try{
        def status=[:]
        def result= studyCenterInfoService.studyCenterList(params)
        if(result)
        render result as JSON
        else  status.flag="false"
        render status as JSON
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting study center list" + e)
        }
    }

    def getCityList() {

        try{
        District district = District.get(params.int('data'));
        def cityList = null
        if (district != null) {
            cityList = City.findAllByDistrict(district)
            render cityList as JSON
        } else {
            render null
        }
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting city list" + e)
        }
    }
    def editStudyCenter(){

    }

}