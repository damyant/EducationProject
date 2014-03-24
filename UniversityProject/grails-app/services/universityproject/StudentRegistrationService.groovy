package universityproject

import examinationproject.CourseDetail
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
       studentRegistration.programDetail=programDetail
       studentRegistration.studentImage=photographe.bytes

        //RAJ CODE
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
       String year = sdf.format(Calendar.getInstance().getTime());

       studentRegistration.registrationYear=Integer.parseInt(year)


       studentRegistration.rollNo=getStudentRollNumber(params.programDetail)

      //END RAJ CODE

//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }

    def getStudentRollNumber(courseId){

        Set<ProgramDetail> course = ProgramDetail.findAllById(courseId)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))

        String courseCodeStr= course.courseCode.toString()
        int rollNumber = 0;


        def student =Student.findByRegistrationYear(year)
        def studentCourse = student.programDetail

        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+student)
        println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+student.programDetail)
        if(student && studentCourse){
        def lastStudent = Student.findByProgramDetailAndRegistrationYear(course,year, [max:1, sort:"rollNo", order:"desc"])
        rollNumber = lastStudent.rollNo+1
         }else{
                       String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
                       int rollno= 1001
            String rollStr = Integer.toString(rollno)
            rollNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
        }

        println(rollNumber);

        return rollNumber




    }


}
