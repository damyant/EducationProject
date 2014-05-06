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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (params.studentId) {
            studentRegistration = Student.findById(Long.parseLong(params.studentId))
            studentRegistration.studentName = params.studentName
            studentRegistration.gender = params.gender
            studentRegistration.category = params.category
            studentRegistration.mobileNo = Long.parseLong(params.mobileNo)
            studentRegistration.nationality = params.nationality
            studentRegistration.state = params.state
            studentRegistration.addressState = params.addressState
            studentRegistration.addressPinCode = params.addressPinCode
            studentRegistration.addressPO = params.addressPO
            studentRegistration.addressTown = params.addressTown
            studentRegistration.addressStudentName = params.addressStudentName
            studentRegistration.addressDistrict = params.addressDistrict
            if(params.idol=="idol")
             studentRegistration.challanNo = getChallanNumber()

        } else {
            studentRegistration = new Student(params)
            studentRegistration.registrationYear = Integer.parseInt(year)
            if (springSecurityService.isLoggedIn()) {
                studentRegistration.referenceNumber = 0
                studentRegistration.rollNo = getStudentRollNumber(params)
                studentRegistration.status = Status.findById(2)

            } else {
                studentRegistration.referenceNumber = Integer.parseInt(getStudentReferenceNumber())
                studentRegistration.status = Status.findById(1)
            }
        }
        studentRegistration.dob = df.parse(params.d_o_b)

        Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))

        studentRegistration.studyCentre = studyCentre
        Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programId))
        endYear = (Integer.parseInt(year) + programDetail[0].noOfAcademicYears).toString()
        programSession = (startYear + "-" + endYear)

        def session = ProgramSession.count()
        def programSessionIns
        if (session > 0) {
            if (programDetail[0].programSession.id) {
                programSessionIns = ProgramSession.findById(programDetail[0].programSession.id)
            } else {
                programSessionIns = new ProgramSession(sessionOfProgram: programSession, programDetail: programDetail).save(flush: true, failOnError: true)
            }
        } else {
            programSessionIns = new ProgramSession(sessionOfProgram: programSession, programDetail: programDetail).save(flush: true, failOnError: true)
            println("Session new" + programSessionIns.sessionOfProgram)
        }



        studentRegistration.programSession = programSessionIns
        studentRegistration.programDetail = programDetail
        if(params.idol=="idol")
            studentRegistration.challanNo = getChallanNumber()
        Set<ExaminationVenue> examinationCentreList = ExaminationVenue.findAllById(Integer.parseInt(params.examinationCentre))
        studentRegistration.examinationCentre = examinationCentreList
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
            def feeDetails = new FeeDetails()

            feeDetails.bankId= Bank.findById(Integer.parseInt(params.bankName))
            feeDetails.branchId = Branch.findById(Integer.parseInt(params.branchName))
            feeDetails.paymentModeId = PaymentMode.findById(Integer.parseInt(params.paymentMode))
            feeDetails.paymentReferenceNumber = Integer.parseInt(params.feeReferenceNumber)
            feeDetails.feeTypeId = FeeType.findById(1)
            feeDetails.studentId= studentRegistration
            feeDetails.paymentDate = df.parse(params.paymentDate)
            feeDetails.save(flush: true,failOnError: true)
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
            println(courseCodeStr)
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

            println("List Size"+studentByYearAndCourse)
                if (studentByYearAndCourse.size()>0) {
                    if (studentByYearAndCourse.get(0).rollNo) {

                        if (rollNumber == null) {
                            rollTemp = studentByYearAndCourse.get(0).rollNo.substring(4, 8)
                            rollTemp1 = Integer.parseInt(rollTemp) + 1
                            rollNumber = courseCodeStr + yearCode + rollTemp1.toString()
                        } else {
                            ++rollTemp1
                            rollNumber = courseCodeStr + yearCode + rollTemp1.toString()
                        }
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
            if(!Student.findByReferenceNumber(Integer.parseInt(new String(buf)))){
                return new String(buf);
            }else{
                getStudentReferenceNumber()
            }
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
        def programSession = ProgramSession.findById(1)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))

        for (int i = 0; i < 100; i++) {
            students = new Student()
            println("Seeded user" + i)
            students.studentName = "Student" + i
            students.gender = "Male"
            students.category = "GEN"
            students.referenceNumber = Integer.parseInt(getStudentReferenceNumber())
            students.mobileNo = Long.parseLong("9898787998")
            students.nationality = "Indian"
            students.state = "Assam"
            students.status = Status.findById(1)
            students.examinationCentre = examinationCentre
            students.studyCentre = studyCenters
            students.admitCardGenerated = false
            students.programDetail = programDetails
            students.programSession = programSession
            students.registrationYear = year
            students.save(flush: true)

        }
    }

    def getChallanNumber(){
        String challanNo = getStudentReferenceNumber()
        if(Student.count()>0){
            if(!Student.findByChallanNo(challanNo)){
               return challanNo
            }else{
               getChallanNumber()
            }
        }
    }

}

