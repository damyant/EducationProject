package universityproject

import examinationproject.ExaminationCentre
import grails.transaction.Transactional

@Transactional
class SaveExaminationCentreService {

    def serviceMethod() {

    }
   Boolean saveCentres(params){
       println("params in service "+ params)
       println("<><><><><><><><><><>1 "+params.examinationCentreName)
        Boolean examinationCentreInsSaved = false;
        def examinationCentreNameList =[]
        examinationCentreNameList.addAll(params?.examinationCentreName)
       println("<><><><><><><><><><> 2"+params.examinationCentreName)
        def examinationCentreAddressList =[]
        examinationCentreAddressList.addAll(params?.examinationCentreAddress)
       println("<><><><><><><><><><>3 "+params.examinationCentreAddress)
        def examinationCentreContactNoList =[]
        examinationCentreContactNoList.addAll(params?.examinationCentreContactNo)
       println("<><><><><><><><><><>4 "+params.examinationCentreContactNo)
        def examinationCentreInchargeList =[]
        examinationCentreInchargeList.addAll(params?.examinationCentreIncharge)
       println("<><><><><><><><><><>5 "+params.examinationCentreIncharge)
        def examinationCentreCapacityList =[]
        examinationCentreCapacityList.addAll(params?.examinationCentreCapacity)
       println("<><><><><><><><><><>6 "+params.examinationCentreCapacity)
       def examinationCentreCodeList =[]
       examinationCentreCodeList.addAll(params?.examinationCentreCode)
        for(int i=0;i<examinationCentreNameList.size();i++) {
            println("saving this centre "+i)
            ExaminationCentre examinationCentreIns = new ExaminationCentre()
            examinationCentreIns.location = params.location
            examinationCentreIns.address= examinationCentreAddressList[i].toString()
            examinationCentreIns.capacity=Integer.parseInt(examinationCentreCapacityList[i])
            examinationCentreIns.contactNo= examinationCentreContactNoList[i]
            examinationCentreIns.inchargeName= examinationCentreInchargeList[i].toString()
            examinationCentreIns.name= examinationCentreNameList[i].toString()
            examinationCentreIns.centreCode= Integer.parseInt(examinationCentreCodeList[i])
            if (examinationCentreIns.save(flush: true)) {
                println("centre saved successfully")
                examinationCentreInsSaved = true;
            }
        }
        return examinationCentreInsSaved;
        }

    }

