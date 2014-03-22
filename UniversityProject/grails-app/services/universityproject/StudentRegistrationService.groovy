package universityproject

import examinationproject.CourseDetail
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
      def studentRegistration = new Student(params)
//       studentRegistration.name=params.name
//       studentRegistration.program=params.program
//       studentRegistration.category=params.category
//       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
////       studentRegistration.dob=params.date_of_birth
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
//       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllByCentreCode(Integer.parseInt(params.preference))
//       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes
//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }

    def genarateStudentRollNumber(courseId){

        def course = CourseDetail.findById(courseId)
        def courseCode =course.courseCode
        SimpleDateFormat sdf = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String year = sdf.format(Calendar.getInstance().getTime());
        //def rolNumber = Student.f

        String courseCodeStr= Integer.toString(courseCode);

        int rollno= 1001;
        String r = Integer.toString(rollno);

        String rollNumber = null;

        rollNumber= crsecode+yr+r;

        System.out.println(rollNumber);




    }


}
