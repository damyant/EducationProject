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
//        println("hello kuldeep in examination centre")
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
            println("???????????")
            render centre as JSON
        } else {
            render "<h5>No Examination Centre Found</h5>"
        }
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
//        println("========="+examinationVenueInstance)
//        println(examinationVenueInstance.properties)
//        println("location of examination centre is "+ examinationCentreInstance.city.district.districtName)
        return [examinationVenueInstance: examinationVenueInstance]
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
        def districtList=District.list(sort:'districtName')
        [districtList:districtList]
    }
    def saveExamCentre={
        int flag = examinationCentreService.saveExamCentres(params)
        if(flag==2){
            flash.message =  "Examination Centre Saved Successfully"
        }
        else if(flag==1){
            flash.message =  "Examination Centre Already Exist"
        }
        else{
            flash.message =  "Examination Centre Not Saved"
        }
        redirect(action: "createExamCentre")
    }


    def getExamCenterList() {

        try{
            District district = District.get(params.int('data'));
            def examCenterList = null
            if (district != null) {
                examCenterList = City.findAllByDistrictAndIsExamCentre(district,1,[sort: 'cityName'])
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

}
