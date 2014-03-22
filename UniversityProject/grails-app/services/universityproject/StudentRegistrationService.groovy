package universityproject

import examinationproject.ExaminationCentre
import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.StudyCenter
import examinationproject.Student
import grails.transaction.Transactional
import java.text.DateFormat;
import java.text.SimpleDateFormat

@Transactional
class StudentRegistrationService {

   Boolean saveNewStudentRegistration(params, signature, photographe){
       Boolean studentRegistrationInsSaved = false;
   println("params in service "+ params)
      def studentRegistration = new Student(params)
//       studentRegistration.name=params.name
//       studentRegistration.program=params.program
//       studentRegistration.category=params.category
//       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//       studentRegistration.dob=df.parse(params.dob)
//       studentRegistration.gender=params.gender
//       studentRegistration.nationality=params.nationality
//       studentRegistration.state=params.state
//       if(params.registrationNo1){
//       studentRegistration.registrationNo1=Integer.parseInt(params.registrationNo1)
//       }
//       if(params.registrationNo2){
//       studentRegistration.registrationNo2=Integer.parseInt(params.registrationNo2)
//       }
//       studentRegistration.addressStudentName=params.studentName
//       studentRegistration.addressVillage=params.town
//       studentRegistration.addressPO=params.po
//       studentRegistration.addressDistrict=params.district
//       studentRegistration.addressState=params.addressState
//       studentRegistration.addressPinCode=Integer.parseInt(params.pinCode)
       Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))
       studentRegistration.status= Status.findById(1)
       studentRegistration.studyCentre=studyCentre
       Set<ProgramDetail> programDetail = ProgramDetail.findAllByCourseCode(Integer.parseInt(params.programDetail))
       studentRegistration.program=programDetail
       studentRegistration.studentImage=photographe.bytes
//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }
}
