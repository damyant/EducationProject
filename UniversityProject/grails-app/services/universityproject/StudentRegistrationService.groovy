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
       Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programDetail))
       studentRegistration.programDetail=programDetail
       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllById(Integer.parseInt(params.examiNationCentre))
       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes

        //RAJ CODE
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
       String year = sdf.format(Calendar.getInstance().getTime());

       studentRegistration.registrationYear=Integer.parseInt(year)
       studentRegistration.referenceNumber=getStudentReferenceNumber(Long.parseLong(params.programDetail))
      //END RAJ CODE

//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true

       }
       return studentRegistrationInsSaved

   }
    /**
     * Service to generate the roll no.
     * @param courseId
     * @return
     */
            def getStudentRollNumber(Long courseId){

            Set<ProgramDetail> course = ProgramDetail.findAllById(courseId)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
            int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
            String courseCodeStr= course[0].courseCode.toString()
            int rollNumber = 0;

            def program = ProgramDetail.findById(courseId)
            def student=Student.list()
            if(student){
                Map paginateParams=null
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
                if(studentByYearAndCourse){
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
            return rollNumber
    }

    /**
     * Service to generate the reference no.
     * @param courseId
     * @return
     */
    def getStudentReferenceNumber(Long courseId){

        Set<ProgramDetail> course = ProgramDetail.findAllById(courseId)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))

        String courseCodeStr= course[0].courseCode.toString()
        int referenceNumber = 0;


        def program = ProgramDetail.findById(courseId)
        def student=Student.list()
        if(student){
            Map paginateParams=null
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
            if(studentByYearAndCourse){
                referenceNumber = studentByYearAndCourse[0].rollNo+1
            }else{
                String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
                int reference= 1111
                String rollStr = Integer.toString(reference)
                referenceNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
            }
        }else{
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
            int reference= 1111
            String rollStr = Integer.toString(reference)
            referenceNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
        }
        return referenceNumber
    }


}
