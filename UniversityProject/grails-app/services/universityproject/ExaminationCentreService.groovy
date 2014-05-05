package universityproject

import examinationproject.City
import examinationproject.District
import examinationproject.ExaminationCentre
import examinationproject.ExaminationVenue
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class ExaminationCentreService {

    def serviceMethod() {

    }

    Boolean saveCentres(params) {

        Boolean examinationVenueInsSaved = false;
        def examinationCentreNameList = []
        examinationCentreNameList.addAll(params?.examinationCentreName)
        def examinationCentreAddressList = []
        examinationCentreAddressList.addAll(params?.examinationCentreAddress)
        def examinationCentreContactNoList = []
        examinationCentreContactNoList.addAll(params?.examinationCentreContactNo)
        def examinationCentreInchargeList = []
        examinationCentreInchargeList.addAll(params?.examinationCentreIncharge)
        def examinationCentreCapacityList = []
        examinationCentreCapacityList.addAll(params?.examinationCentreCapacity)
        def examinationCentreCodeList = []
        examinationCentreCodeList.addAll(params?.examinationCentreCode)
        for (int i = 0; i < examinationCentreNameList.size(); i++) {
            ExaminationVenue examinationVenueIns = new ExaminationVenue()
//            examinationVenueIns.city = City.findById(params.city)
            examinationVenueIns.address = examinationCentreAddressList[i].toString()
            examinationVenueIns.capacity = Integer.parseInt(examinationCentreCapacityList[i])
            examinationVenueIns.contactNo = examinationCentreContactNoList[i]
            examinationVenueIns.inchargeName = examinationCentreInchargeList[i].toString()
            examinationVenueIns.name = examinationCentreNameList[i].toString()
            examinationVenueIns.centreCode = Integer.parseInt(examinationCentreCodeList[i])
            def examIns = ExaminationCentre.findById(Integer.parseInt(params.examinationCentre))
            examIns.addToExamVenue(examinationVenueIns)

//            examinationVenueIns. = examinationCentreList
            if (examinationVenueIns.save(flush: true, failOnError: true)) {
                examinationVenueInsSaved = true;
            }
        }
        return examinationVenueInsSaved;
    }


    def examVenueList(params) {

        if (params) {
            def list=ExaminationCentre.findAllById(Integer.parseInt(params.examinationCentre))
//            println("<<<"+list.examVenue)
//            def obj=ExaminationVenue.createCriteria();
//            def examVenueList=obj.list{
//                ex
//            }
            return list.examVenue[0];

        }

    }

    Boolean updateExaminationCentre(params) {
        Boolean isSaved = false
        def examCentreIns = ExaminationVenue.get(params.id)
//        examCentreIns.city = City.findById(Integer.parseInt(params.city))
        examCentreIns.capacity = Integer.parseInt(params.capacity)
        examCentreIns.name = params.centreName
        examCentreIns.contactNo = params.contactNo
        examCentreIns.address = params.address
        if (examCentreIns.save(flush: true)) {
            isSaved = true
        }

        return isSaved
    }

    def associatedExamVenue(params) {

        def examinationCentre = ExaminationCentre.findById(Long.parseLong(params.examinationCentre))
        def programIns = ProgramDetail.findById(Long.parseLong(params.programList))
        def examVenue = ProgramExamVenue.findAllByCourseDetailAndExamCenter(programIns, examinationCentre)
         return examVenue.examVenue
    }

    Boolean saveExamCentres(params) {
        Boolean isSaved = false;
        ExaminationCentre examCentreIns = new ExaminationCentre()
        examCentreIns.examinationCentreName= params.examCentreName
        examCentreIns.district = District.findById(Integer.parseInt(params.district))
        if (examCentreIns.save(flush: true)) {
            isSaved = true
        }
    }

    def deletionExamVenue(params){
        def examVenueInstance = ExaminationVenue.get(params.id)
        Student.findAllByExaminationVenue(examVenueInstance).removeAll()
        def obj = ExaminationCentre.createCriteria()

        def examList = obj.list{
            examVenue {
                eq('id',Long.parseLong(params.id))
            }
        }
        examList.each{itr->
            itr.removeFromExamVenue(examVenueInstance)
            itr.save(flush:true)
        }
    }


}
