
package com.university



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]
    }

    def show(User userInstance) {
        respond userInstance
    }

    def resetPassword(){

    }


    @Transactional
    def updatePwd(){
        println("?????????????????????"+params)
        def userInstance=User.findById(params.id)
        userInstance.password=params.newPwd
        userInstance.save(flush: true)
//        println newPassword
//        userInstance.password=newPassword
//         if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
//             mailerService.sendForgetPassword(newPassword,userInstance)

        flash.message = "${message(code:'password.reset.msg',args:[userInstance.username,userInstance.email])}"
        redirect(action: "userList")
//         }
        /* flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
         redirect(action: "userList")*/
    }

    def createUser() {
        def userInstance = new User()
        userInstance.properties = params
        def roleList=userService.getRoleList()
        [userInstance: userInstance,roles:roleList]
    }


    def userList(Integer max){
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]

    }

    @Transactional
    def saveUser(User userInstance) {

        userInstance = new User(params)
        def role=Role.findByAuthority(params?.userRole)
        if (userInstance.save(flush: true)) {
            UserRole.create userInstance, role
            redirect(action: "index")
        }
        else {
            render(view: "createUser", model: [userInstance: userInstance])
        }
    }

    def editUser(User userInstance) {
//        userInstance = User.get(params.id)

        def role
        if (!userInstance) {
            redirect(action: "index")
        }
        else {
            role=UserRole.findByUser(userInstance)
            println(role.role)
            return [userInstance: userInstance,role:role.role]
        }
    }

    @Transactional
    def update(User userInstance) {
        userInstance = User.get(params.id)
        def role=Role.findByAuthority(params?.userRole)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            userInstance.properties = params
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                if(role)
                    userService.updateUserRole(userInstance,role)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
                redirect(action: "userList", id: userInstance.id)
            }
            else {
                render(view: "editUser", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "userList")
        }
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush:true

        request.withFormat {
            form {

                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}


