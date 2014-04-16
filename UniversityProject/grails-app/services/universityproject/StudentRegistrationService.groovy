package universityproject

import examinationproject.ExaminationCentre
import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.ProgramSession
import examinationproject.StudyCenter
import examinationproject.Student
import grails.transaction.Transactional

import java.security.SecureRandom
import java.text.DateFormat;
import java.text.SimpleDateFormat

@Transactional
class StudentRegistrationService {

    def springSecurityService

   Student saveNewStudentRegistration(params, signature, photographe){
       Boolean studentRegistrationInsSaved = false;

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
       String year = sdf.format(Calendar.getInstance().getTime());
       def startYear= year
       def endYear
       def programSession
       def studentRegistration = new Student(params)
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       studentRegistration.dob=df.parse(params.d_o_b)


       Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))
       studentRegistration.status= Status.findById(1)
       studentRegistration.studyCentre=studyCentre
       Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programId))
       endYear = (Integer.parseInt(year)+programDetail[0].noOfAcademicYears).toString()
       programSession=(startYear+"-"+endYear)

       def programSessionIns = ProgramSession.findBySessionOfProgram(programSession) ?: new ProgramSession(sessionOfProgram:  programSession,programDetail:programDetail).save(flush: true, failOnError: true)

       studentRegistration.programSession = programSessionIns
       studentRegistration.programDetail=programDetail

       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllById(Integer.parseInt(params.examiNationCentre))
       studentRegistration.examinationCentre=examinationCentreList
       studentRegistration.studentImage=photographe.bytes
       studentRegistration.semester=1

        //RAJ CODE
       studentRegistration.registrationYear=Integer.parseInt(year)
       if(springSecurityService.isLoggedIn()){
       studentRegistration.referenceNumber=0
       studentRegistration.rollNo=(Integer)getStudentRollNumber(params)
       studentRegistration.status= Status.findById(2)
       }else{
           studentRegistration.referenceNumber=Integer.parseInt(getStudentReferenceNumber())
           studentRegistration.status= Status.findById(1)
       }


      //END RAJ CODE

      // studentRegistration.studentSignature=signature.bytes
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

                if(params.studentList){
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
            }else{

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

                    return rollNumber

                }
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
            buf = new char[8];
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
