package universityproject

import com.university.Role
import com.university.User
import com.university.UserRole
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

/*import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils*/

@Transactional
class UserService {


    static transactional = true
    def springSecurityService
    def grailsApplication

    def serviceMethod() {

    }

    def iUserLoggedInAction(params) {
        def c = User.createCriteria()
        def userInstance = c.list {
            eq('username', params.username)
            eq('password', springSecurityService.encodePassword(params.password))
            eq('enabled', true)
            eq('accountExpired', false)
            eq('accountLocked', false)
            eq('passwordExpired', false)
        }[0]
        //def userInstance=User.findByUsernameAndPassword(params.username,springSecurityService.encodePassword(params.password))
        def role = Role.findByAuthority('ROLE_USER')
        def userRole = UserRole.findByUserAndRoleNotEqual(userInstance, role)
        if (userInstance && userRole) {
            return userInstance
        } else {
            return false
        }
    }

    def iUserLoggedIn() {
        try {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            if (session.user) {
                return session.user
            } else {
                return false
            }
        }
        catch (IllegalStateException ise) {
            log.warn("No WebRequest available!")
            return false
        }
    }

    def iUserLogOut() {
        try {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            session.user = ""

        }
        catch (IllegalStateException ise) {
            log.warn("No WebRequest available!")
        }
    }

    def updateUserRole(user, role) {
        //UserRole.create userInstance, role
        def uRole = UserRole.findAllByUser(user)
        uRole.each { ur ->
            ur.delete(flush: true)
        }
        UserRole.create user, role
    }

    def newPwd(Integer len = 10) {
        def AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /*
    * Return role list below the current role.
    * */

    def getRoleList() {
        /* def role=springSecurityService.getPrincipal().authorities*.authority[0]*/
        def rolesGrated = []
        def rolesList = Role.list()
        println(rolesList)
        /* rolesList.each {r ->
             if(SpringSecurityUtils.ifAnyGranted(r.authority) && r.authority!=role){
                 rolesGrated.add(r)
             }
         }*/

        println("Role List from User Service" + rolesList)
        return rolesList
    }

    /*
    * Return user list below the current user role.
    * */

    def getUsersList() {
        def roleList = getRoleList()
        def userList = []
        User.list().each { u ->
            def flag = true
            roleList.each { r ->
                if (r.authority == u.getAuthorities().authority[0]) flag = false
            }
            if (!flag) userList.add(u)
        }
        return userList
    }

}