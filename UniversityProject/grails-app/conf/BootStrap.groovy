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

        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'admin',
                email: 'admin@damyant.com',
                studyCentreId: 1,
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
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
        def studyC=User.findByUsername('studycentre') ?:new User(
                username:'studycentre',
                password:'admin',
                email:'idol@email.com',
                studyCentreId:73,
                enabled:true,
                accountExpired:false,
                accountLocked:false,
                passwordExpired:false,
        ).save()
        if (!studyC.authorities.contains(studyCentreRole)) {
            UserRole.create studyC, studyCentreRole
        }
        RollNoGenerationFixture gb = new RollNoGenerationFixture (
                startD:new Date(),
                endD:new  Date(),
        ).save()

    }
    def destroy = {
    }
}
