package universityproject

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.FeeDetails
import examinationproject.FeeSession
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.MiscellaneousFeeChallan
import examinationproject.PaymentMode
import examinationproject.ProgramDetail

import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyCenter
import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class FeeDetailService {
    def springSecurityService

    def serviceMethod() {

    }

    /**
     * Service to save the fee details and approve student
     * @param params
     * @return
     */
    def saveFeeDetails(params) {

        def feeDetailsInstance = new FeeDetails(params)
        def studentIns = Student.findById(Long.parseLong(params.studentId))
        if (feeDetailsInstance.save(flush: true, failOnError: true)) {
            studentIns.status = Status.findById(4)
            studentIns.save(flush: true, failOnError: true)
        }

        return feeDetailsInstance
    }

    /**
     * Service to get provisional students
     * @param params
     * @return
     */
    def provisionalStudentList(params) {

//        println(" this is the id of studyCentre " + params)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        def studyCenterId = 0
        def statusObj
        Date date = null
        if (params.admissionDate) {
//            println("now searching students for date")
            date = df.parse(params.admissionDate)
        }
        if (params.studyCenterId != 'null') {
//            println("assign Study Centre1")
            studyCenterId = params.studyCenterId
        } else {
            def currentUser = springSecurityService.getCurrentUser()
//            println("assign Study Centre2")
            studyCenterId = currentUser.studyCentreId
        }
        statusObj = Status.findById(2)


        def obj = Student.createCriteria()
        def stuList
        if (params.programId != 'null') {
//            println("getting students of program id" + params.programId)
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and {
                    eq('status', statusObj)
                }
            }
        } else if (params.studyCenterId != 'null') {
//            println("finding student of this studyCentre " + studyCenterId)
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(studyCenterId))
                }
                and {
                    eq('status', statusObj)
                }
            }
        } else if (params.admissionDate != 'null') {
//            println("searching by date " + date)
            stuList = Student.findAllByAdmissionDateAndStatus(date, statusObj)
        }

//        println("this is the final list of students " + stuList)
        return stuList
    }

    def StudentList(params) {
        def resultMap = [:]
        def obj = Student.createCriteria()
        def currentUser = springSecurityService.getCurrentUser()
        def stuList = []

        if (params.program != 'All' && params.semester != 'All') {
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.program))
                }
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                    isNull('challanNo')
                    eq('semester', Integer.parseInt(params.semester))
                }
            }
        } else if (params.program == 'All' && params.semester != 'All') {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                    isNull('challanNo')
                    eq('semester', params.semester)
                }
            }

        } else if (params.program != 'All' && params.semester == 'All') {
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.program))
                }
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                }
            }
        } else {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                }
            }
        }

//        println("*******************>> "+stuList)
        if (stuList.size() > 0) {
            def bankName = Bank.list(sort: 'bankName')
            def paymentMode = PaymentMode.list(sort: 'paymentModeName')
            def feeList = FeeType.list(sort: 'type')
            def feeAmountList = []
            def lateFee = 0
            def prevProgram = null
            def payableAmount = 0
            def today = new Date()
            println("this is the size " + stuList.size())
            if (params.program == 'All' && params.semester == 'All') {
                for (int i = 0; i < stuList.size(); i++) {
                    int year = stuList[i].registrationYear
                    def sessionVal = year + 1
                    sessionVal = year + '-' + sessionVal

                    def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[i].programDetail[0].id), sessionVal)

                    if (prevProgram != stuList[i].programDetail.courseName) {
                        def lateFeeDate = stuList[i].programDetail.lateFeeDate[0]
                        def amount = AdmissionFee.findByFeeSession(feeSessionObj)
                        prevProgram = stuList[i].programDetail.courseName
                        if (lateFeeDate != null) {
                            if (today.compareTo(lateFeeDate) > 0) {
                                lateFee = amount.lateFeeAmount
                            }
                        }
                        payableAmount = amount.feeAmountAtSC + lateFee
                    }
                    feeAmountList.add(payableAmount)
                }
            } else {
//            println("??????????"+stuList[0])
                def lateFeeDate = stuList[0].programDetail.lateFeeDate[0]
                int year = stuList[0].registrationYear
                def sessionVal = year + 1
                sessionVal = year + '-' + sessionVal

                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[0].programDetail[0].id), sessionVal)
                def amount = AdmissionFee.findByFeeSession(feeSessionObj)
//            println("this is the amount "+ amount)

                if (lateFeeDate != null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = amount.lateFeeAmount
                    }
                }
                payableAmount = amount.feeAmountAtSC + lateFee
                for (int i = 0; i < stuList.size(); i++) {
                    feeAmountList.add(payableAmount)
                }
            }
            resultMap.studentList = stuList
            resultMap.bankName = bankName
            resultMap.paymentMode = paymentMode
            resultMap.feeList = feeList
            resultMap.feeAmount = feeAmountList
        } else {
            resultMap.error = "NO STUDENT AVAILABLE"
        }
        return resultMap
    }

    def StudentListForAdmission(params) {
        def resultMap = [:]
        def obj = Student.createCriteria()
        def currentUser = springSecurityService.getCurrentUser()
        def stuList = []
        def studentsWhoFeePaidPrevious
        def feeObj = FeeDetails.createCriteria()
        def studentsWhoFeePaid = feeObj.list {
            eq('feeType', FeeType.findById(params.feeType))
            eq('semesterValue', Integer.parseInt(params.semester))
            eq('isApproved', 1)
        }.student.id.unique()
        def feePaidObj = FeeDetails.createCriteria()

        if (studentsWhoFeePaid.size() == 0) {
            studentsWhoFeePaidPrevious = feePaidObj.list {
                eq('feeType', FeeType.findById(params.feeType))
                eq('semesterValue', Integer.parseInt(params.semester) - 1)
                eq('isApproved', 1)
            }.student.id.unique()
        } else {
            studentsWhoFeePaidPrevious = feePaidObj.list {
                eq('feeType', FeeType.findById(params.feeType))
                eq('semesterValue', Integer.parseInt(params.semester) - 1)
                eq('isApproved', 1)
                not('in'('student', studentsWhoFeePaid))
            }.student.id.unique()
        }

        println("dfdfff>>>" + studentsWhoFeePaid)
        if (params.semester == '1') {
            if (studentsWhoFeePaid.size() > 0) {
                if (params.program != 'All') {
                    stuList = obj.list {
                        programDetail {
                            eq('id', Long.parseLong(params.program))
                        }
                        studyCentre {
                            eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                        }
                        and {
                            not('in'('student', studentsWhoFeePaid))
                        }

                    }
                } else {
                    stuList = obj.list {
                        studyCentre {
                            eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                        }
                        and {
                            not('in'('student', studentsWhoFeePaid))
                        }
                    }
                }
            } else {
                if (params.program != 'All') {
                    stuList = obj.list {
                        programDetail {
                            eq('id', Long.parseLong(params.program))
                        }
                        studyCentre {
                            eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                        }

                    }
                } else {
                    stuList = obj.list {
                        studyCentre {
                            eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                        }
                    }
                }
            }
        } else {
            studentsWhoFeePaidPrevious.each {
                stuList << Student.findById(it)
            }

        }

        println("*******************>> " + stuList)
        if (stuList.size() > 0) {
            def bankName = Bank.list(sort: 'bankName')
            def paymentMode = PaymentMode.list(sort: 'paymentModeName')
            def feeList = FeeType.list(sort: 'type')
            def feeAmountList = []
            def lateFee = 0
            def prevProgram = null
            def payableAmount = 0
            def today = new Date()
            println("this is the size " + stuList.size())
            if (params.program == 'All' && params.semester == 'All') {
                for (int i = 0; i < stuList.size(); i++) {
                    int year = stuList[i].registrationYear
                    def sessionVal = year + 1
                    sessionVal = year + '-' + sessionVal

                    def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[i].programDetail[0].id), sessionVal)

                    if (prevProgram != stuList[i].programDetail.courseName) {
                        def lateFeeDate = stuList[i].programDetail.lateFeeDate[0]
                        def amount
                        if(params.feeType=='3'){
                            amount= AdmissionFee.findByFeeSession(feeSessionObj)
                            prevProgram = stuList[i].programDetail.courseName
                            if (lateFeeDate != null) {
                                if (today.compareTo(lateFeeDate) > 0) {
                                    lateFee = amount.lateFeeAmount
                                }
                            }
                            payableAmount = amount.feeAmountAtSC + lateFee
                        }else{
                            payableAmount= MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj,FeeType.findById(params.feeType)).amount
                        }

                    }
                    feeAmountList.add(payableAmount)
                }
            } else {
//            println("??????????"+stuList[0])

                int year = stuList[0].registrationYear
                def sessionVal = year + 1
                sessionVal = year + '-' + sessionVal

                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[0].programDetail[0].id), sessionVal)
                if(params.feeType=='3'){
                    def lateFeeDate = stuList[0].programDetail.lateFeeDate[0]
                    def amount = AdmissionFee.findByFeeSession(feeSessionObj)
                    if (lateFeeDate != null) {
                        if (today.compareTo(lateFeeDate) > 0) {
                            lateFee = amount.lateFeeAmount
                        }
                    }
                    payableAmount = amount.feeAmountAtSC + lateFee
                }else{
                    payableAmount= MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj,FeeType.findById(params.feeType)).amount
                }

                for (int i = 0; i < stuList.size(); i++) {
                    feeAmountList.add(payableAmount)
                }
            }
            resultMap.studentList = stuList
            resultMap.bankName = bankName
            resultMap.paymentMode = paymentMode
            resultMap.term = params.semester
            resultMap.feeList = feeList
            resultMap.feeAmount = feeAmountList
        } else {
            resultMap.studentError = "NO STUDENT AVAILABLE"
        }
        return resultMap
    }

    def StudentListForMFee(params) {
        println(params)
        def resultMap = [:]
        def obj = Student.createCriteria()
        def currentUser = springSecurityService.getCurrentUser()
        def stuList = []
        if (params.program != 'All' && params.semester != 'All') {
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.program))
                }
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                    eq('semester', Integer.parseInt(params.semester))
                }
            }
        } else if (params.program == 'All' && params.semester != 'All') {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                    eq('semester', Long.parseLong(params.semester))
                }
            }

        } else if (params.program != 'All' && params.semester == 'All') {
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.program))
                }
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                }
            }
        } else {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                }
            }
        }
        def bankName = Bank.list(sort: 'bankName')
        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
        def feeList = FeeType.list(sort: 'type')
        def feeAmountList = []







        for (int i = 0; i < stuList.size(); i++) {
            int year = stuList[i].registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[i].programDetail[0].id), sessionVal)
            def amount = MiscellaneousFee.findAllByFeeSessionAndFeeType(feeSessionObj, FeeType.findById(params.feeType))
//            println(amount)
            feeAmountList.add(amount.amount)
        }
        resultMap.studentList = stuList
        resultMap.bankName = bankName
        resultMap.paymentMode = paymentMode
        resultMap.feeList = feeList
        resultMap.feeAmount = feeAmountList
        return resultMap
    }
    def AllProgramStudentList = {
        def resultMap = [:]
        def obj = Student.createCriteria()
        def currentUser = springSecurityService.getCurrentUser()
        def stuList = obj.list {
            studyCentre {
                eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
            }
            and {
                eq('status', Status.findById(2))
            }
        }
        def bankName = Bank.list(sort: 'bankName')
        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
        def feeList = FeeType.list(sort: 'type')
        def feeAmountList = []
        for (int i = 0; i < stuList.size(); i++) {
            int year = stuList[i].registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuList[i].programDetail[0].id), sessionVal)
            def amount = AdmissionFee.findAllByFeeSession(feeSessionObj)
            feeAmountList.add(amount.feeAmountAtSC)
        }
        resultMap.studentList = stuList
        resultMap.bankName = bankName
        resultMap.paymentMode = paymentMode
        resultMap.feeList = feeList
        resultMap.feeAmountList = feeAmountList
        return resultMap
    }


    def studentDetailByChallanNumber(params) {
        def returnMap = [:]
        def stuList = []
        def lateFee = 0
        def courseNameList = [], courseFee = [], studyCentreList = []
        def tempStuList = Student.findAllByChallanNo(params.challanNo)
//        if(!tempStuList){
//            return null
//        }
        def currentUser = springSecurityService.currentUser
        def feeDetails = FeeDetails.findByChallanNo(params.challanNo)
        def rollStatus = true
        tempStuList.each {
            if ((!(it.status.id == 4)) && (it.rollNo != null)) {
                stuList.add(it)
            }
            if (it.rollNo == null) {
                rollStatus = false
            }
        }
        stuList.each {
//            println("==="+it.programDetail[0])
            lateFee = 0
            def lateFeeDate = it.programDetail.lateFeeDate[0]
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    int year = it.registrationYear
                    def sessionVal = year + 1
                    sessionVal = year + '-' + sessionVal

                    def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)
                    lateFee = AdmissionFee.findByFeeSession(feeSessionObj).lateFeeAmount
                }
            }
            courseNameList << it.programDetail[0].courseName
            studyCentreList << StudyCenter.findById(it.studyCentre.id).name


            int year = it.registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)
            def programFee = AdmissionFee.findByFeeSession(feeSessionObj)
            //if(StudyCenter.findAllById(.studyCentreId).centerCode[0]=="11111") {
            if (it.studyCentre.centerCode[0] == "11111") {
                courseFee << AdmissionFee.findByFeeSession(feeSessionObj).feeAmountAtIDOL + lateFee
            } else {
                courseFee << AdmissionFee.findByFeeSession(feeSessionObj).feeAmountAtSC + lateFee
            }
        }
        println("Study" + studyCentreList[0])
        returnMap.stuList = stuList
        returnMap.rollStatus = rollStatus
        returnMap.studyCentreList = studyCentreList
        returnMap.courseNameList = courseNameList
        returnMap.courseFee = courseFee
        returnMap.paymentReferenceNumber = feeDetails.paymentReferenceNumber
        returnMap.bank = feeDetails.bankId.bankName
        returnMap.branch = feeDetails.branchId.branchLocation
        return returnMap

    }

    def rollNumberFeeStatus(params) {
        def returnMap = [:]
        def studInst = Student.findByRollNo(params.rollNo)

        def miscFeeList = FeeDetails.findAllByStudent(studInst)
//            println(miscFeeList)
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
        def miscFeeStatus = []
        def miscFeetype = []
        def mPayDate = []
        def challan = []

        miscFeeList.each {
            def status = it.isApproved
            challan << it.challanNo
            println(status)
            if (status == 1) {
                miscFeeStatus.add('Approved')
                mPayDate << df.format(it.paymentDate)
            } else {
                miscFeeStatus.add('Not Approved')
                mPayDate << "Not Paid"
            }
            miscFeetype.add(it.feeType.type)

        }
        returnMap.mPayDate = mPayDate
        returnMap.studInst = studInst

        returnMap.miscFeeList = miscFeeList
        returnMap.miscFeeStatus = miscFeeStatus
        returnMap.miscFeetype = miscFeetype
        return returnMap
    }

    def deleteFee(params) {
        def status = true
        FeeType feeType = FeeType.get(params.int('data'))
        def obj = MiscellaneousFee.findByFeeType(feeType)
        obj.each {
            it.delete(failOnError: true)
        }
        def objMisFeeChallan = FeeDetails.findByFeeType(feeType)
        objMisFeeChallan.each {
            it.delete(failOnError: true)
        }

        feeType.delete(flush: true, failOnError: true)

        if (feeType.exists(params.int('data'))) {
            status = false
        }
        return status

    }

    def searchByRollNumber(params) {
        def returnMap = [:]
        def stuIns = Student.findByRollNo(params.rollNumber)
        if (stuIns) {
            returnMap.courseName = stuIns.programDetail.courseName
            returnMap.courseId = stuIns.programDetail.id
            returnMap.totalYears = stuIns.programDetail.noOfAcademicYears
            returnMap.programType = stuIns.programDetail.programType.type
            returnMap.currrentSemester = stuIns.semester
            returnMap.feeType = FeeType.list()
            returnMap.status = true
        } else {
            returnMap.status = false
        }
        return returnMap


    }

    def getChallanDetails(params) {
        def returnMap = [:]
        def challanInst = FeeDetails.findAllByChallanNo(params.challanNo)
        def rollNo = []
        def program = []
        def feeAmount = []
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        def studyCentre, status, paymentMode, refNo
        def total = 0
        println(challanInst.size())
        if (challanInst) {
            challanInst.each {
                rollNo << it.student.rollNo
                program << it.student.programDetail.courseName
                feeAmount << it.paidAmount
                total += it.paidAmount
            }
            returnMap.studyCentre = challanInst[0].student.studyCentre.name
            returnMap.paymentMode = challanInst[0].paymentModeId.paymentModeName
            returnMap.refNo = challanInst[0].paymentReferenceNumber
            returnMap.paydate = df.format(challanInst[0].paymentDate)
            returnMap.bank = challanInst[0].bankId.bankName
            returnMap.branch = challanInst[0].branchId.branchLocation
            if (challanInst[0].isApproved == '1') {
                returnMap.status = 'Approved'
            } else {
                returnMap.status = 'Not Approved'
            }
            returnMap.challanInst = challanInst
            returnMap.rollNo = rollNo
            returnMap.program = program
            returnMap.feeAmount = feeAmount
            returnMap.total = total
        } else {
            returnMap.challanError = 'Incorrect Challan Number or Its a custom Challan Number.'
            println("dfdfdfd")
        }
        return returnMap
    }
}


