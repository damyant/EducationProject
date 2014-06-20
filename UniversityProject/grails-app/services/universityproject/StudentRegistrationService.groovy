package universityproject

import examinationproject.Bank
import examinationproject.Branch
import examinationproject.City
import examinationproject.CustomChallan
import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.MiscellaneousFeeChallan
import examinationproject.PaymentMode
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

    Student saveNewStudentRegistration(params, signature, photographe) {
//    println(params)
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
            studentRegistration.isAppliedFor = params.isAppliedFor
            studentRegistration.mobileNo = Long.parseLong(params.mobileNo)
            studentRegistration.nationality = params.nationality
            studentRegistration.state = params.state
            studentRegistration.addressState = params.addressState
            studentRegistration.addressPinCode = params.addressPinCode
            studentRegistration.addressPO = params.addressPO
            if (params.registrationNo1)
                studentRegistration.registrationNo1 = params.registrationNo1
            if (params.registrationNo2)
                studentRegistration.registrationNo2 = params.registrationNo2
            studentRegistration.addressTown = params.addressTown
            studentRegistration.studentAddress = params.studentAddress
            studentRegistration.addressDistrict = params.addressDistrict
//            println("roll number is "+params.rollNo)
            if (params.rollNo) {
//                println("roll number is "+params.rollNo)
                def studentToBeUpdate
                try {
                    studentToBeUpdate = Student.findByRollNo(params.rollNo)
                } catch (Exception e) {
                    println("hello" + e.printStackTrace())
                }

                if (!(studentToBeUpdate.programDetail[0].id == (Integer.parseInt(params.programId)))) {
                    studentRegistration.rollNo = getStudentRollNumber(params)
                }
            }
//            studentRegistration.challanNo = getChallanNumber()

        } else {
            studentRegistration = new Student(params)
            bindData(studentRegistration, params)
            studentRegistration.registrationYear = Integer.parseInt(year)
            if (springSecurityService.isLoggedIn()) {
                studentRegistration.referenceNumber = 0
                studentRegistration.rollNo = getStudentRollNumber(params)
                studentRegistration.status = Status.findById(2)

            } else {
                studentRegistration.referenceNumber = getStudentReferenceNumber()
                studentRegistration.status = Status.findById(1)
                studentRegistration.challanNo = getChallanNumber()
            }
        }
        studentRegistration.dob = df.parse(params.d_o_b)
        Set<StudyCenter> studyCentre = StudyCenter.findAllByCenterCode((params.studyCentreCode))
        studentRegistration.studyCentre = studyCentre
        Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.programId))
        endYear = Integer.parseInt(year) + 1
        programSession = (startYear + "-" + endYear)
        def session = ProgramSession.count()
        def programSessionIns
        if (session > 0) {
            if (programDetail[0].id) {
                programSessionIns = ProgramSession.findByProgramDetailIdAndSessionOfProgram(programDetail[0], programSession)
                if (!programSessionIns) {
                    def programIns = ProgramSession.executeQuery("from ProgramSession where programDetailId=:programDetailId order by sessionOfProgram desc ", [programDetailId: programDetail[0], max: 1])
                    programSessionIns = programIns[0]
                }
            }
        } else {
            programSessionIns = new ProgramSession(sessionOfProgram: programSession).save(flush: true, failOnError: true)
        }
        studentRegistration.programSession = programSessionIns
        studentRegistration.programDetail = programDetail
        if (params.idol == "idol")
            studentRegistration.challanNo = getChallanNumber()
        Set<City> examinationCentreList = City.findAllById(Integer.parseInt(params.examinationCentre))
        studentRegistration.city = examinationCentreList
        if (!params.appNo) {
            if (photographe.bytes)
                studentRegistration.studentImage = photographe.bytes
        } else {
//            println("in else true" + params.appNo)
            studentRegistration.applicationNo = params.applicationNo
        }
        studentRegistration.semester = 1
        studentRegistration.admitCardGenerated = false
        def obj1 = Student.createCriteria()

        def studentExistRollNo = obj1.list {

            maxResults(1)
            order("id", "desc")
        }
        def maxId = studentExistRollNo.get(0).id
        println("maxId >>> " + maxId)
        if (studentRegistration.save(flush: true, failOnError: true)) {
            println("new Id   >>>>>> " + studentRegistration.id)
            if (!springSecurityService.isLoggedIn()) {
                def feeDetails = new FeeDetails()
                feeDetails.bankId = Bank.findById(Integer.parseInt(params.bankName))
                feeDetails.branchId = Branch.findById(Integer.parseInt(params.branchName))
                feeDetails.paymentModeId = PaymentMode.findById(Integer.parseInt(params.paymentMode))
                feeDetails.isAdmission = 0
                feeDetails.paymentReferenceNumber = Integer.parseInt(params.feeReferenceNumber)
                feeDetails.challanDate = new Date()
                feeDetails.challanNo = studentRegistration.challanNo
                feeDetails.paymentDate = df.parse(params.paymentDate)
                feeDetails.save(flush: true, failOnError: true)
                params.fee = params.admissionFeeAmount
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
        try {
            //  println("programId is "+Long.parseLong(params.programId))
            Set<ProgramDetail> course = ProgramDetail.findAllById(Long.parseLong(params.programId))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
            int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
            String courseCodeStr = course[0].courseCode.toString()
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2, 4)
            int rollNo = 1
            String rollTemp = null
            int rollTemp1 = 0
            String rollStr = Integer.toString(rollNo)

            if (courseCodeStr.length() > 2) {
                courseCodeStr = courseCodeStr.substring(courseCodeStr.length() - 2, courseCodeStr.length())
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

                if (studentByYearAndCourse.size() > 0) {
                    if (studentByYearAndCourse.get(0).rollNo) {
                        rollTemp = studentByYearAndCourse.get(0).rollNo.substring(4, 8)
                        rollTemp1 = Integer.parseInt(rollTemp) + 1
                        rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollTemp1.toString())
                    } else {
                        rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollStr)
                    }
                } else {
                    rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollStr)
                }
            } else {
                rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollStr)
            }
            return rollNumber
        } catch (Exception e) {
            println("Problem in roll number generation" + e.printStackTrace())
        }
    }

    private String prepareSequenceForRollNo(String serial) {
        int length
        String rollNoSr
        length = serial.toString().length()
        switch (length) {
            case 1:
                rollNoSr = "000" + serial.toString()
                break;
            case 2:
                rollNoSr = "00" + serial.toString()
                break;
            case 3:
                rollNoSr = "0" + serial.toString()
                break;
            default:
                rollNoSr = serial.toString()
        }
        return rollNoSr

    }

    def getUpdatedStudentRollNumber(params) {

        def status = false
        try {
            Set<ProgramDetail> course = ProgramDetail.findAllById(Long.parseLong(params.programId))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
            int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
            String courseCodeStr = course[0].courseCode.toString()
            String yearCode = sdf.format(Calendar.getInstance().getTime()).substring(2, 4)
            int rollNo = 1
            String rollTemp = null
            int rollTemp1 = 0
            String rollStr = Integer.toString(rollNo)

            if (courseCodeStr.length() > 2) {
                courseCodeStr = courseCodeStr.substring(courseCodeStr.length() - 2, courseCodeStr.length())
            }
            String rollNumber = null;
            Map<String> rollNoList = [:]
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
            def studentIdList = params.studentList.split(",")
            if (studentByYearAndCourse.get(0).rollNo) {
                println("In if")
                rollTemp = studentByYearAndCourse.get(0).rollNo.substring(4, 8)
                for (int i = 1; i <= studentIdList.size(); i++) {
                    rollTemp1 = Integer.parseInt(rollTemp) + i
                    rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollTemp1.toString())
                    rollNoList.put(i.toString(), rollNumber)
                }
            } else {
//                    println("In Else")
                int j = 1
                rollNoList.put(j.toString(), (courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollNo.toString())))
                for (int i = 1; i <= studentIdList.size(); i++) {
                    rollTemp1 = rollNo + i
                    rollNumber = courseCodeStr + yearCode + this.prepareSequenceForRollNo(rollTemp1.toString())
                    rollNoList.put((i + 1).toString(), rollNumber)
                }
            }
            Student stuObj;
            int j = 0
            ArrayList<String> rollNoKeyList = new ArrayList<String>(rollNoList.keySet());
//            println("keys"+rollNoKeyList)
            studentIdList.each { i ->
                rollNumber = rollNoList.get(rollNoKeyList.get(j))
                stuObj = Student.findById(i)
                if (!stuObj.rollNo)
                    stuObj.rollNo = rollNumber
                stuObj.status = Status.findById(Long.parseLong("3"))
                stuObj.save(flush: true, failOnError: true)
                j++
            }




            return rollNumber
        } catch (Exception e) {
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
        if (Student.count() > 0) {
            if (!Student.findByReferenceNumber(new String(buf))) {
                return new String(buf);
            } else {
                getStudentReferenceNumber()
            }
        } else {
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

    def seedStudent(params) {
        println("Start Time" + new Date())
        def students
        Set<ProgramDetail> programDetails = ProgramDetail.findAllById(23)

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
//////       At Study Center
//
//        for (int i = 1; i <= 20000; i++) {
//            println("Student Number is "+i)
//            students = new Student()
//            students.firstName = "StudentAtStudy"+i
//            students.lastName="Test"
//            students.gender = "Male"
//            students.category = "GEN"
//            students.programSession = ProgramSession.get(21)
////            students.referenceNumber = getStudentReferenceNumber()
////            students.challanNo = getChallanNumber()
//            params.programId="21"
//            students.rollNo = getStudentRollNumber(params)
//            students.dob = new Date()
//            students.admissionDate = new Date()
//            students.programDetail = programDetails
//            students.status = Status.findById(2)
//            students.studyCentre = StudyCenter.findAllById(73)
//            students.admitCardGenerated = false
//            students.semester=1
//            students.city=City.findAllById(8)
//            students.programDetail = programDetails
//            students.registrationYear = year
//            students.city= City.findAllById(1)
//            try{
//            students.save(flush: true,failOnError: true)
//            }catch (Exception e){
//                println("????????"+e.printStackTrace())
//            }
//        }

        //At IDOL
        for (int i = 250; i < 750; i++) {
            println("Student Number is " + i)
            students = new Student()
            students.firstName = "StudentAtIDOL" + i
            students.lastName = "Test"
            students.gender = "Male"
            students.category = "GEN"
            students.programSession = ProgramSession.get(23)
            students.referenceNumber = getStudentReferenceNumber()
            //students.challanNo = getChallanNumber()
            students.dob = new Date()
            students.admissionDate = new Date()
            students.programDetail = programDetails
            students.status = Status.findById(1)
            students.studyCentre = StudyCenter.findAllById(1)
            students.admitCardGenerated = false
            students.semester = 1
            students.city = City.findAllById(8)
            students.programDetail = programDetails
            students.registrationYear = year
            try {
                students.save(flush: true, failOnError: true)
            } catch (Exception e) {
                println("????????" + e.printStackTrace())
            }
        }
        println("End Time" + new Date())
    }

    def getChallanNumber() {
        int serialNo = 1
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd"); // Just the year
        def date = (sdf.format(Calendar.getInstance().getTime()))
        def challan = date.replaceAll("/", "")
        def challanSr
        def length
        def challanNo
//        println("Student.count" + Student.count)
//        println(Student.count)
        def studentByChallanNo
        if (Student.count() > 0) {
            def stdObj = Student.createCriteria()
            def studentTableByChallanNo = stdObj.list {
                isNotNull("challanNo")
                maxResults(1)
                order("id", "desc")
            }
            def mscObj = MiscellaneousFeeChallan.createCriteria()
            def MiscByChallanNo = mscObj.list {
                maxResults(1)
                order("id", "desc")
            }
            def custObj = CustomChallan.createCriteria()
            def custByChallanNo = custObj.list {
                maxResults(1)
                order("id", "desc")
            }
            def customChallan = 0
            println("In Here>>>" + custByChallanNo[0].challanNo)
            if (custByChallanNo) {
                customChallan = Integer.parseInt(custByChallanNo[0].challanNo)
            }

            if (studentTableByChallanNo) {
                if (MiscByChallanNo) {
                    if (custByChallanNo) {
                        if (Integer.parseInt(MiscByChallanNo[0].challanNo) > Integer.parseInt(studentTableByChallanNo[0].challanNo) && Integer.parseInt(studentTableByChallanNo[0].challanNo) > Integer.parseInt(custByChallanNo[0].challanNo)) {
                            studentByChallanNo = MiscByChallanNo
                            println('11')
                        } else if (Integer.parseInt(MiscByChallanNo[0].challanNo) < Integer.parseInt(studentTableByChallanNo[0].challanNo) && Integer.parseInt(studentTableByChallanNo[0].challanNo) < Integer.parseInt(custByChallanNo[0].challanNo)) {
                            studentByChallanNo = custByChallanNo
                            println('22')
                        } else {
                            studentByChallanNo = studentTableByChallanNo
                            println('33')
                        }
                    } else {
                        if (Integer.parseInt(MiscByChallanNo[0].challanNo) > Integer.parseInt(studentTableByChallanNo[0].challanNo)) {
                            studentByChallanNo = MiscByChallanNo
                            println('111')
                        } else {
                            studentByChallanNo = studentTableByChallanNo
                            println('333')
                        }
                    }
                } else {
                    if (Integer.parseInt(studentTableByChallanNo[0].challanNo) < Integer.parseInt(custByChallanNo[0].challanNo)) {
                        studentByChallanNo = custByChallanNo
                        println('2222')
                    } else {
                        studentByChallanNo = studentTableByChallanNo
                        println('3333')
                    }
                }
                println(studentByChallanNo)
                def lastChallanDate
                if (studentByChallanNo[0].challanNo != null) {
                    lastChallanDate = studentByChallanNo[0].challanNo.substring(0, 6)

                    if (lastChallanDate.equalsIgnoreCase(challan)) {
                        serialNo = Integer.parseInt(studentByChallanNo[0].challanNo.substring(6, 10))
                        serialNo = serialNo + 1
                    } else {
                        serialNo = 1
                    }
                } else {
                    serialNo = 1
                }
            } else {
                serialNo = 1
            }

            length = serialNo.toString().length()
            switch (length) {
                case 1:
                    challanSr = "000" + serialNo.toString()
                    break;
                case 2:
                    challanSr = "00" + serialNo.toString()
                    break;
                case 3:
                    challanSr = "0" + serialNo.toString()
                    break;
                default:
                    challanSr = serialNo.toString()
            }
            challanNo = challan + challanSr

        } else {
            challanSr = "000" + serialNo.toString()
            challanNo = challan + challanSr

        }
        return challanNo
    }

    def saveCChallan(params) {
        def resultMap = [:]
        def status = false
        def challanInst = new CustomChallan(params)
        def challanNo = getChallanNumber()
        challanInst.challanNo = challanNo
        challanInst.challanDate = new Date()
        if (challanInst.save(flush: true, failOnError: true)) {
            status = true
        }
        resultMap.status = status
        resultMap.challanNo = challanNo
        return resultMap
    }
}



