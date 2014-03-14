package universityproject

import examinationproject.City
import examinationproject.StudyCenter
import grails.transaction.Transactional

@Transactional
class StudyCenterInfoService {

    def serviceMethod() {

    }

    def saveInfo(params){
        def saveStatus=null
        if(params.studyCenterId){
            def studyCenter = StudyCenter.get(params.studyCenterId)
            studyCenter.name=params.name
            studyCenter.address=params.address
            studyCenter.centerCode=params.centerCode
            studyCenter.websiteUrl=params.websiteUrl
            studyCenter.city=City.findById(Integer.parseInt(params.city))
            studyCenter.nameOfCoordinator=params.nameOfCoordinator
            studyCenter.emailIdOfCoordinator=params.emailIdOfCoordinator
            studyCenter.phoneNoOfCoordinator=params.phoneNoOfCoordinator
            studyCenter.phoneNoOfHeadIns=params.phoneNoOfHeadIns
            studyCenter.nameOfHeadIns=params.nameOfHeadIns
            studyCenter.emailIdOfHeadIns=params.emailIdOfHeadIns
            if(studyCenter.save(flush: true,failOnError: true)){
                saveStatus='updated'
            }
        }
        else{
            if(params){
                def studyCenterObj=new StudyCenter(params)
                if(studyCenterObj.save(flush: true,failOnError: true)){
                    saveStatus='created'
                }
            }
        }

    return saveStatus
    }

    def studyCenterList(params){
        if(params){
            StudyCenter.findAllByCity(City.findById(params.data))

        }


    }

    def studyCenterDetailInfo(params){
       // println(params.studyCenterId);
        if(params){
        def studyCenter = StudyCenter.get(params.studyCenterId)
//            println(studyCenter.name)
            return [studyCentreInstance:studyCenter]
        }
    }
}
