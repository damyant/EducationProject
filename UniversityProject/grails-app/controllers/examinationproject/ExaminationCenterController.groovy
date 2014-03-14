package examinationproject

import grails.converters.JSON

class ExaminationCenterController {
    def saveExaminationCentreService

    def index() {}

    def createNewCentre(){

    }
    def saveExaminationCentre ={
        println("hello kuldeep "+ params)
        def status=[:]
        Boolean flag = saveExaminationCentreService.saveCentres(params);
        println("back to controller")
        if(flag){
//            println("value of flag is "+flag)
//            status.flag="center saved"
            render  "centre saved"
        }
        else{
            println("value of flag is "+flag)
            render "Centres Not Saved"
        }
    }
    def saveNewCentre = {
        println("save centre " + params)
        def examCentreIns = new ExaminationCentre()
        examCentreIns.location= params.location;
        examCentreIns.capacity=Integer.parseInt(params.capacity)
        examCentreIns.name=params.centreName
        examCentreIns.contactNo= params.contactNo
//        examCentreIns.rooms= Integer.parseInt(params.rooms)
        examCentreIns.address= params.address
       if(examCentreIns.save(flush: true)){
           flash.message = "${message(code: 'centre.created.message')}"
           redirect(action: "createNewCentre")
       }

    }
    def viewExaminationCentre(){


    }
    def getCentreList = {
        println("in getCentreList "+ params)
       def examinationCentreList = ExaminationCentre.list()
        println("<<>><>><<>><><><<<< "+ params.edit)
        if(examinationCentreList){
        render(template: "listOfCentre", model: [centreList: examinationCentreList, edit:params.edit, delete:params.delete])
        }
        else
        {
            render "<h1>No Examination Centre Found</h1>"
        }
    }
    def updateExaminationCentre= {

    }
    def editExaminationCentre ={
        println(params.id)
        def examinationCentreInstance = ExaminationCentre.findById(params.id)
        println("location of examination centre is "+ examinationCentreInstance.location)
        return [examinationCentreInstance: examinationCentreInstance]
    }
    def updateCentre ={


            println("update centre "+ params.id)
            def examCentreIns =  ExaminationCentre.get(params.id)
            examCentreIns.location= params.location;
            examCentreIns.capacity=Integer.parseInt(params.capacity)
            examCentreIns.name=params.centreName
            examCentreIns.contactNo= params.contactNo
//            examCentreIns.rooms= Integer.parseInt(params.rooms)
            examCentreIns.address= params.address
            if(examCentreIns.save(flush: true)){
                println("updated succesfully")
                flash.message = "${message(code: 'centre.updated.message', args: [message( default: 'ExaminationCentre'),   examCentreIns.id])}"
                render(view: "editExaminationCentre" , model:[examinationCentreInstance:examCentreIns])
            }

        }

    def deleteExaminationCentre={

    }
    def deleteCentre={
        println('in delete Centre')
        def examCentreInstance = ExaminationCentre.get(params.id)
        examCentreInstance.delete(flush: true)
        flash.message = "${message(code: 'centre.deleted.message')}"
        redirect(action: "deleteExaminationCentre")
    }
    def create={

    }

}
