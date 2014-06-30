package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured("ROLE_ADMIN")
class BranchController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def branchList(Integer max) {
        def bankInstanceList = Bank.list()
        [bankInstanceList:bankInstanceList]
    }

    def getBranchList = {
        def list = Bank.findById(Integer.parseInt(params.bank));
         println("this is the branch list "+list.branch)
         render list.branch as JSON
    }

    def createBranch() {
        def bankInstanceList = Bank.list()
        [bankInstanceList:bankInstanceList]
    }

    @Transactional
    def saveBranch() {
       println("params"+params)
//       def branch =  new Branch(branchLocation: params.branchName,bank: Integer.parseInt(params.bankId[0])).save(flush: true)
//        Set<Branch> branches = new HashSet<Branch>()
//        branches.add(branch)
//        def bank = Bank.findById(Integer.parseInt(params.bankId[0]))
//        bank.branch=branches
//       if(bank.save(flush: true)){
//         flash.message ="Branch Added Successfully"
//      }else{
//           flash.message ="Unable To Add Branch"
//       }
//        redirect(action: "createBranch")
        def branch = new Branch()
        branch.bank= Bank.findById(Integer.parseInt(params.bankId))
        branch.branchLocation= params.branchName
        if(branch.save(flush: true, failOnError: true)){
         flash.message ="Branch Added Successfully"
            redirect(action: "createBranch")
      }else{
           flash.message ="Unable To Add Branch"
            redirect(action: "createBranch")
       }


    }

    def editBranch() {
        if(params.branchId){
        def branch = Branch.findById(Integer.parseInt(params.branchId))
        [branch:branch]
        }
    }

    @Transactional
    def updateBranch() {
        def branch = Branch.findById(Integer.parseInt(params.branchId))
        branch.branchLocation = params.branchName
       if( branch.save(flush: true)){
           flash.message="Branch Updated Successfully"

       }else{
           flash.message="Unable to Update Branch"
       }
       redirect(action: "editBranch")

    }

    @Transactional
    def deleteBranch(Branch branchInstance) {
        def branch = Branch.findById(Integer.parseInt(params.branchId))
        branch.delete(flush: true)
        if(!Branch.exists(branch.id)){
           flash.message="Branch Successfully Deleted"
        }else{
            flash.message = "Unable To Delete Branch"
        }
        redirect(action: "branchList")
    }

}
