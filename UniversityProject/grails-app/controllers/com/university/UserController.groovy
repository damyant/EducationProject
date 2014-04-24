
package com.university



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService
    def springSecurityService
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


//    def saveUser(User userInstance) {
//
//        userInstance = new User(params)
//        def role=Role.findByAuthority(params?.userRole)
//        if (userInstance.save(flush: true)) {
//            UserRole.create userInstance, role
//            redirect(action: "index")
//        }
//        else {
//            render(view: "createUser", model: [userInstance: userInstance])
//        }
//    }

    def save = {

        def userInstance = new User(params)
        def checked = params.list('myCheckbox')
        def roleList=Role.getAll()
        if (userInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
            for(int i=0;i<checked.size();i++){
                def role=Role.findById(checked[i])
                UserRole.create userInstance, role

            }
            redirect(action: "index")
        }
        else {

            render(view: "createUser", model: [userInstance: userInstance])

        }
    }




    def editUser= {
        def currentUser= springSecurityService.getCurrentUser().getUsername()
        def boolean compare= false
        def userInstance = User.get(params.id)
        if(currentUser==userInstance.username){
            compare=true
        }
        def userRoles = UserRole.findAllByUser(userInstance)*.role
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message( default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {

            def roles=Role.getAll()
            println(roles.id)
            println(userRoles)
            return [userInstance: userInstance,roles:roles, userRoles:userRoles, compare:compare]
        }
    }


//    def update(User userInstance) {
//        userInstance = User.get(params.id)
//        def role=Role.findByAuthority(params?.userRole)
//        if (userInstance) {
//            if (params.version) {
//                def version = params.version.toLong()
//                if (userInstance.version > version) {
//                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
//                    render(view: "edit", model: [userInstance: userInstance])
//                    return
//                }
//            }
//            userInstance.properties = params
//            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
//                if(role)
//                    userService.updateUserRole(userInstance,role)
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
//                redirect(action: "userList", id: userInstance.id)
//            }
//            else {
//                render(view: "editUser", model: [userInstance: userInstance])
//            }
//        }
//        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
//            redirect(action: "userList")
//        }
//    }


    def updateUser = {
        println("hello kuldeeppppppppppppppppppppppppppp")
        def UserInstance = User.get(params.id)
        if (UserInstance) {
            if (params.version) {

                def version = params.version.toLong()
                if (UserInstance.version > version) {

                    UserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message( default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "editUser", model: [UserInstance: UserInstance])
                    return
                }
            }

            UserInstance.properties=params

            def secUserSecRoleInstance = UserRole.findAllByUser(UserInstance)
            secUserSecRoleInstance.each{
                it.delete()
            }
            def checked = params.list('myCheckbox')
            if (UserInstance.save(flush: true)) {

                flash.message = "${message(code: 'default.updated.message', args: [message( default: 'User'),   UserInstance.id])}"
                for(int i=0;i<checked.size();i++){
                    def role=Role.findById(checked[i])
                    UserRole.create    UserInstance, role

                }

                redirect(action: "index", id:    UserInstance.id)
            }
            else {

                log.debug "-----------" +    UserInstance.errors
                render(view: "editUser", model: [userInstance:UserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message( default: 'User'), params.id])}"
            redirect(action: "index")
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


