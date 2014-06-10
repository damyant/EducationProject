package examinationproject



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BankController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bank.list(params), model: [bankInstanceCount: Bank.count()]
    }

    def show(Bank bankInstance) {
        respond bankInstance
    }

    def create() {
        respond new Bank(params)
    }

    @Transactional
    def save(Bank bankInstance) {
        if (bankInstance == null) {
            notFound()
            return
        }

        if (bankInstance.hasErrors()) {
            respond bankInstance.errors, view: 'create'
            return
        }

        bankInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bankInstance.label', default: 'Bank'), bankInstance.id])
                redirect bankInstance
            }
            '*' { respond bankInstance, [status: CREATED] }
        }
    }

    def edit(Bank bankInstance) {
        respond bankInstance
    }

    @Transactional
    def update(Bank bankInstance) {
        if (bankInstance == null) {
            notFound()
            return
        }

        if (bankInstance.hasErrors()) {
            respond bankInstance.errors, view: 'edit'
            return
        }

        bankInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Bank.label', default: 'Bank'), bankInstance.id])
                redirect bankInstance
            }
            '*' { respond bankInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Bank bankInstance) {

        if (bankInstance == null) {
            notFound()
            return
        }

        bankInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Bank.label', default: 'Bank'), bankInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankInstance.label', default: 'Bank'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
