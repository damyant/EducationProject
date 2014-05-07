package examinationproject

import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured("ROLE_ADMIN")
@Transactional
class ProgramFeeController {
    def programFeeService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def listOfFeeType(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ProgramFee.list(params), model: [programFeeInstanceCount: ProgramFee.count()]
    }

//    def show(ProgramFee programFeeInstance) {
//        respond programFeeInstance
//    }

    def createNewFeeType() {
        respond new ProgramFee(params)
    }

    @Transactional
    def save(ProgramFee programFeeInstance) {
        if (programFeeInstance == null) {
            notFound()
            return
        }

        if (programFeeInstance.hasErrors()) {
            respond programFeeInstance.errors, view: 'createNewFeeType'
            return
        }

        programFeeService.saveProgramFeeType(programFeeInstance)
        redirect(action: "listOfFeeType")

    }

    def editFeeType(ProgramFee programFeeInstance) {

        respond programFeeInstance
    }

    @Transactional
    def update(ProgramFee programFeeInstance) {
        if (programFeeInstance == null) {
            notFound()
            return
        }

        if (programFeeInstance.hasErrors()) {
            respond programFeeInstance.errors, view: 'editFeeType'
            return
        }

        programFeeService.saveProgramFeeType(programFeeInstance)

        redirect(action: "listOfFeeType")

    }

//    @Transactional
    def deleteFeeType(ProgramFee programFeeInstance) {
//        println("ffffffff****************")

        if (programFeeInstance == null) {
            notFound()
            return
        }

        programFeeService.deleteFeeType(programFeeInstance)
        redirect(action: "listOfFeeType")

    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'programFeeInstance.label', default: 'ProgramFee'), params.id])
                redirect action: "listOfFeeType", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
    def addFeeType = {
        def feeInstance = FeeType.get(params?.feeTypeId);
        [feeInstance: feeInstance]
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
            feeTypeInst = new FeeType()
            feeTypeInst.type = params?.type
            println("hoooooo");
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
            println("<<<<<<<<<<<Problem in deleting study center" + e)
        }
    }
}
