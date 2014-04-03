package examinationproject

import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
@Secured("ROLE_ADMIN")
@Transactional
class ProgramFeeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def listOfFeeType(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ProgramFee.list(params), model: [programFeeInstanceCount: ProgramFee.count()]
    }

    def show(ProgramFee programFeeInstance) {
        respond programFeeInstance
    }

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

        programFeeInstance.save flush: true
        redirect(action: "listOfFeeType")
//        request.withFormat {
//            form {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'programFeeInstance.label', default: 'ProgramFee'), programFeeInstance.id])
//                redirect programFeeInstance
//            }
//            '*' { respond programFeeInstance, [status: CREATED] }
//        }
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

        programFeeInstance.save flush: true

        redirect(action: "listOfFeeType")

//        request.withFormat {
//            form {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'ProgramFee.label', default: 'ProgramFee'), programFeeInstance.id])
//                redirect programFeeInstance
//            }
//            '*' { respond programFeeInstance, [status: OK] }
//        }
    }

    @Transactional
    def deleteFeeType(ProgramFee programFeeInstance) {

        if (programFeeInstance == null) {
            notFound()
            return
        }

        programFeeInstance.delete flush: true
        redirect(action: "listOfFeeType")

//        request.withFormat {
//            form {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ProgramFee.label', default: 'ProgramFee'), programFeeInstance.id])
//                redirect action: "listOfFeeType", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
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
}
