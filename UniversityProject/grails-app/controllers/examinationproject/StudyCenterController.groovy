package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SecurityTagLib
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class StudyCenterController {

    def studyCenterInfoService
    def springSecurityService

    def index() {}


    @Secured("ROLE_ADMIN")
        def createNewStudyCenter() {
        def config = SpringSecurityUtils.securityConfig
        try {
         def studyCentreInstance=   studyCenterInfoService.studyCenterDetailInfo(params)
            def districtIns=District.list(sort:'districtName' )
            [studyCentreInstance:studyCentreInstance,districtIns:districtIns,]
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

    @Secured("ROLE_ADMIN")
    def deleteStudyCenter() {
        try{
//            println('in delete Centre')
            StudyCenter studyCenter = StudyCenter.get(params.int('data'))
            def tmp=[]
            studyCenter.student.each { tmp << it }
            tmp.each { studyCenter.removeFromStudent(it) }
//            studyCenter.delete()
            studyCenter.delete(flush: true)
            flash.message = "${message(code: 'centre.deleted.message')}"
            redirect(action: "updateStudyCentre")
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in deleting study center" + e)
        }

    }
    @Secured("ROLE_ADMIN")
    def viewStudyCentre() {
        def cityIns=City.list(sort:'cityName' )
        def districtList=District.list(sort:'districtName')
        [districtList:districtList,cityList:cityIns]

    }

    @Secured("ROLE_ADMIN")
    def updateStudyCentre(){
        redirect(action: "viewStudyCentre", params:['type':"update"])
    }



    def getStudyCenterList(){

        try{
        def status=[:]
        def result= studyCenterInfoService.studyCenterList(params)
        if(result)
        render result as JSON
        else
            status.flag="false"
        render status as JSON
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting study center list" + e)
        }
    }
def getDistrictStudyCenterList(){
        try{
        def status=[:]
        def resultMap=[:]
        def result= studyCenterInfoService.districtStudyCenterList(params)
        def cityList=City.findAllByDistrict(District.findById(params.data))
        if(result) {
            resultMap.result = result
            resultMap.cityList = cityList
            render resultMap as JSON
        }
        else
            status.flag="false"
        render status as JSON
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting study center list" + e)
        }
    }

    def getStudyCenterForECList(){

        try{
            def status=[:]
            def result= studyCenterInfoService.studyCenterForECList(params)
            if(result)
                render result as JSON
            else
                status.flag="false"
            render status as JSON
        }
        catch (Exception e){
            println("<<<<<<<<<<<Problem in getting study center list" + e)
        }
    }


    def getStudyCenterDetails(){
        def studyCenterDetails=[:]
        def studyCenterInst=StudyCenter.findById(params.centreId)
        studyCenterInst.name
        studyCenterDetails.studyCenterInst=studyCenterInst
        render studyCenterDetails as JSON
    }


    def getCityList() {

        try{
        District district = District.get(params.int('data'));
        def cityList = null
        if (district != null) {
            cityList = City.findAllByDistrict(district,[sort:'cityName'])
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
    def checkCenterCode(){
        def status=[:]
        def centerCodeIns=StudyCenter.findByCenterCode(params.centerCode)
        if(centerCodeIns){
            status.centerCode='true'
        }
        else{
            status.centerCode='false'
        }
        render status as JSON

    }

}