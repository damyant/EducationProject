package examinationproject

import grails.converters.JSON

class ExaminationCenterController {
    def examinationCentreService

    def index() {}

    def createNewCentre(){
    }
    def saveExaminationCentre ={
        Boolean flag = examinationCentreService.saveCentres(params)
        if(flag){
            render   "${message(code: 'centre.created.message')}"
        }
        else{
             render "Centres Not Saved"
        }
    }
    def viewExaminationCentre(){}

    def getCentreList = {
        println("in getCentreList "+ params)
        def result= examinationCentreService.studyCenterList(params)
        println("resultttttttttttttttttttttt "+ result)
       if(result){
        render(template: "listOfCentre", model: [centreList: result, edit:params.edit, delete:params.delete])
        }
        else
        {
            render "<h1>No Examination Centre Found</h1>"
        }
    }
    def updateExaminationCentre= {}


    def editExaminationCentre ={
        println(params.id)
        def examinationCentreInstance = ExaminationCentre.findById(params.id)
        println("location of examination centre is "+ examinationCentreInstance.city.district.districtName)
        return [examinationCentreInstance: examinationCentreInstance]
    }
    def updateCentre ={
            def examCentreIns =  ExaminationCentre.get(params.id)
           def isSaved= examinationCentreService.updateExaminationCentre(params)
            if(isSaved){
                println("updated succesfully")
                flash.message = "${message(code: 'centre.updated.message', args: [message( default: 'ExaminationCentre'),   examCentreIns.id])}"
                render(view: "editExaminationCentre" , model:[examinationCentreInstance:examCentreIns])
            }
        else{
                flash.message = "${message(code: 'centre.notUpdated.message', args: [message( default: 'ExaminationCentre'),   examCentreIns.id])}"
                render(view: "editExaminationCentre" , model:[examinationCentreInstance:examCentreIns])
            }

        }

    def deleteExaminationCentre={

    }
    def deleteCentre={
        try {
            println('in delete Centre')
            def examCentreInstance = ExaminationCentre.get(params.id)
            examCentreInstance.delete(flush: true)
            flash.message = "${message(code: 'centre.deleted.message')}"
            redirect(action: "deleteExaminationCentre")
        }
      catch (Exception e){
          flash.message = "${message(code: 'centre.cannotDeleted.message')}"
          redirect(action: "deleteExaminationCentre")
      }


    }
    def getExaminationCentreList(){
        println("hello kuldeep you are displaying this"+ params)
        try{
            City city = City.get(params.int('data'));
            def centreList = null
            if (city != null) {
                centreList = ExaminationCentre.findAllByCity(city)
                println("<><><><><><><><>><<><>"+centreList)
                render centreList as JSON
            } else {
                render null
            }
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting city list" + e)
        }
    }
    def create={

    }

}
