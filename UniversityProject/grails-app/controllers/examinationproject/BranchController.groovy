package examinationproject

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BranchController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def branchList(Integer max) {
        def bankInstanceList = Bank.list()
        [bankInstanceList:bankInstanceList]
    }

    def getBranchList = {
        def list = Bank.findById(Integer.parseInt(params.bank));
        println(list.branch[0].id)
        render list.branch as JSON
    }

    def createBranch() {
        def bankInstanceList = Bank.list()
        [bankInstanceList:bankInstanceList]
    }

    @Transactional
    def saveBranch() {
       println("params"+params)
       def branch =  new Branch(branchLocation: params.branchName).save(flush: true)
        Set<Branch> branches = new HashSet<Branch>()
        branches.add(branch)
        def bank = Bank.findById(Integer.parseInt(params.bankId[0]))
        bank.branch=branches
       if(bank.save(flush: true)){
          redirect(action: "branchList")
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

        def tmp=[]
        studyCenter.student.each { tmp << it }
        tmp.each { studyCenter.removeFromStudent(it) }
        def branch = Branch.findById(Integer.parseInt(params.branchId))
        branch.delete(flush: true)
    }

}
