package universityproject

import com.university.Role
import com.university.User
import com.university.UserRole
import examinationproject.City
import examinationproject.District
import examinationproject.StudyCenter
import grails.transaction.Transactional

@Transactional
class StudyCenterInfoService {

    def serviceMethod() {

    }

    def saveInfo(params) {
        def saveStatus = null
        if (params.studyCenterId) {
            def studyCenter = StudyCenter.get(params.studyCenterId)
            studyCenter.name = params.name
            studyCenter.address = params.address
            studyCenter.centerCode = params.centerCode
            studyCenter.websiteUrl = params.websiteUrl
            studyCenter.city = City.findById(Integer.parseInt(params.city))
            studyCenter.nameOfCoordinator = params.nameOfCoordinator
            studyCenter.emailIdOfCoordinator = params.emailIdOfCoordinator
            studyCenter.phoneNoOfCoordinator = params.phoneNoOfCoordinator
            studyCenter.phoneNoOfHeadIns = params.phoneNoOfHeadIns
            studyCenter.nameOfHeadIns = params.nameOfHeadIns
            studyCenter.emailIdOfHeadIns = params.emailIdOfHeadIns
            studyCenter.asstEmail = params.asstEmail
            studyCenter.asstCoordinator = params.asstCoordinator
            studyCenter.asstMobile = params.asstMobile
            if (studyCenter.save(flush: true, failOnError: true)) {
                saveStatus = 'updated'
            }
        } else {
            if (params) {
                def studyCenterObj = new StudyCenter(params)
                if (studyCenterObj.save(flush: true, failOnError: true)) {
//                    def studyCentreUser = User.findByUsername(params.emailIdOfHeadIns) ?: new User(
//                            username: params.emailIdOfHeadIns,
//                            password: 'admin',
//                            email: params.emailIdOfHeadIns,
//                            studyCentreId: studyCenterObj.id,
//                            enabled: true).save(failOnError: true)
//
//                    def studyCentreRole = Role.findByAuthority('ROLE_STUDY_CENTRE')
//                    if (!studyCentreUser.authorities.contains(studyCentreRole)) {
//                        UserRole.create studyCentreUser, studyCentreRole
//                    }
                    saveStatus = 'created'
                }
            }
        }

        return saveStatus
    }

    def studyCenterList(params) {
        if (params) {
            StudyCenter.findAllByCity(City.findById(params.data))

        }
    }
    def districtStudyCenterList(params) {
        def studyCenterList=[]
        if (params) {
           def cityList=City.findAllByDistrict(District.findById(params.data))
            cityList.each{
                    studyCenterList << StudyCenter.findAllByCity(it)
            }          
        }
        return  studyCenterList
    }

    def studyCenterForECList(params) {
        if (params) {
            StudyCenter.findAllByCityAndIsExamVenue(City.findById(params.data), 1)

        }


    }

    def studyCenterDetailInfo(params) {
        // println(params.studyCenterId);
        if (params) {
            def studyCenter = StudyCenter.get(params.studyCenterId)
//            println(studyCenter.name)
//            return [studyCentreInstance:studyCenter]
        }
    }
}
