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
       println("********"+params)
       Boolean studentRegistrationInsSaved = false;

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
       String year = sdf.format(Calendar.getInstance().getTime());
       def startYear= year
       def endYear
       def programSession
       def studentRegistration
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       if(params.studentId){
           studentRegistration=Student.findById(Long.parseLong(params.studentId))
           studentRegistration.studentName=params.studentName
           studentRegistration.gender=params.gender
           studentRegistration.category=params.category
           studentRegistration.mobileNo=Long.parseLong(params.mobileNo)
           studentRegistration.nationality=params.nationality
           studentRegistration.state=params.state
           studentRegistration.addressState=params.addressState
           studentRegistration.addressPinCode=params.addressPinCode
           studentRegistration.addressPO=params.addressPO
           studentRegistration.addressTown=params.addressTown
           studentRegistration.addressStudentName=params.addressStudentName
           studentRegistration.addressDistrict=params.addressDistrict
//           studentRegistration.status

       }
       else{
           studentRegistration = new Student(params)
           studentRegistration.registrationYear=Integer.parseInt(year)
           if(springSecurityService.isLoggedIn()){
               studentRegistration.referenceNumber=0
               studentRegistration.rollNo=getStudentRollNumber(params)
               studentRegistration.status= Status.findById(2)

           }else{
               studentRegistration.referenceNumber=Integer.parseInt(getStudentReferenceNumber())
               studentRegistration.status= Status.findById(1)
           }
       }
       studentRegistration.dob=df.parse(params.d_o_b)

       Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))

       studentRegistration.studyCentre=studyCentre
       Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programId))
       endYear = (Integer.parseInt(year)+programDetail[0].noOfAcademicYears).toString()
       programSession=(startYear+"-"+endYear)

       def session = ProgramSession.count()
       def programSessionIns
       if(session>0 ){
           if(programDetail[0].programSession.id){
               programSessionIns= ProgramSession.findById(programDetail[0].programSession.id)
           }else{
               programSessionIns=new ProgramSession(sessionOfProgram:  programSession,programDetail:programDetail).save(flush: true, failOnError: true)
           }
       }else{
           programSessionIns=new ProgramSession(sessionOfProgram:  programSession,programDetail:programDetail).save(flush: true, failOnError: true)
           println("Session new"+programSessionIns.sessionOfProgram)
       }



       studentRegistration.programSession = programSessionIns
       studentRegistration.programDetail=   programDetail

       Set<ExaminationCentre> examinationCentreList = ExaminationCentre.findAllById(Integer.parseInt(params.examinationCentre))
       studentRegistration.examinationCentre=examinationCentreList
       if(!params.appNo){
       studentRegistration.studentImage=photographe.bytes
       }
       else{
           studentRegistration.applicationNo=params.applicationNo
       }
       studentRegistration.semester=1
       studentRegistration.admitCardGenerated=false
        //RAJ CODE



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
         println("?????"+params)
            def status=false
            Set<ProgramDetail> course = ProgramDetail.findAllById(Long.parseLong(params.programId))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
            int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
            String courseCodeStr= course[0].courseCode.toString()
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2,4)
            int rollNo= 1001
            String rollTemp=null
         String rollTemp1
            String rollStr = Integer.toString(rollNo)
        println("<<<"+courseCodeStr)
         println("?????"+yearCode)
                if(courseCodeStr.length()>2){
                    println("lenth of course"+courseCodeStr.length())
                    courseCodeStr= courseCodeStr.substring(courseCodeStr.length()-2,courseCodeStr.length())
                    println(courseCodeStr)
                }
            String rollNumber = null;

//            def program = ProgramDetail.findById(courseId)
            def student=Student.count()

            if(student>0){
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
                    println("Hi generation the roll number"+(studentByYearAndCourse[0].rollNo==null))

                    if(!(studentByYearAndCourse[0].rollNo)){
                        if(rollTemp==null){
                        rollTemp = studentByYearAndCourse[0].rollNo.substring(4,8)
                        rollTemp1 = Integer.parseInt(rollTemp)+1
                        rollNumber = studentByYearAndCourse[0].rollNo.replace(rollTemp,rollTemp1)
                        }else{
                            ++rollTemp1
                            rollNumber = studentByYearAndCourse[0].rollNo.replace(rollTemp,rollTemp1)
                        }
                    }
                    else{
                        rollNumber= courseCodeStr+yearCode+rollStr
                    }
                }
                else{
                 rollNumber= courseCodeStr+yearCode+rollStr
                 }
                    stuObj.rollNo=rollNumber
                    stuObj.status=Status.findById(Long.parseLong("2"))
                    stuObj.save(failOnError: true)
              }
                return status=true
            }else{

                    if(studentByYearAndCourse){
                        if(studentByYearAndCourse[0].rollNo){
                            if(rollNumber==null){
                                rollTemp = studentByYearAndCourse[0].rollNo.substring(4,8)
                                rollTemp1 = Integer.parseInt(rollTemp)+1
                                rollNumber = studentByYearAndCourse[0].rollNo.replace(rollTemp,rollTemp1)
                          }
                          else{
                                ++rollTemp1
                                rollNumber = studentByYearAndCourse[0].rollNo.replace(rollTemp,rollTemp1)
                            }
                        }
                        else{
                            rollNumber= courseCodeStr+yearCode+rollStr
                        }
                    }
                    else{
                        rollNumber= courseCodeStr+yearCode+rollStr
                    }



                }
            }else{
                rollNumber= courseCodeStr+yearCode+rollStr
            }
         return rollNumber
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
