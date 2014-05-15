package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured("ROLE_ADMIN")
//@Transactional
class ProgramFeeController {
    def programFeeService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def listOfFeeType = {
        Integer max
        params.max = Math.min(max ?: 10, 100)
        def programFeeInstanceList
        programFeeInstanceList = AdmissionFee.list()

         [programFeeInstanceList:programFeeInstanceList]
    }

//    def show(ProgramFee programFeeInstance) {
//        respond programFeeInstance
//    }

    def createNewFeeType() {
        def feeType=null
        if(FeeType.count()>0)
            feeType = FeeType.list()
       def programDetailList = ProgramDetail.list()
         [feeType:feeType,programDetailList:programDetailList]

    }


    def saveProgramFee(){
        println("innnnnnnnnnn=="+params)
        def feeTypeList=params.feeTypeList.split(',')
        println("innnnnn"+params.programSession+"nnnnn=="+feeTypeList[0])

        programFeeService.saveProgramFeeType(params)
        redirect(action: "listOfFeeType")

    }

    def editFeeType  =  {


        def programFeeInstance = AdmissionFee.findById(Integer.parseInt(params.id))
        def program = programFeeInstance.programDetail
        def programSession = programFeeInstance.programSession
        def feeType
        List<MiscellaneousFee> miscellaneousFeeList = []
        if(FeeType.count()>0){
        feeType = FeeType.list()
            feeType.each {
                println("Hello")
                def miscellaneousFee= MiscellaneousFee.findByFeeTypeAndProgramDetailAndProgramSession(FeeType.findById(it.id),program,programSession)
                if(miscellaneousFee)
                miscellaneousFeeList.add(miscellaneousFee)
                println("fdsgfhsdgfsdhf"+miscellaneousFee)
            }
            [programFeeInstance:programFeeInstance,miscellaneousFeeList:miscellaneousFeeList]

        }else{
            [programFeeInstance:programFeeInstance]
        }


    }

    @Transactional
    def update(AdmissionFee programFeeInstance) {


        if (programFeeInstance.hasErrors()) {
            respond programFeeInstance.errors, view: 'editFeeType'
            return
        }

        programFeeService.saveProgramFeeType(params)

        redirect(action: "listOfFeeType")

    }

//    @Transactional
    def deleteFeeType= {
        programFeeService.deleteFeeType(params)
        redirect(action: "listOfFeeType")

    }

//    protected void notFound() {
//        request.withFormat {
//            form {
//                flash.message = message(code: 'default.not.found.message', args: [message(code: 'programFeeInstance.label', default: 'ProgramFee'), params.id])
//                redirect action: "listOfFeeType", method: "GET"
//            }
//            '*' { render status: NOT_FOUND }
//        }
//    }
    def addFeeType = {
        def feeInstance = FeeType.get(params?.feeTypeId);
        def programList = ProgramDetail.list(sort:'courseName')
        [feeInstance: feeInstance,programList: programList]
    }
    def saveNewFee = {
        println(params?.feeId)
        def feeTypeInst
        if (params?.feeId) {
            feeTypeInst = FeeType.findById(Long.parseLong(params.feeId))
            println(feeTypeInst.type);
            feeTypeInst.type = params?.type
            println("hiiiiiii");
        } else {
            feeTypeInst = new FeeType(type: params.type)

            println("hoooooo"+params.type);
        }
        Boolean flag = false
        if (feeTypeInst.save(flush: true)) {
            flag = true
        }
        if (flag) {
            flash.message = "New Fee Type Added Successfully";
        } else {
            flash.message = "Unable to Add New Fee Type.";
        }
        redirect(action: "addFeeType")
    }
    def viewExistingFeeType = {
        def feeTypeList = FeeType.list(sort: 'type');
        [feeTypeList: feeTypeList]
    }
    @Secured("ROLE_ADMIN")
    def deleteFeesType = {
        try {
            println("*&&&&&&&&&"+params.int('data'));
            FeeType feeType = FeeType.get(params.int('data'))
            feeType.delete(flush: true)
            flash.message = "Deleted Successfully"
            redirect(action: "viewExistingFeeType")
        }
        catch (Exception e) {
            println("<<<<<<<<<<<Problem in deleting study center$e")
        }
    }


    def getProgramSession = {
     def programSessions=   programFeeService.getProgramSessions(params)
        render programSessions as JSON
    }
}
