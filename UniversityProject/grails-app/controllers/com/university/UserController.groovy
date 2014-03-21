
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
            return [userInstance: userInstance,role:role.role]
        }
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'editUser'
            return
        }

        userInstance.save(flush:true)


        request.withFormat {
            form {
                          redirect(action: "index")
            }
            '*'{ respond userInstance, [status: OK] }
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


