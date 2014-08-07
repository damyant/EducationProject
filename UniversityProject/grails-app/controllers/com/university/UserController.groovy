package com.university

import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.StudyCenter
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import javax.validation.constraints.Null

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService
    def springSecurityService

    @Secured(["ROLE_ADMIN"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model: [userInstanceCount: User.count()]
    }

    def show(User userInstance) {
        respond userInstance
    }

    def resetPassword() {

    }


    @Transactional
    @Secured(["ROLE_ADMIN"])
    def updatePwd() {
        def userInstance = User.findById(params.id)
        userInstance.password = params.newPwd
        userInstance.save(flush: true)

        flash.message = "${message(code: 'password.reset.msg', args: [userInstance.username, userInstance.email])}"
        redirect(action: "userlist")
    }

    @Secured(["ROLE_ADMIN"])
    def createUser() {
        def userInstance = new User()
        userInstance.properties = params
        def stydyCentreList = StudyCenter.list(sort: 'name')
        def programList = ProgramSession.list()
        def roleList = userService.getRoleList()
        [userInstance: userInstance, roles: roleList, stydyCentreList: stydyCentreList, programList: programList]
    }

    @Secured(["ROLE_ADMIN"])
    def userlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model: [userInstanceCount: User.count()]

    }

    def getProgramList = {

        def programListMap = []
        def programList = ProgramSession.list()
        programList.each {
            def returnMap = [:]
            returnMap.id = it.id
            returnMap.programName = it.programDetailId.courseName
            returnMap.noOfSemester = it.programDetailId.noOfTerms
            returnMap.semesterList = Semester.findAllByProgramSession(it)
            println("-----------------"+Semester.findAllByProgramSession(it))
            programListMap.add(returnMap)
        }

        render programListMap as JSON
    }

    @Secured(["ROLE_ADMIN"])
    def save = {
        println("________________________" + params)
        def result=userService.saveUserDetails(params)
       if(result.status){
           flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), result.userInstance.id])}"
           redirect(action: "index")
        } else {
            render(view: "createUser", model: [userInstance: result.userInstance])
        }
    }
    @Secured(["ROLE_ADMIN"])


    def editUser = {
        def result=userService.editUserDetails(params)
        if (!result.status) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(default: 'User'), params.id])}"
            redirect(action: "list")
        } else {
            return [tab1HProgList: result.tab1HProgList, tab1HSemList: result.tab1HSemList, tab2HProgList: result.tab2HProgList, tab2HSemList: result.tab2HSemList, tab1OptionText: result.tab1OptionText, tab2OptionText: result.tab2OptionText, tab1OptionValue: result.tab1OptionValue, tab2OptionValue: result.tab2OptionValue, userInstance: result.userInstance, roles: result.roles, userRoles: result.userRoles, compare: result.compare, studyCentreList: result.studyCentreList, studyCentre: result.studyCentre]
        }
    }


    @Secured(["ROLE_ADMIN"])
    def updateUser = {

        def result=userService.updateUserDetails(params)
        if(result.userInstance){
            if(result.status){
                flash.message = "${message(code: 'default.updated.message', args: [message(default: 'User'), result.userInstance.id])}"

                redirect(action: "index", id: result.userInstance.id)
            } else {

                log.debug "-----------" + result.userInstance.errors
                render(view: "editUser", model: [userInstance: result.userInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(default: 'User'), params.id])}"
            redirect(action: "index")
        }
    }

    @Transactional
    @Secured(["ROLE_ADMIN"])
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush: true

        request.withFormat {
            form {

                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }


    def assignCourse(){
//        println('these are the parameters '+params)

        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def saveCourseForTabulator() {
        println('these are the parameters ' + params)
        def sizeP = ProgramDetail.list().size()
//        for(int i=1; i<=sizeP; i++){
//            def programVar = 'program'+i
//            println(programVar)
//            def a = params.(programVar)
//            println(a)
//            if(a){
//                println(params.program+''+i)
//               def p = params.program+''+i
//                p.each{
//                    println('this is the value '+ it)
//                }
//            }
//
//        }

    }
}


