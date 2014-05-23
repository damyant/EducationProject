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

//        def newProgramDetailList =[]
//        programDetailList.each{
//          if(!AdmissionFee.findByProgramDetail(it)){
//              newProgramDetailList.add(it)
//        }
       // }
         [feeType:feeType,programDetailList:programDetailList]

    }


    def saveProgramFee(){
        def response
        println("innnnnnnnnnn=="+params)
        def feeTypeList=params.feeTypeList.split(',')
        println("innnnnn"+params.programSession+"nnnnn=="+feeTypeList[0])

        def result=programFeeService.saveProgramFeeType(params)
        response=[status:result]
        render response as JSON
//        redirect(action: "listOfFeeType")
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

    def isFeeCreated = {
        def response
        try{
            def program = ProgramDetail.findById(Integer.parseInt(params.programDetail))
            def session = ProgramSession.findBySessionOfProgram(params.session)
            def admissionFee = AdmissionFee.findByProgramDetailAndProgramSession(program,session)

            def programName=program.courseName
            boolean status
            if(admissionFee)
              status=true
            else
              status = false

            response =[feeStatus:status,program:programName]
        }catch(Exception ex){
            println("problem in checking the existence of Fee"+ex.printStackTrace())
        }
        render response as JSON
    }
}
