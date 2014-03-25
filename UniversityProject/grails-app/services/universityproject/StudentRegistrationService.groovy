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
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       studentRegistration.dob=df.parse(params.d_o_b)
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
       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllById(Integer.parseInt(params.examiNationCentre))
       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes

        //RAJ CODE
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
       String year = sdf.format(Calendar.getInstance().getTime());

       studentRegistration.registrationYear=Integer.parseInt(year)


       studentRegistration.rollNo=getStudentRollNumber(Long.parseLong(params.programDetail))

      //END RAJ CODE

//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }

    def getStudentRollNumber(Long courseId){

      Set<ProgramDetail> course = ProgramDetail.findAllById(courseId)
      //  def course = ProgramDetail.findAllById(courseId)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))

        String courseCodeStr= course[0].courseCode.toString()
        int rollNumber = 0;

        def program = ProgramDetail.findById(courseId)
        println("NNNNNNNNNNNNNNNNNNNNNN"+program.student)


        def student=Student.list()

       // def student =Student.findByProgramDetailAndRegistrationYear(course[0],year)

        if(student){
            //def studentByYearAndCourse=Student.findByRegistrationYearAndProgramDetailInList(year,course)
            Map paginateParams=null
            println("Check for Student++++++++++++++++++++++++")
//            def studentByYearAndCourse = Student.findByRegistrationYearAndProgramDetail(year, course, paginateParams)
            def obj=Student .createCriteria()
           def studentByYearAndCourse= obj.list{
                programDetail{
                    eq('id', courseId)
                }
               and{
                       eq('registrationYear',year)
               }
               maxResults(1)
               order("rollNo", "desc")
            }
            println("Check for Student++++++++++++++++++++++++@@@@@@@@@@@@@@@@"+studentByYearAndCourse)
            if(studentByYearAndCourse){
//           def lastStudent = Student.findByRegistrationYearAndProgramDetailInList(year,course,[max:1, sort:"rollNo", order:"desc"])
              rollNumber = studentByYearAndCourse[0].rollNo+1
            }else{
                String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
                int rollNo= 1001
                String rollStr = Integer.toString(rollNo)
                rollNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
            }
         }else{
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
            int rollNo= 1001
            String rollStr = Integer.toString(rollNo)
            rollNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
        }

        println(rollNumber);

        return rollNumber




    }


}
