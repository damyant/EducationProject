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
        Boolean flag = examinationCentreService.saveCentres(params)
//        println("*********")
        if(flag){
            render   "${message(code: 'centre.created.message')}"
        }
        else{
             render "Centres Not Saved"
        }
    }

    @Secured(["ROLE_ADMIN","ROLE_ACCOUNT"])
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
            centre.status=false
        }
        render centre as JSON
    }
    def getCentreList = {
//        println("in getCentreList "+ params)
        def result= examinationCentreService.examVenueList(params)
//        println("in result "+ result)
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
//        println("========="+params.id)
        def examinationVenueInstance = ExaminationVenue.findById(params.id)
        def examCenterList = City.findAllByIsExamCentre(1,[sort: 'cityName'])
        Set <ExaminationVenue> exmVenue=ExaminationVenue.findAllById(params.id)
        def obj=City.createCriteria()
        def examCentre=obj.list {
            examVenue{
                eq('id', examinationVenueInstance.id)
            }


        }
        return [examinationVenueInstance: examinationVenueInstance, examCenterList:examCenterList, examCentre:examCentre]
    }
    def updateCentre ={
//        println("?????????/"+params)
            def examCentreIns =  ExaminationVenue.get(params.id)
           def isSaved= examinationCentreService.updateExaminationCentre(params)
            if(isSaved){
//                println("updated succesfully")
                flash.message = "${message(code: 'centre.updated.message', args: [message( default: 'ExaminationVenue'),   examCentreIns.id])}"
                render(view: "editExaminationCentre" , model:[examinationCentreInstance:examCentreIns])
            }
        else{
                flash.message = "${message(code: 'centre.notUpdated.message', args: [message( default: 'ExaminationVenue'),   examCentreIns.id])}"
                render(view: "editExaminationCentre" , model:[examinationCentreInstance:examCentreIns])
            }

        }

    @Secured("ROLE_ADMIN")

    def deleteCentre={
        try {

           examinationCentreService.deletionExamVenue(params)
           flash.message = "${message(code: 'centre.deleted.message')}"
            redirect(action: "updateExaminationCentre")
        }
      catch (Exception e){
          flash.message = "${message(code: 'centre.cannotDeleted.message')}"
          redirect(action: "updateExaminationCentre")
      }


    }

    def getExaminationVenueList(){
        try{
            City city = City.get(params.int('data'));

            def venueList = null
            if (city != null) {
                venueList= city.examVenue
                render venueList as JSON
            } else {
                render null
            }
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting city list" + e)
        }
    }

    def getProgrammeList() {
//        println("this is the id of venue" +params.int('data'))
        try{
            ExaminationVenue examinationCentre = ExaminationVenue.get(params.int('data'));

            def programmeList = null
            if (examinationCentre != null) {
                programmeList = ProgramExamVenue.findAllByExamVenue(examinationCentre).courseDetail
//                println("list of programs"+programmeList)
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
        def districtList=City.list()*.district as Set
        def finalDistrictList= districtList.sort{a,b->
            a.districtName<=>b.districtName
        }
        [districtList:finalDistrictList]

    }

    def checkCenterCode(){
        def status=[:]
        def centerCodeIns=ExaminationVenue.findByCentreCode(Integer.parseInt(params.centerCode))
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
        def cityInst=City.findById(params.id)
        def districtList=District.list(sort:'districtName')
        [districtList:districtList,cityInst:cityInst]
    }
    def saveExamCentre= {
        int flag = examinationCentreService.saveExamCentres(params)
        if (flag == 2) {
            if (params.isCity) {
                flash.message = "City Saved Successfully"
            } else {
                flash.message = "Examination Centre Saved Successfully"
            }
        } else if (flag == 1) {
            if (params.isCity) {
                flash.message = "City Already Exist"
            } else {
                flash.message = "Examination Centre Already Exist"
            }
        } else {
            if (params.isCity) {
                flash.message = "City Not Saved"
            } else {
                flash.message = "Examination Centre Not Saved"
            }
        }
        if (params.isCity) {
            redirect(action: "createNewCity")
        } else {
            redirect(action: "createExamCentre")
        }
    }

    def getExamCenterName() {
        try{
            District district = District.get(params.int('data'));
            if (district != null) {
                def examCenterList = City.findAllByDistrictAndIsExamCentre(district,1,[sort: 'cityName'])
//                println(examCenterList)
                render examCenterList as JSON
            } else {
                render null
            }
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting exam Center List list" + e)
        }
    }
    @Secured("ROLE_ADMIN")
    def listOfCity(){
        def districtList=District.list(sort:'districtName')
        [districtList:districtList]
    }
    @Secured("ROLE_ADMIN")
    def createNewCity(){
        def cityInst=City.findById(params.id)
        def districtList=District.list(sort:'districtName')
        [districtList:districtList,cityInst:cityInst]
    }
    @Secured("ROLE_ADMIN")
    def deleteCity(){
//        println("dsdsdsds"+params)
            try {

                def status=examinationCentreService.deletionCity(params)
//                println(status)
                if(status) {
                    flash.message = "City Removed Successfully"

                }
            }
            catch (Exception e){
                flash.message = "Unable To Remove City"
            }
        redirect(action: "listOfCity")
    }
    @Secured("ROLE_ADMIN")
    def listOfExamCentre(){
        def districtList=District.list(sort:'districtName')
        [districtList:districtList]
    }
}
