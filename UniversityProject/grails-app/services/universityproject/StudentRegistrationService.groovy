package universityproject

import examinationproject.Bank
import examinationproject.Branch
import examinationproject.ExaminationCentre
import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.ProgramSession
import examinationproject.StudyCenter
import examinationproject.Student
import grails.transaction.Transactional
import jxl.CellReferenceHelper

import java.security.SecureRandom
import java.text.DateFormat;
import java.text.SimpleDateFormat

@Transactional
class StudentRegistrationService {

    def springSecurityService

    Student saveNewStudentRegistration(params, signature, photographe) {
        Boolean studentRegistrationInsSaved = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        String year = sdf.format(Calendar.getInstance().getTime());
        def startYear = year
        def endYear
        def programSession
        def studentRegistration
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        if (params.studentId) {
            studentRegistration = Student.findById(Long.parseLong(params.studentId))
            studentRegistration.firstName = params.firstName
            studentRegistration.lastName = params.lastName
            studentRegistration.middleName = params.middleName
            studentRegistration.gender = params.gender
            studentRegistration.category = params.category
            studentRegistration.mobileNo = Long.parseLong(params.mobileNo)
            studentRegistration.nationality = params.nationality
            studentRegistration.state = params.state
            studentRegistration.addressState = params.addressState
            studentRegistration.addressPinCode = params.addressPinCode
            studentRegistration.addressPO = params.addressPO
            studentRegistration.addressTown = params.addressTown
            studentRegistration.studentAddress = params.studentAddress
            studentRegistration.addressDistrict = params.addressDistrict
            studentRegistration.challanNo = getChallanNumber()

        } else {
            studentRegistration = new Student(params)
            studentRegistration.registrationYear = Integer.parseInt(year)
            if (springSecurityService.isLoggedIn()) {
                studentRegistration.referenceNumber = 0
                studentRegistration.rollNo = getStudentRollNumber(params)
                studentRegistration.status = Status.findById(2)

            } else {
                studentRegistration.referenceNumber = getStudentReferenceNumber()
                studentRegistration.status = Status.findById(1)
            }
        }
        studentRegistration.dob = df.parse(params.d_o_b)
        studentRegistration.challanNo = getChallanNumber()
        Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))

        studentRegistration.studyCentre = studyCentre
        Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programId))
      //  endYear = (Integer.parseInt(year) + programDetail[0].noOfAcademicYears).toString()
        endYear = Integer.parseInt(year)+1
        programSession = (startYear + "-" + endYear)

        def session = ProgramSession.count()
        def programSessionIns
        println("++++++++++++++"+session)
        if (session > 0) {
            println("???????????"+programDetail[0].id)
            if (programDetail[0].id) {
                programSessionIns = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(programDetail[0].id))
            } else {
                programSessionIns = new ProgramSession(sessionOfProgram: programSession).save(flush: true, failOnError: true)
            }
        } else {
            programSessionIns = new ProgramSession(sessionOfProgram: programSession).save(flush: true, failOnError: true)

        }

        studentRegistration.programSession = programSessionIns
        studentRegistration.programDetail = programDetail
        if(params.idol=="idol")
            studentRegistration.challanNo = getChallanNumber()
        Set<ExaminationVenue> examinationCentreList = ExaminationVenue.findAllById(Integer.parseInt(params.examinationCentre))
        studentRegistration.city = examinationCentreList
        if (!params.appNo) {
            studentRegistration.studentImage = photographe.bytes
        } else {
            studentRegistration.applicationNo = params.applicationNo
        }
        studentRegistration.semester = 1
        studentRegistration.admitCardGenerated = false
        //RAJ CODE

        //END RAJ CODE
        // studentRegistration.studentSignature=signature.bytes
        if (studentRegistration.save(flush: true, failOnError: true)) {
            if (!springSecurityService.isLoggedIn()) {
            def feeDetails = new FeeDetails()

            feeDetails.bankId= Bank.findById(Integer.parseInt(params.bankName))
            feeDetails.branchId = Branch.findById(Integer.parseInt(params.branchName))
            feeDetails.paymentModeId = PaymentMode.findById(Integer.parseInt(params.paymentMode))
//            feeDetails.paymentReferenceNumber = Integer.parseInt(params.feeReferenceNumber)
            feeDetails.isAdmission = 0
            feeDetails.challanDate=new Date()
            feeDetails.challanNo= studentRegistration.challanNo
            feeDetails.paymentDate = df.parse(params.paymentDate)
            feeDetails.save(flush: true,failOnError: true)
            }
            return studentRegistration
        } else {
            return null
        }


    }

    /**
     * Service to generate the roll no.
     * @param params
     * @return
     */
    def getStudentRollNumber(params) {
        def status = false
        try{
        Set<ProgramDetail> course = ProgramDetail.findAllById(Long.parseLong(params.programId))
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
        String courseCodeStr = course[0].courseCode.toString()
        String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2, 4)
        int rollNo = 1001
        String rollTemp = null
        int rollTemp1 = 0
        String rollStr = Integer.toString(rollNo)

        if (courseCodeStr.length() > 2) {
            courseCodeStr = courseCodeStr.substring(courseCodeStr.length() - 2, courseCodeStr.length())
//            println(courseCodeStr)
        }
        String rollNumber = null;
        def student = Student.count()
        if (student > 0) {

            def obj = Student.createCriteria()
            def studentByYearAndCourse = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and {
                    eq('registrationYear', year)
                }
                maxResults(1)
                order("rollNo", "desc")
            }

                if (studentByYearAndCourse.size()>0) {
                    if (studentByYearAndCourse.get(0).rollNo) {

//                        if (rollNumber == null) {
                            rollTemp = studentByYearAndCourse.get(0).rollNo.substring(4, 8)
                            rollTemp1 = Integer.parseInt(rollTemp) + 1
                            rollNumber = courseCodeStr + yearCode + rollTemp1.toString()
                    } else {
                        rollNumber = courseCodeStr + yearCode + rollStr
                    }
                } else {
                    rollNumber = courseCodeStr + yearCode + rollStr
                }

        } else {
            rollNumber = courseCodeStr + yearCode + rollStr
        }
        return rollNumber
        }catch(Exception e){
           println("Problem in roll number generation")
        }
    }

    /**
     * Service to generate the reference no.
     * @param courseId
     * @return
     */
    def getStudentReferenceNumber() {
        /* Assign a string that contains the set of characters you allow. */
        String symbols = "123456789987654321";
        Random random = new SecureRandom();
        char[] buf;
        buf = new char[6];
        def bufLength = buf.length
        for (int idx = 0; idx < bufLength; idx++)
            buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
        if(Student.count()>0){
            if(!Student.findByReferenceNumber(new String(buf))){
                return new String(buf);

            }else{
                getStudentReferenceNumber()
            }
             }else{
            return new String(buf)
          }

    }

    def approvedStudents(params) {
        def studentIdList = params.studentList.split(",")
        studentIdList.each { i ->
            def stuObj = Student.findById(Long.parseLong(i.toString()))
            stuObj.status = Status.findById(Long.parseLong("3"))
            stuObj.save(failOnError: true)
        }
    }

    def seedStudent() {
        def students

        Set<ExaminationCentre> examinationCentre = ExaminationCentre.findAllById(1)
        Set<StudyCenter> studyCenters = StudyCenter.findAllById(8)
        Set<ProgramDetail> programDetails = ProgramDetail.findAllById(23)
//        def programSession = ProgramSession.findById(1)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))

        for (int i = 0; i < 100; i++) {
            students = new Student()
//            println("Seeded user" + i)
            //students.studentName = "Student" + i
            students.gender = "Male"
            students.category = "GEN"
            students.referenceNumber = getStudentReferenceNumber()
            students.mobileNo = Long.parseLong("9898787998")
            students.nationality = "Indian"
            students.state = "Assam"
            students.status = Status.findById(1)
            students.examinationCentre = examinationCentre
            students.studyCentre = studyCenters
            students.admitCardGenerated = false
            students.programDetail = programDetails
//            students.programSession = programSession
            students.registrationYear = year
            students.save(flush: true)

        }
    }

    def getChallanNumber(){

        int serialNo = 1
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd"); // Just the year
        def date = (sdf.format(Calendar.getInstance().getTime()))
        def challan = date.replaceAll("/","")
        def challanSr
        def length
        def challanNo
        if(Student.count()>0){
            def obj = Student.createCriteria()
            def studentByChallanNo = obj.list {
                maxResults(1)
                order("challanNo", "desc")
            }

            def lastChallanDate = studentByChallanNo[0].challanNo.substring(0,6)
            if(lastChallanDate.equalsIgnoreCase(challan)){
                serialNo = Integer.parseInt(studentByChallanNo[0].challanNo.substring(6,10))
                serialNo =serialNo+1
            }else{
                serialNo =1
            }

            length = serialNo.toString().length()
            switch(length){
                case 1:
                    challanSr = "000"+serialNo.toString()
                    break;
                case 2:
                    challanSr = "00"+serialNo.toString()
                    break;
                case 3:
                    challanSr = "0"+serialNo.toString()
                    break;
                default:
                    challanSr = serialNo.toString()

            }

            println("Incremented serial"+challanSr)
            challanNo = challan+challanSr

        }else{
             challanSr = "000"+serialNo.toString()
             challanNo = challan+challanSr

            }
            println("challan number is"+challanNo)
            return challanNo
        }


    }



