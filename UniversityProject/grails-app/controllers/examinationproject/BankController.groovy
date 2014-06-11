package examinationproject



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BankController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def bankList(Integer max) {
//        params.max = Math.min(max ?: 20, 100)
        respond Bank.list(params), model: [bankInstanceCount: Bank.count()]
    }


    def createBank() {
        respond new Bank(params)
    }

    @Transactional
    def saveBank() {
          if(new Bank(bankName: params.bankName).save(flush: true)){
            redirect(action: "bankList")
        }


    }

    def editBank() {
        def bankInstance = Bank.findById(Integer.parseInt(params.bankId))
        respond bankInstance
    }

    @Transactional
    def updateBank() {

      def bankInstance = Bank.findById(Integer.parseInt(params.bankId))

       if(bankInstance.save(flush: true)){
           redirect(action: "bankList")
       }
    }


    def delete() {
        println("params"+params)
        def bankInstance = Bank.findById(Integer.parseInt(params.bankId))
       if(bankInstance.delete(flush: true)){
           redirect(action: "bankList")
       }
    }
}
