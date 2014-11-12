import com.university.Role
import com.university.User
import com.university.UserRole
import examinationproject.RollNoGenerationFixture

class BootStrap {

    def init = { servletContext ->

        def examinationCentreRole = Role.findByAuthority('ROLE_EXAMINATION_CENTRE') ?: new Role(authority: 'ROLE_EXAMINATION_CENTRE').save(failOnError: true)
        def courseRole = Role.findByAuthority('ROLE_COURSE') ?: new Role(authority: 'ROLE_COURSE').save(failOnError: true)
        def studyCentreRole = Role.findByAuthority('ROLE_STUDY_CENTRE')?:new Role(authority: 'ROLE_STUDY_CENTRE').save(failOnError: true)
        def admitCardRole = Role.findByAuthority('ROLE_ADMIT_CARD')?:new Role(authority: 'ROLE_ADMIT_CARD').save(failOnError: true)
        def generateRollNoRole = Role.findByAuthority('ROLE_GENERATE_ROLL_NO')?:new Role(authority: 'ROLE_GENERATE_ROLL_NO').save(failOnError: true)
        def idolUserRole = Role.findByAuthority('ROLE_IDOL_USER')?:new Role(authority: 'ROLE_IDOL_USER').save(failOnError: true)
        def adminRole =Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        def accountsRole =Role.findByAuthority('ROLE_ACCOUNT') ?: new Role(authority: 'ROLE_ACCOUNT').save(failOnError: true)
        def tabulatorRole1 =Role.findByAuthority('ROLE_TABULATOR1') ?: new Role(authority: 'ROLE_TABULATOR1').save(failOnError: true)
        def tabulatorRole2 =Role.findByAuthority('ROLE_TABULATOR2') ?: new Role(authority: 'ROLE_TABULATOR2').save(failOnError: true)
        def examAdmin =Role.findByAuthority('ROLE_EXAM_ADMIN') ?: new Role(authority: 'ROLE_EXAM_ADMIN').save(failOnError: true)
        def libraryRole =Role.findByAuthority('ROLE_LIBRARY') ?: new Role(authority: 'ROLE_LIBRARY').save(failOnError: true)


        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'admin',
                email: 'admin@damyant.com',
                studyCentreId: 1,
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }
        if (!adminUser.authorities.contains(examinationCentreRole)) {
            UserRole.create adminUser, examinationCentreRole
        }
        if (!adminUser.authorities.contains(courseRole)) {
            UserRole.create adminUser, courseRole
        }

        if (!adminUser.authorities.contains(admitCardRole)) {
            UserRole.create adminUser, admitCardRole
        }
        if (!adminUser.authorities.contains(generateRollNoRole)) {
            UserRole.create adminUser, generateRollNoRole
        }

        def iuser=User.findByUsername('idol') ?:new User(
                username:'idol',
                password:'admin',
                email:'idol@idolgu.com',
                studyCentreId:1,
                enabled:true,
                accountExpired:false,
                accountLocked:false,
                passwordExpired:false,
        ).save()
        if (!iuser.authorities.contains(idolUserRole)) {
            UserRole.create iuser, idolUserRole
        }
        def examAdminUser = User.findByUsername('examAdmin') ?: new User(
                username: 'examAdmin',
                password: 'admin',
                email: 'exam_admin@damyant.com',
                studyCentreId: 0,
                enabled: true).save(failOnError: true)

        if (!examAdminUser.authorities.contains(examAdmin)) {
            UserRole.create examAdminUser, examAdmin
        }
        def libraryUser = User.findByUsername('library') ?: new User(
                username: 'library',
                password: 'admin',
                email: 'exam_admin@damyant.com',
                studyCentreId: 0,
                enabled: true).save(failOnError: true)

        if (!libraryUser.authorities.contains(libraryRole)) {
            UserRole.create libraryUser, libraryRole
        }

//        def studyC=User.findByUsername('studycentre') ?:new User(
//                username:'studycentre',
//                password:'admin',
//                email:'idol@email.com',
//                studyCentreId:73,
//                enabled:true,
//                accountExpired:false,
//                accountLocked:false,
//                passwordExpired:false,
//        ).save()
//        if (!studyC.authorities.contains(studyCentreRole)) {
//            UserRole.create studyC, studyCentreRole
//        }

        RollNoGenerationFixture gb = new RollNoGenerationFixture (
                startD:new Date(),
                endD:new  Date(),
        ).save()

    }
    def destroy = {
    }
}
