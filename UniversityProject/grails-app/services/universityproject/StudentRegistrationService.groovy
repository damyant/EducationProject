package universityproject

import examinationproject.ExaminationCentre
import examinationproject.Student
import grails.transaction.Transactional
import java.text.DateFormat;
import java.text.SimpleDateFormat

@Transactional
class StudentRegistrationService {

   Boolean saveNewStudentRegistration(params, signature, photographe){
       Boolean studentRegistrationInsSaved = false;
   println("params in service "+ params)
      def studentRegistration = new Student()
       studentRegistration.name=params.name
       studentRegistration.program=params.program
       studentRegistration.category=params.category
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       studentRegistration.dob=df.parse(params.date_of_birth)
       studentRegistration.gender=params.gender
       studentRegistration.nationality=params.nationality
       studentRegistration.state=params.state
       studentRegistration.registrationNo1=Integer.parseInt(params.registrationNo1)
       studentRegistration.registrationNo2=Integer.parseInt(params.registrationNo2)
       studentRegistration.addressStudentName=params.studentName
       studentRegistration.addressVillage=params.town
       studentRegistration.addressPO=params.po
       studentRegistration.addressDistrict=params.district
       studentRegistration.addressState=params.addressState
       studentRegistration.addressPinCode=Integer.parseInt(params.pinCode)
//       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllByCentreCode(Integer.parseInt(params.preference))
//       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes
//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }
}
