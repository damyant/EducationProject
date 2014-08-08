package universityproject

import com.university.Role
import com.university.TabulatorProgram
import com.university.TabulatorSemester
import com.university.User
import com.university.UserRole
import examinationproject.ProgramDetail
import examinationproject.Semester
import examinationproject.StudyCenter
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
//        println(rolesList)
        /* rolesList.each {r ->
             if(SpringSecurityUtils.ifAnyGranted(r.authority) && r.authority!=role){
                 rolesGrated.add(r)
             }
         }*/

//        println("Role List from User Service" + rolesList)
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
    def saveUserDetails(params){
        def resultMap=[:]
        def userInstance = new User(params)
        def checked = params.list('myCheckbox')
        def roleList = Role.getAll()
        if (userInstance.save(flush: true)) {
            for (int i = 0; i < checked.size(); i++) {
                def role = Role.findById(checked[i])
                UserRole.create userInstance, role

                if (checked[i] == '9') {
                    String[] tab1Prog
                    String[] tab1Sem
                    tab1Prog = (params.tab1Program).split(" ")
                    tab1Sem = (params.tab1Semester).toString().split("/")
                    for (int l = 0; l < tab1Prog.length; l++) {
                        def tabProgramInst = new TabulatorProgram()
                        tabProgramInst.program = ProgramDetail.findById(Integer.parseInt(tab1Prog[l]))
                        tabProgramInst.user = userInstance
                        tabProgramInst.role = role
                        String[] sem
                        if (tabProgramInst.save(flush: true)) {
                            sem = tab1Sem[l].split(",")
                            for (int j = 0; j < sem.length; j++) {
                                def tabSemesterInst = new TabulatorSemester()
                                tabSemesterInst.semesterId = Semester.findById(sem[j])
                                tabSemesterInst.tabulatorProgram = tabProgramInst
                                tabSemesterInst.save(flush: true)
                            }
                        }
                    }
                } else if (checked[i] == '10') {
                    String[] tab2Prog
                    String[] tab2Sem
                    tab2Prog = (params.tab2Program).toString().split(" ")
                    tab2Sem = (params.tab2Semester).toString().split("/")
                    for (int k = 0; k < tab2Prog.length; k++) {
                        def tabProgramInst = new TabulatorProgram()
                        tabProgramInst.program = ProgramDetail.findById(Integer.parseInt(tab2Prog[k]))
                        tabProgramInst.user = userInstance
                        tabProgramInst.role = role
                        String[] sem
                        if (tabProgramInst.save(flush: true)) {
                            sem = tab2Sem[k].split(",")
                            for (int j = 0; j < sem.length; j++) {
                                def tabSemesterInst = new TabulatorSemester()
                                tabSemesterInst.semesterId = Semester.findById(sem[j])
                                tabSemesterInst.tabulatorProgram = tabProgramInst
                                tabSemesterInst.save(flush: true)
                            }
                        }
                    }
                }
            }
            resultMap.userInstance=userInstance
            resultMap.status=true
        }
        else{
            resultMap.status=false
        }
        return resultMap
    }

   def updateUserDetails(params){
       def resultMap=[:]
       def UserInstance = User.get(params.id)
       if (UserInstance) {
           if (params.version) {

               def version = params.version.toLong()
               if (UserInstance.version > version) {

                   UserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(default: 'User')] as Object[], "Another user has updated this User while you were editing")
                   render(view: "editUser", model: [UserInstance: UserInstance])
                   return
               }
           }

           UserInstance.properties = params

           def secUserSecRoleInstance = UserRole.findAllByUser(UserInstance)
           secUserSecRoleInstance.each {
               it.delete()
           }
           def checked = params.list('myCheckbox')
           if (UserInstance.save(flush: true)) {
               def count = 0
               for (int i = 0; i < checked.size(); i++) {
                   def role = Role.findById(checked[i])
                   UserRole.create UserInstance, role
                   if (checked[i] == '9' || checked[i] == '10') {
                       if (count == 0) {
                           def tabProgramsList = TabulatorProgram.findAllByUser(UserInstance)
                           if (tabProgramsList) {
                               tabProgramsList.each {
                                   def tabSemLists = TabulatorSemester.findAllByTabulatorProgram(it)
                                   tabSemLists.each {
                                       it.delete()
                                   }
                                   it.delete()
                               }
                           }
                           count++
                       }
                   }
                   if (checked[i] == '9') {
                       String[] tab1Prog
                       String[] tab1Sem
                       tab1Prog = (params.tab1Program).split(" ")
                       tab1Sem = (params.tab1Semester).toString().split("/")
                       for (int l = 0; l < tab1Prog.length; l++) {
                           def tabProgramInst = new TabulatorProgram()
                           tabProgramInst.program = ProgramDetail.findById(Integer.parseInt(tab1Prog[l]))
                           tabProgramInst.user = UserInstance
                           tabProgramInst.role = role
                           if (tabProgramInst.save(flush: true, failOnError: true)) {
                               String[] sem = tab1Sem[l].split(",")
                               for (int j = 0; j < sem.length; j++) {
                                   def tabSemesterInst = new TabulatorSemester()
                                   println("*******"+sem[j])
                                   tabSemesterInst.semesterId = Semester.get(sem[j])
                                   tabSemesterInst.tabulatorProgram = tabProgramInst
                                   if (tabSemesterInst.save(flush: true, failOnError: true))
                                       println("Saved")
                               }
                           }
                       }
                   } else if (checked[i] == '10') {
                       String[] tab2Prog
                       String[] tab2Sem
                       tab2Prog = (params.tab2Program).toString().split(" ")
                       tab2Sem = (params.tab2Semester).toString().split("/")
                       for (int k = 0; k < tab2Prog.length; k++) {
                           def tabProgramInst
                           tabProgramInst = new TabulatorProgram()
                           tabProgramInst.program = ProgramDetail.findById(Integer.parseInt(tab2Prog[k]))
                           tabProgramInst.user = UserInstance
                           tabProgramInst.role = role
                           tabProgramInst.save(flush: true)

                           String[] sem
                           sem = tab2Sem[k].split(",")
                           for (int j = 0; j < sem.length; j++) {
                               def tabSemesterInst
                               tabSemesterInst = new TabulatorSemester()
                               tabSemesterInst.semesterId = Semester.get(sem[j])
                               tabSemesterInst.tabulatorProgram = tabProgramInst
                               tabSemesterInst.save(flush: true)
                           }
                       }
                   }
               }
               resultMap.status=true
           }
           else{
               resultMap.status=false
           }
           resultMap.userInstance=UserInstance
       }
       return resultMap
   }
    def editUserDetails(params){
        def resultMap=[:]
        def currentUser = springSecurityService.getCurrentUser().getUsername()
        def studyCentreList = StudyCenter.list(sort: 'name')
        def boolean compare = false
        println("+++++++++++++++++++"+params.id)
        def userInstance = User.get(params.id)
        if (currentUser == userInstance.username) {
            compare = true
        }
        def userRoles = UserRole.findAllByUser(userInstance)*.role
        if (!userInstance) {
            resultMap.status=false
        }
        else{
            def roles = Role.getAll()
            def studyCentre = null
            if (userInstance.studyCentreId != 0) {
                studyCentre = StudyCenter.findById(userInstance.studyCentreId).id
            }
            def tab1ProgramList, tab2ProgramList, tab1OptionValue = [], tab1OptionText = []
            def tab1HProgList = '', tab2HProgList = '', tab1HSemList = '', tab2HSemList = '', tab2OptionValue = [], tab2OptionText = []
            if(params.id==9) {
                tab1ProgramList = TabulatorProgram.findAllByUserAndRole(userInstance, Role.findById(9))
                if (tab1ProgramList) {
                    for (int j = 0; j < tab1ProgramList.size(); j++) {
                        def semList = TabulatorSemester.findAllByTabulatorProgram(tab1ProgramList[j])
                        String semester = '',semValue=''
                        for (int i = 0; i < semList.size(); i++) {
                            if (i == 0) {
                                semester = semList[i].semesterId.semesterNo
                                semValue=semList[i].semesterId.id
                            } else {
                                semester = ","+semList[i].semesterId.semesterNo
                                semValue=","+semList[i].semesterId.id
                            }
                        }
                        tab1OptionValue << tab1ProgramList[j].program.id + "/" + semValue
                        tab1OptionText << tab1ProgramList[j].program.courseName + "(Semesters " + semester + ")"
                        if (j == 0) {
                            tab1HProgList = tab1ProgramList[j].program.id
                            tab1HSemList = semValue
                        } else {
                            tab1HProgList += " " + tab1ProgramList[j].program.id
                            tab1HSemList += "/" + semValue
                        }
                    }
                }
            }
            if(params.id==10) {
                tab2ProgramList = TabulatorProgram.findAllByUserAndRole(userInstance, Role.findById(10))

                if (tab2ProgramList) {
                    for (int j = 0; j < tab2ProgramList.size(); j++) {
                        def semList = TabulatorSemester.findAllByTabulatorProgram(tab2ProgramList[j])
                        String semester = '',semValue=''
                        for (int i = 0; i < semList.size(); i++) {
                            if (i == 0) {
                                semester = semList[i].semesterId.semesterNo
                                semValue=semList[i].semesterId.id
                            } else {
                                semester = ","+semList[i].semesterId.semesterNo
                                semValue=","+semList[i].semesterId.id
                            }
                        }
                        if (j == 0) {
                            tab2HProgList = tab2ProgramList[j].program.id
                            tab2HSemList = semValue
                        } else {
                            tab2HProgList += " " + tab2ProgramList[j].program.id
                            tab2HSemList += "/" + semValue
                        }
                        tab2OptionValue << tab2ProgramList[j].program.id + "/" + semValue
                        tab2OptionText << tab2ProgramList[j].program.courseName + "(Semesters " + semester + ")"
                    }
                }
            }
            resultMap.status=true
            resultMap.tab1HProgList=tab1HProgList
            resultMap.tab1HSemList=tab1HSemList
            resultMap.tab2HProgList=tab2HProgList
            resultMap.tab2HSemList=tab2HSemList
            resultMap.tab1OptionText=tab1OptionText
            resultMap.tab2OptionText=tab2OptionText
            resultMap.tab1OptionValue=tab1OptionValue
            resultMap.tab2OptionValue=tab2OptionValue
            resultMap.userInstance=userInstance
            resultMap.roles=roles
            resultMap.userRoles=userRoles
            resultMap.compare=compare
            resultMap.studyCentreList=studyCentreList
            resultMap.studyCentre=studyCentre
        }
        return resultMap
    }
}