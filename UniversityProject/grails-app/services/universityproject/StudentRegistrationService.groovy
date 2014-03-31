package universityproject


import examinationproject.ExaminationCentre
import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.StudyCenter
import examinationproject.Student
import grails.transaction.Transactional

import java.security.SecureRandom
import java.text.DateFormat;
import java.text.SimpleDateFormat

@Transactional
class StudentRegistrationService {

   Student saveNewStudentRegistration(params, signature, photographe){
       Boolean studentRegistrationInsSaved = false;

      def studentRegistration = new Student(params)
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       studentRegistration.dob=df.parse(params.d_o_b)

       Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))
       studentRegistration.status= Status.findById(1)
       studentRegistration.studyCentre=studyCentre
       Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programDetail))
       studentRegistration.programDetail=programDetail
       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllById(Integer.parseInt(params.examiNationCentre))
       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes

        //RAJ CODE
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
       String year = sdf.format(Calendar.getInstance().getTime());

       studentRegistration.registrationYear=Integer.parseInt(year)
       studentRegistration.referenceNumber=Integer.parseInt(getStudentReferenceNumber())
      //END RAJ CODE

//       studentRegistration.studentSignature=signature.bytes
       if(studentRegistration.save(flush:true,failOnError: true)){
           println('new student registered successfully')
           studentRegistrationInsSaved= true
           return studentRegistration
        }else{
           return null
       }


   }
    /**
     * Service to generate the roll no.
     * @param courseId
     * @return
     */
     def getStudentRollNumber(params){
            def status=false
            Set<ProgramDetail> course = ProgramDetail.findAllById(Long.parseLong(params.programId))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
            int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
            String courseCodeStr= course[0].courseCode.toString()
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
            int rollNo= 1001
            String rollStr = Integer.toString(rollNo)

                if(courseCodeStr.length()>2){
                    courseCodeStr= courseCodeStr.substring(0,2)
                }
            int rollNumber = 0;

//            def program = ProgramDetail.findById(courseId)
            def student=Student.list()
            if(student){
                def obj=Student.createCriteria()
                def studentByYearAndCourse= obj.list{
                    programDetail{
                        eq('id', Long.parseLong(params.programId))
                    }
                    and{
                        eq('registrationYear',year)
                    }
                    maxResults(1)
                    order("rollNo", "desc")
                }

                def studentIdList=params.studentList.split(",")
                studentIdList.each{i ->
                 def stuObj=Student.findById(Long.parseLong(i.toString()))

                if(studentByYearAndCourse){
                    if(studentByYearAndCourse[0].rollNo>0){
                        if(rollNumber==0){
                        rollNumber = studentByYearAndCourse[0].rollNo+1
                        }
                        else{
                            rollNumber=++rollNumber
                        }
                    }
                    else{
                        rollNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
                    }
                }
                else{
                 rollNumber= Integer.parseInt(courseCodeStr+yearCode+rollStr)
                 }
                    stuObj.rollNo=rollNumber
                    stuObj.status=Status.findById(Long.parseLong("2"))
                    stuObj.save(failOnError: true)
                }
                return status=true
            }

     }


    /**
     * Service to generate the reference no.
     * @param courseId
     * @return
     */
      def getStudentReferenceNumber(){
            /* Assign a string that contains the set of characters you allow. */
            String symbols = "01234567899876543210";
            Random random = new SecureRandom();
            char[] buf;
            buf = new char[6];
            def bufLength = buf.length
            for (int idx = 0; idx < bufLength;idx++)
                buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
            return new String(buf);
    }

    def approvedStudents(params){
        def studentIdList=params.studentList.split(",")
        studentIdList.each{i ->
            def stuObj=Student.findById(Long.parseLong(i.toString()))
            stuObj.status=Status.findById(Long.parseLong("3"))
            stuObj.save(failOnError: true)
        }


    }
}
