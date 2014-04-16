package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured


class ExaminationCenterController {
    def examinationCentreService

    def index() {}

    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
    def viewExaminationCentre(){

        def districtList=District.list(sort:'districtName')
        [districtList:districtList]
    }

    def getExamCentreList = {
       def result = examinationCentreService.examVenueList(params)
       def associatedExamVenue= examinationCentreService.associatedExamVenue(params)
        println("result==="+result)
        println("exam==="+associatedExamVenue)
       def centre = [:]
        if (result) {
            centre.name = result.name
            centre.id = result.id
            centre.assocaitedExamVenue=associatedExamVenue
            render centre as JSON
        } else {
            render "<h5>No Examination Centre Found</h5>"
        }
    }
    def getCentreList = {
        println("in getCentreList "+ params)
        def result= examinationCentreService.examVenueList(params)

       if(result){
        render(template: "listOfCentre", model: [centreList: result, edit:params.edit, delete:params.delete])
        }
        else
        {
            render "<h5>No Examination Centre Found</h5>"
        }
    }

    @Secured("ROLE_ADMIN")
    def updateExaminationCentre= {}

    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
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

        try{
            City city = City.get(params.int('data'));
            def centreList = null
            if (city != null) {
                centreList = ExaminationCentre.findAllByCity(city,[sort:'name'])
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
    @Secured("ROLE_ADMIN")
    def create={
        def districtList=District.list(sort:'districtName')
        [districtList:districtList]

    }

    def checkCenterCode(){
        def status=[:]
        def centerCodeIns=ExaminationCentre.findByCentreCode(Integer.parseInt(params.centerCode))
        if(centerCodeIns){
            status.centerCode='true'
        }
        else{
            status.centerCode='false'
        }
        render status as JSON

    }
}
