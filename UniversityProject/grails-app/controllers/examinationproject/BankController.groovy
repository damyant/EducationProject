package examinationproject

import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


//ADDED BY RAJ
@Secured("ROLE_ADMIN")
class BankController {

    static allowedMethods = [save: "POST", update: "PUT", get:"GET", delete: "DELETE"]

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
//        println("params"+params)

      def bankInstance = Bank.findById(Integer.parseInt(params.bankId))
        bankInstance.bankName = params.bankName
       if(bankInstance.save(flush: true)){
           redirect(action: "bankList")
       }
    }

@Transactional
    def deleteBank() {
//        println("params"+params)
        def bankInstance = Bank.findById(Integer.parseInt(params.bankId))
       try {
           bankInstance.delete(flush: true)
           redirect(action: "bankList")
       }catch(Exception e){
          // e.printStackTrace()
         flash.message = "Unable To Delete Bank "
         redirect(action: "bankList")
       }

    }
}
