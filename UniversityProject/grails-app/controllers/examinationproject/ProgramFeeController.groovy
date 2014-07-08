package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


//@Transactional
class ProgramFeeController {
    def programFeeService
    def feeDetailService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(["ROLE_ADMIN","ROLE_ACCOUNT"])
    def listOfFeeType = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def c=FeeSession.createCriteria()
        def programFeeInstanceList=c.list(params){

        }
        [programFeeInstanceList:programFeeInstanceList, admissionFeeTotal: FeeSession.count()]
    }

//    def show(ProgramFee programFeeInstance) {
//        respond programFeeInstance
//    }
    @Secured("ROLE_ADMIN")
    def createNewFeeType() {
        def feeType=null
        def programDetailList
        if(FeeType.count()>0)
            feeType = FeeType.findAllByShowValue(true)

        def programSessionList=FeeSession.list()
        if(programSessionList.programDetailId.id){
           programDetailList = ProgramDetail.findAllByIdNotInList(programSessionList.programDetailId.id)
        }
        else{
             programDetailList = ProgramDetail.list(sort:'courseCode')
        }

        def programSessions=   programFeeService.getProgramSessions(params)
        println("::::::::::::::::::::::::::::::: "+programDetailList)

         [feeType:feeType,programDetailList:programDetailList,programSessions:programSessions]

    }
    @Secured("ROLE_ADMIN")
    def createAdmissionFee() {
        def feeType=null
        def programDetailList
        if(FeeType.count()>0)
            feeType = FeeType.findAllByShowValue(true)

        def programSessionList=FeeSession.list()
//        if(programSessionList.programDetailId.id){
//            programDetailList = ProgramDetail.findAllByIdNotInList(programSessionList.programDetailId.id)
//        }
//        else{
            programDetailList = ProgramDetail.list(sort:'courseCode')
//        }
        def programSessions=   programFeeService.getProgramSessions(params)
        def admissionFeeInst,programInst,termList,allTerm, sessionValue
        if(params.id){
            admissionFeeInst=AdmissionFee.findById(params.id)
            termList=admissionFeeInst.term
            sessionValue=admissionFeeInst.feeSession.sessionOfFee
            allTerm=Semester.findAllByProgramSession(ProgramSession.findByProgramDetailId(admissionFeeInst.feeSession.programDetailId))
            programInst=admissionFeeInst.feeSession
        }
            [feeType:feeType,admissionFeeInst:admissionFeeInst,sessionValue:sessionValue,allTerm:allTerm,termList:termList,programInst:programInst, programDetailList:programDetailList,programSessions:programSessions]


    }
    @Secured("ROLE_ADMIN")
    def saveProgramFee(){
        def response
        def feeTypeList=params.feeTypeList.split(',')

        def result=programFeeService.saveProgramFeeType(params)
        response=[status:result]
        render response as JSON

    }

    @Secured("ROLE_ADMIN")
    def saveAdmissionFee(){
        println(params)
        def response
        def result=programFeeService.saveAdmissionFeeType(params)
        if(result){
            flash.message = "Saved Succesfully."
        }else{
            flash.message = "Unable to saved Succesfully."
        }
        redirect(action: "createAdmissionFee")

    }

    def getAdmissionFeeDetails(){
        def response=[:]
        def programDetailIns = ProgramDetail.findById(Integer.parseInt(params.program))
        def sessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.session)
        def admissionInst=AdmissionFee.findByFeeSessionAndTerm(sessionObj,Integer.parseInt(params.term))
        println(admissionInst)
        println(admissionInst.feeAmountAtIDOL)
        response.idolFee=admissionInst.feeAmountAtIDOL
        response.stFee=admissionInst.feeAmountAtSC
        response.lateFee=admissionInst.lateFeeAmount
        render response as JSON
    }
    @Secured("ROLE_ADMIN")
    def editFeeType  =  {
        def programFeeSessionInstance = FeeSession.findById(Integer.parseInt(params.id))
        def program = programFeeSessionInstance.programDetailId
//        def programSession = programFeeInstance.programSession
        def feeType

        def programSessions=   programFeeService.getProgramSessions(params)

        def miscFee
        List<MiscellaneousFee> miscellaneousFeeList = []
        if(FeeType.count()>0){
        feeType = FeeType.list()
            feeType.each {
                miscFee=FeeType.findAllByShowValue(true)
                def miscellaneousFee= MiscellaneousFee.findByFeeTypeAndFeeSession(FeeType.findById(it.id),programFeeSessionInstance)
                if(miscellaneousFee)
                miscellaneousFeeList.add(miscellaneousFee)
            }
            [programFeeInstance:programFeeSessionInstance,miscellaneousFeeList:miscellaneousFeeList,programSessions:programSessions,miscFee:miscFee]
        }else{
            [programFeeInstance:programFeeSessionInstance,programSessions:programSessions]
        }


    }

    @Transactional
    @Secured("ROLE_ADMIN")
    def update(AdmissionFee programFeeInstance) {


        if (programFeeInstance.hasErrors()) {
            respond programFeeInstance.errors, view: 'editFeeType'
            return
        }

        programFeeService.saveProgramFeeType(params)

        redirect(action: "listOfFeeType")

    }

//    @Transactional
    @Secured("ROLE_ADMIN")
    def deleteFeeType= {
        programFeeService.deleteFeeType(params)
        redirect(action: "listOfFeeType")

    }

    @Secured("ROLE_ADMIN")
    def addFeeType = {
        def feeInstance = FeeType.get(params?.feeTypeId);
        def programList = ProgramDetail.list(sort:'courseCode')
        [feeInstance: feeInstance,programList: programList]
    }
    @Secured("ROLE_ADMIN")
    def saveNewFee = {

        def feeTypeInst
        if (params?.feeId) {
            feeTypeInst = FeeType.findById(Long.parseLong(params.feeId))
//            println(feeTypeInst.type);
            feeTypeInst.type = params?.type
            feeTypeInst.showValue=true
           } else {
            feeTypeInst = new FeeType(type: params.type,showValue: true)
        }
        Boolean flag = false
        if (feeTypeInst.save(flush: true)) {
            flag = true
        }
        if (flag && params?.feeId) {
            flash.message = "Fee Type Updated Successfully";
        }
        else if (flag) {
            flash.message = "New Fee Type Saved Successfully";
        }
        else {
            flash.message = "Unable to Add New Fee Type.";
        }
        redirect(action: "addFeeType")
    }
    @Secured(["ROLE_ADMIN","ROLE_ACCOUNT"])
    def viewExistingFeeType = {
        def feeTypeList = FeeType.findAllByShowValue(true);
        [feeTypeList: feeTypeList]
    }
    @Secured("ROLE_ADMIN")
    def deleteFeesType = {
        try {
//            println("*&&&&&&&&&"+params.int('data'));

            def status=feeDetailService.deleteFee(params)
            if(status==true){
            flash.message = "Deleted Successfully"
            redirect(action: "viewExistingFeeType")
            }
            else{

                flash.message = "Problem in deleting Fee Type"
                redirect(action: "viewExistingFeeType")
            }
        }
        catch (Exception e) {
            println("<<<<<<<<<<<Problem in deleting study center$e")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER", "ROLE_ACCOUNT"])
    def getProgramSession = {
     def programSessions=   programFeeService.getProgramSessions(params)
        render programSessions as JSON
    }
    @Secured(["ROLE_ADMIN","ROLE_ACCOUNT"])
    def isFeeCreated = {
        def response
        try{
            def program = ProgramDetail.findById(Integer.parseInt(params.programDetail))
            def session = ProgramSession.findBySessionOfProgram(params.session)
            def admissionFee = AdmissionFee.findByFeeSession(FeeSession.findByProgramDetailId(params.programDetail))
//            AdmissionFee.findByFeeSession(FeeSession.findByProgramDetailId(student.programDetail[0]));

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
    def getTermInList={
        def response=[:]
        def program = ProgramDetail.findById(Integer.parseInt(params.data))
        if(program.programType==ProgramType.findById(1)){
            response.programlist=program.noOfAcademicYears
            println("program.noOfAcademicYears"+program.noOfAcademicYears)
        }
        else{
            response.programlist=program.noOfTerms
            println("program.noOfTerms"+program.noOfTerms)
        }
        render response as JSON
    }
    def listOfAdmissionFee={
        def programDetailList = ProgramDetail.list(sort:'courseCode')
        def programSessions=   programFeeService.getProgramSessions(params)
        [programDetailList:programDetailList,programSessions:programSessions]
    }
    def loadAdmissionFee={
        def response=[:]
        def programInst=ProgramDetail.findById(Integer.parseInt(params.program))
        def feeSessObj=FeeSession.findByProgramDetailIdAndSessionOfFee(programInst,params.session)
        def admissionFeeList=AdmissionFee.findAllByFeeSession(feeSessObj,[sort:'term'])
        response.admissionFeeList=admissionFeeList
        render response as JSON
    }
//    def isValidTerm={
//        def response=[:]
//        def programType=ProgramDetail.findById(params.pId).programType.id
//        if(programType==1){
//            if(ProgramDetail.findById(params.pId).noOfAcademicYears>=Integer.parseInt(params.term)){
//                response.status=true
//            }
//            else
//            {
//                response.status=false
//            }
//        }
//        else{
//            if(ProgramDetail.findById(params.pId).noOfTerms>=Integer.parseInt(params.term)){
//                response.status=true
//            }
//            else
//            {
//                response.status=false
//            }
//        }
//        render response as JSON
//    }
}
