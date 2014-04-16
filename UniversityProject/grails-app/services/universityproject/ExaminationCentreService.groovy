package universityproject

import examinationproject.City
import examinationproject.ExaminationCentre
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import grails.transaction.Transactional

@Transactional
class ExaminationCentreService {

    def serviceMethod() {

    }

    Boolean saveCentres(params) {
        Boolean examinationCentreInsSaved = false;
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
            ExaminationCentre examinationCentreIns = new ExaminationCentre()
            examinationCentreIns.city = City.findById(Integer.parseInt(params.city))
            examinationCentreIns.address = examinationCentreAddressList[i].toString()
            examinationCentreIns.capacity = Integer.parseInt(examinationCentreCapacityList[i])
            examinationCentreIns.contactNo = examinationCentreContactNoList[i]
            examinationCentreIns.inchargeName = examinationCentreInchargeList[i].toString()
            examinationCentreIns.name = examinationCentreNameList[i].toString()
            examinationCentreIns.centreCode = Integer.parseInt(examinationCentreCodeList[i])
            if (examinationCentreIns.save(flush: true, failOnError: true)) {
                examinationCentreInsSaved = true;
            }
        }
        return examinationCentreInsSaved;
    }


    def examVenueList(params) {

        if (params) {
            ExaminationCentre.findAllByCity(City.findById(params.city))

        }

    }

    Boolean updateExaminationCentre(params) {
        Boolean isSaved = false
        def examCentreIns = ExaminationCentre.get(params.id)
        examCentreIns.city = City.findById(Integer.parseInt(params.city))
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

        def examVenue = ProgramExamVenue.findAllByCourseDetail(ProgramDetail.findById(Long.parseLong(params.programList)))
        return examVenue.examCenter
    }

}
