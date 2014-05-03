package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured


class ExaminationCenterController {
    def examinationCentreService

    def index() {}

    @Secured("ROLE_ADMIN")
    def createNewCentre(){
    }
    def saveExaminationCentre = {
        println("hello kuldeep in examination centre")
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
        def associatedExamVenue
        if(params.programList){
        associatedExamVenue= examinationCentreService.associatedExamVenue(params)
        }

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
        println("in getCentreList "+ params.edit)
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
    def updateExaminationCentre= {


        def districtList=District.list(sort:'districtName')
        def edit= "edit"
        [districtList:districtList,edit: edit]
    }

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
//                println("updated succesfully")
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
            def tmp=[]
            def examCentreInstance = ExaminationCentre.get(params.id)
            examCentreInstance.student.each { tmp << it }
            tmp.each { examCentreInstance.removeFromStudent(it) }
            def programExamVenue = ProgramExamVenue.findAllByExamCenter(examCentreInstance)
            programExamVenue.each {
                it.delete(flush: true)
            }
            examCentreInstance.delete(flush: true)
            flash.message = "${message(code: 'centre.deleted.message')}"
            redirect(action: "updateExaminationCentre")
        }
      catch (Exception e){
          flash.message = "${message(code: 'centre.cannotDeleted.message')}"
          redirect(action: "updateExaminationCentre")
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

    def getProgrammeList() {
        println("this is the id of venue" +params.int('data'))
        try{
            ExaminationCentre examinationCentre = ExaminationCentre.get(params.int('data'));

            def programmeList = null
            if (examinationCentre != null) {
                programmeList = ProgramExamVenue.findAllByExamCenter(examinationCentre).courseDetail
                println("list of programs"+programmeList)
                render programmeList as JSON
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
    @Secured("ROLE_ADMIN")
    def createExamCentre={
        def districtList=District.list(sort:'districtName')
        [districtList:districtList]
    }
    def saveExamCentre={
        Boolean flag = examinationCentreService.saveExamCentres(params)
        if(flag){
            flash.message =  "Examination Centre Saved Succesfully"
        }
        else{
            flash.message =  "Examination Centre Not Saved"
        }
        redirect(action: "createExamCentre")
    }

}
