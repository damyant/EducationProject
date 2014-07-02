package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

//@Transactional(readOnly = true)
class FeeDetailsController {
    def feeDetailService
    def studentRegistrationService
    def pdfRenderingService
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    @Secured("ROLE_ADMIN")
    def createFeeDetails() {
        def paymentModeList = PaymentMode.list(sort: 'paymentModeName')
        respond new FeeDetails(params)
    }

    @Transactional
    def saveFeeDetails(FeeDetails feeDetailsInstance) {


        if (feeDetailsInstance == null) {
            return
        }

        feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            respond feeDetailsInstance.errors, view: 'createFeeDetails'
            return
        }

        if (feeDetailsInstance) {
            flash.message = "Fee Details Entered Successfully."
            redirect(action: "createFeeDetails")
        }
    }

    def editFeeDetails(FeeDetails feeDetailsInstance) {
        respond feeDetailsInstance
    }

    @Transactional
    def updateFeeDetails(FeeDetails feeDetailsInstance) {


        if (feeDetailsInstance.hasErrors()) {
            respond feeDetailsInstance.errors, view: 'editFeeDetails'
            return
        }

        if (feeDetailsInstance.save(flush: true)) {
            redirect(action: "createFeeDetails")
        }
    }
    @Secured("ROLE_ADMIN")
    def bulkFeeEntry = {

        def filterType = []
        def programList = ProgramDetail.list()
        def studyCentre = StudyCenter.list();
        filterType.add("By Program")
        filterType.add("By Study Centre")
//         filterType.add("By Admission Date")

        [filterType: filterType, programList: programList, studyCentre: studyCentre]
    }

    @Secured("ROLE_ADMIN")
    def getStudentList() {
        def responseMap = [:]
        def stuList = feeDetailService.provisionalStudentList(params)
        responseMap.status = "referenceNo"
        responseMap.label = params.pageType
        responseMap.stuList = stuList
        render responseMap as JSON

    }

    def checkStudentByRollNo = {
        def student = Student.findByRollNo(params.rollNo)
        def response = [id: student.id, feeStatus: true]
        render response as JSON
    }

    def saveBulkFeeDetails = {
//        println("hello kuldeep now save your data" + params)
        def feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            render "<h5>Fee Details for this student cannot be saved</h5>"
        }

        if (feeDetailsInstance) {
            render "<h5>Fee Details saved</h5>"
        }

    }
    @Secured("ROLE_STUDY_CENTRE")
    def payAdmissionFee = {
        def bankName = Bank.list(sort: 'bankName')
        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
        [bankName: bankName, paymentMode: paymentMode]
    }

    @Secured("ROLE_STUDY_CENTRE")
    def generateChallanSCAdmissionFee = {
        def programList = ProgramDetail.list(sort: 'courseName')
        def programCategory = ProgramType.list(sort: 'type')
        def miscFeeType = FeeType.list(sort: 'type')
        [programList: programList, programCategory: programCategory, miscFeeType: miscFeeType]
    }

    def loadProgram = {
//        println("params"+params)
        def programType = ProgramType.findById(Long.parseLong(params.type))
        def programList = ProgramDetail.findAllByProgramType(programType)
        def response = [programList: programList]
        println(response.programList[0].courseName)
        render response as JSON
    }
    @Secured("ROLE_STUDY_CENTRE")
    def challanForMiscellaneousFee = {
        def programList = ProgramDetail.list(sort: 'courseName')
        def programCategory = ProgramType.list(sort: 'type')
        def miscFeeType = FeeType.list(sort: 'type')
        [programList: programList, programCategory: programCategory, miscFeeType: miscFeeType]
    }

    @Secured("ROLE_STUDY_CENTRE")
    def payMiscellaneousFee = {
        def bankName = Bank.list(sort: 'bankName')
        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
        [bankName: bankName, paymentMode: paymentMode]
    }
    def populateStudents = {
        def resultMap = [:]
        resultMap = feeDetailService.StudentList(params)
        render resultMap as JSON
    }
    def populateStudentsForStudyCenter = {
        def resultMap = [:]
        if(params.feeType=='3'){
            resultMap = feeDetailService.StudentListForAdmission(params)
        }
        else if(params.feeType=='2'){
            resultMap = feeDetailService.StudentListForCertificate(params)
        }
        else{
            resultMap = feeDetailService.StudentListForMFee(params)
        }
        render resultMap as JSON
    }
    def populateStudentsForMFee = {
        def resultMap = [:]
        resultMap = feeDetailService.StudentListForMFee(params)
        render resultMap as JSON
    }

    def populateStudentsForAllProgram = {
//        println("in method");
        def resultMap = [:]
        resultMap = feeDetailService.AllProgramStudentList()
//        println("in method"+resultMap);
        render resultMap as JSON
    }

    def getBankBranch = {

        def bankIns = Bank.findById(Long.parseLong(params.bankId))
        def branchName = bankIns.branch
        render branchName as JSON

    }
    def saveFeeData = {

        feeDetailService.saveFeeDetails(params)
        def resultMap = [:]
        resultMap = feeDetailService.StudentList(params)
        render resultMap as JSON

    }
    def getFeeAmount = {
//        println("################################################ ====>"+params);
        def resultMap = [:]
        def student = Student.findById(params.studentId)
        def programFee = AdmissionFee.findByFeeSession(FeeSession.findByProgramDetailId(student.programDetail[0]));
//        println(student.programDetail)
        def programFeeAmount
//        println("type --->"+params.feeType)
        def feeType = params.feeType
        if (feeType == 1) {
            programFeeAmount = programFee.feeAmountAtSC
//            println("1")
        } else if (feeType == 2) {
            programFeeAmount = programFee.examinationFee
//            println("2")
        } else {
            programFeeAmount = programFee.certificateFee
//                println("3")
        }
        resultMap.programFeeAmount = programFeeAmount;
//        println(programFee.feeAmountAtSC)
//        println(resultMap)
        render resultMap as JSON
    }


    def challanForStudyCenterStu = {
        println("***************" + params)
        List<Student> studList = []
        List<AdmissionFee> addmissionFee = []
        studList.clear()
        addmissionFee.clear()
        def stuList = []
        stuList.clear()
        def totalFee = 0;
        def lateFee = 0
        def feeDetails
        def term
        def studyCentre
        def feeForStudent
        def challanNo = studentRegistrationService.getChallanNumber()
        def today = new Date()
        def feeTypeName = FeeType.findById(params.feeCategory).type

        if (params.rollNoSearch) {
            lateFee = 0

            def stuIns = Student.findByRollNo(params.rollNoSearch)
            studyCentre = stuIns.studyCentre

            studList.add(stuIns)
            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)

            int year = stuIns.registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)

            if (params.feeCategory == '3') {
                stuIns.challanNo = challanNo
                stuIns.save(failOnError: true)
                def lateFeeDate = stuIns.programDetail.lateFeeDate[0]
                feeForStudent = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,Integer.parseInt(params.semesterListHidden)).feeAmountAtSC
                if (lateFeeDate != null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,Integer.parseInt(params.semesterListHidden)).lateFeeAmount
                    }
                }
                feeForStudent = feeForStudent + lateFee
                totalFee = totalFee + feeForStudent
                def studInFeeDetails = FeeDetails.findByStudentAndFeeTypeAndSemesterValue(stuIns, FeeType.findById(3), Integer.parseInt(params.semesterListHidden))
                if (studInFeeDetails) {
                    feeDetails = studInFeeDetails
                } else {
                    feeDetails = new FeeDetails()
                }
            }
            else {
                feeForStudent=MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj,FeeType.findById(params.feeCategory)).amount
                totalFee = totalFee + feeForStudent
                feeDetails = new FeeDetails()
            }


            term = params.semesterListHidden
            feeDetails.feeType = FeeType.findById(params.feeCategory)
            feeDetails.paidAmount = feeForStudent
            feeDetails.challanNo = challanNo
            feeDetails.isApproved=Status.findById(1)
            feeDetails.challanDate = new Date()
            feeDetails.student = stuIns
            feeDetails.semesterValue = Integer.parseInt(params.semesterListHidden)
            feeDetails.save(failOnError: true, flush: true)
            addmissionFee.add(feeForStudent)
        } else {
            stuList = params.studentListId.split(",")
            def semesterList = params.semesterListHidden.split(",")
            term = Integer.parseInt(semesterList[0])
//                println(semesterList)
            for (def i = 0; i < stuList.size(); i++) {
                lateFee = 0
                def stuIns = Student.findById(Long.parseLong(stuList[i]))
                if (i == 0) {
                    studyCentre = stuIns.studyCentre
                }
                def FeeDetails
                studList.add(stuIns)
                int year = stuIns.registrationYear
                def sessionVal = year + 1
                sessionVal = year + '-' + sessionVal

                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)

                if (params.feeCategory == '3') {
                    stuIns.challanNo = challanNo
                    stuIns.save(failOnError: true)
                def lateFeeDate = stuIns.programDetail.lateFeeDate[0]


                Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)

                 feeForStudent = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,term).feeAmountAtSC
                if (lateFeeDate != null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,term).lateFeeAmount
                    }
                }
                feeForStudent = feeForStudent + lateFee
                totalFee = totalFee + feeForStudent
                def studInFeeDetails = examinationproject.FeeDetails.findByStudentAndFeeTypeAndSemesterValue(stuIns, FeeType.findById(3), semesterList[i])

                if (studInFeeDetails) {
                    feeDetails = studInFeeDetails
                } else {
                    feeDetails = new FeeDetails()
                }
                }
                else {
                    feeForStudent=MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj,FeeType.findById(params.feeCategory)).amount
                    totalFee = totalFee + feeForStudent
                    feeDetails = new FeeDetails()
                }
                feeDetails.feeType = FeeType.findById(params.feeCategory)
                feeDetails.paidAmount = feeForStudent
                feeDetails.challanNo = challanNo
                feeDetails.challanDate = new Date()
                feeDetails.student = stuIns
                feeDetails.isApproved=Status.findById(1)
                feeDetails.semesterValue = Integer.parseInt(semesterList[i])
                feeDetails.save(failOnError: true, flush: true)
                addmissionFee.add(feeForStudent)
            }
        }

//        def termMesg = feeTypeName + ' for ' + termText + ' Term'
        def args = [template: "printChallan", model: [studList: studList,challanNo:challanNo, term: term, studyCentre: studyCentre, addmissionFee: addmissionFee, totalFee: totalFee, lateFee: lateFee], filename: challanNo + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
//    def generateChallanForMiscellaneousFee = {
////        println("***************"+params)
//        List<Student> studList = []
//        List<MiscellaneousFee> miscellaneousFee = []
//        studList.clear()
//        miscellaneousFee.clear()
//        def stuList = []
//        def totalFee = 0;
//        def studyCentre
//        def challanNo = studentRegistrationService.getChallanNumber()
//        def feeType = FeeType.findById(params.feeCategory)
//        if (params.rollNoSearch) {
////            def feeType=FeeType.findById(params.feeCategory)
//            def stuIns = Student.findByRollNo(params.rollNoSearch)
//            studyCentre = stuIns.studyCentre
//            def mFeeInstance = new FeeDetails()
//            mFeeInstance.challanNo = challanNo
//            mFeeInstance.feeType = feeType
//            mFeeInstance.student = stuIns
//            mFeeInstance.challanDate = new Date()
//            mFeeInstance.save(failOnError: true)
//            studList.add(stuIns)
//
//            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
//            int year = stuIns.registrationYear
//            def sessionVal = year + 1
//            sessionVal = year + '-' + sessionVal
//
//            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)
//
//            def feeForStudent = MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, feeType).amount
////            def feeForStudent=MiscellaneousFee.findByProgramDetailAndProgramSessionAndFeeType(programDetails[0],stuIns.programSession,feeType).amount
//
//            totalFee = totalFee + feeForStudent
//            miscellaneousFee.add(feeForStudent)
//        } else {
//            stuList = params.studentListId.split(",")
//            for (def i = 0; i < stuList.size(); i++) {
////                println("**********"+stuList[i]);
//                def mFeeInstance = new FeeDetails()
//                def stuIns = Student.findById(Long.parseLong(stuList[i]))
//                if (i == 0) {
//                    studyCentre = stuIns.studyCentre
//                }
//                mFeeInstance.challanNo = challanNo
//                mFeeInstance.feeType = feeType
//                mFeeInstance.student = stuIns
//                mFeeInstance.challanDate = new Date()
//                Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
//                int year = stuIns.registrationYear
//                def sessionVal = year + 1
//                sessionVal = year + '-' + sessionVal
//
//                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)
//
//                def feeForStudent = MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, feeType).amount
//
////                def feeForStudent=MiscellaneousFee.findByProgramDetailAndProgramSessionAndFeeType(programDetails[0],stuIns.programSession,feeType).amount
//                totalFee = totalFee + feeForStudent
//                miscellaneousFee.add(feeForStudent)
//                mFeeInstance.paidAmount = feeForStudent
////                stuIns.challanNo=challanNo
//                mFeeInstance.save(failOnError: true)
//                studList.add(stuIns)
//
//            }
//
//        }
//
////        println("sdsdsdsdsds"+Student.findById(Long.parseLong(stuList[0])).studyCentre.name)
//        def args = [template: "printMiscFeeChallan", model: [studList: studList, feesType: feeType, studyCentre: studyCentre, challanNo: challanNo, miscellaneousFee: miscellaneousFee, totalFee: totalFee], filename: challanNo + ".pdf"]
//        pdfRenderingService.render(args + [controller: this], response)
//    }

    def printChallan = {

    }

    def populateStudentsByChallan = {
        def resultMap = [:]
        resultMap = feeDetailService.StudentListByChallan(params)
//        println(resultMap)
        render resultMap as JSON
    }


    def payChallanForStudyCenterStu = {
//       println("***************"+params)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        def courseNameList = [], courseFee = []
        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.searchChallanNo)
        def stuList=[]

        feeDetailsInstance.each {
            stuList<<it.student
        }
        def currentUser = springSecurityService.currentUser
        def totalFee = 0;
        def lateFee = 0
        def studyCentre
        studyCentre = stuList[0].studyCentre
        stuList.each {
            lateFee = 0
//            println("==="+it.programDetail[0])
            def lateFeeDate = it.programDetail.lateFeeDate[0]
            def today = new Date()

            int year = it.registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)

            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,feeDetailsInstance[0].semesterValue).lateFeeAmount
                }
            }
            courseNameList << it.programDetail[0].courseName
            if (it.studyCentre.centerCode[0] == "11111") {
                courseFee << AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,feeDetailsInstance[0].semesterValue).feeAmountAtIDOL + lateFee
            } else {
                courseFee << AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,feeDetailsInstance[0].semesterValue).feeAmountAtSC + lateFee
            }
        }
        for (def k = 0; k < courseFee.size(); k++) {
            totalFee = totalFee + courseFee[k]
        }
//        println(params)
        def paymentModeName = PaymentMode.findById(params.paymentMode)
        def bank = Bank.findById(params.bankName)
        def branch = Branch.findById(params.branchLocation)
        def termList = []
        feeDetailsInstance.each {
            termList << it.semesterValue
            it.paymentModeId = paymentModeName
            it.paymentReferenceNumber = Integer.parseInt(params.paymentReferenceNumber)
            it.bankId = Bank.findById(params.bankName)
            it.branchId = Branch.findById(params.branchLocation)

            it.paymentDate = df.parse(params.paymentDate)
            if (PaymentMode.findById(params.paymentMode).paymentModeName != 'Pay In Slip') {
                it.isApproved = Status.findById(3)
                if (it.save(flush: true, failOnError: true)) {
                    if(it.semesterValue==1){
                        it.student.status = Status.findById(3)
                    }
                }
            } else {
                it.isApproved = Status.findById(4)
                if (it.save(flush: true, failOnError: true)) {
                    if(it.semesterValue==1){
                        it.student.status = Status.findById(4)
                    }
                }
            }
        }

        def challanNo = params.searchChallanNo
        def paymentDate = params.paymentDate
        def paymentReferenceNumber = params.paymentReferenceNumber
        def args = [template: "printPayChallan", model: [bank: bank, termList: termList, lateFee: lateFee, studyCentre: studyCentre, branch: branch, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)

    }
    def payMiscFeeChallan = {
        def courseNameList = [], courseFee = [], feeType = []
        def stuList = []
        def studyCentre
        def miscFeeChallanList = FeeDetails.findAllByChallanNo(params.searchChallanNo)
        miscFeeChallanList.each {
            stuList << it.student
            courseNameList << it.student.programDetail[0].courseName
            int year = it.student.registrationYear
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal
            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.student.programDetail[0].id), sessionVal)
            println("session"+feeSessionObj)
            println("session"+it.feeType)
            courseFee << MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, it.feeType).amount
        }
        def totalFee = 0;
        for (def k = 0; k < courseFee.size(); k++) {
            totalFee = totalFee + courseFee[k]
        }
        def paymentModeName = PaymentMode.findById(params.paymentMode)
        def bank = Bank.findById(params.bankName)
        def branch = Branch.findById(params.branchLocation)
        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.searchChallanNo)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        def status = false
        feeDetailsInstance.each {
            it.paymentModeId = PaymentMode.findById(params.paymentMode)
            it.bankId = bank
            it.branchId = branch
//            it.isApproved = Status.findById(3)

            it.paymentReferenceNumber = Integer.parseInt(params.paymentReferenceNumber)

            it.paymentDate = df.parse(params.paymentDate)
            if (PaymentMode.findById(params.paymentMode).paymentModeName != 'Pay In Slip') {
                it.isApproved = Status.findById(3)
                if (it.save(flush: true, failOnError: true)) {
                    status = true
                }
            } else {
                it.isApproved = Status.findById(4)
                if (it.save(flush: true, failOnError: true)) {
                    status = true
                }
            }
//            if (it.save(flush: true, failOnError: true)) {
//                status = true
//            }
        }
        studyCentre = stuList[0].studyCentre
        def challanNo = params.searchChallanNo
        def paymentDate = params.paymentDate
        def paymentReferenceNumber = params.paymentReferenceNumber

        def args = [template: "printPayMiscFeeChallan", model: [bank: bank, studyCentre: studyCentre, feeType: feeType, branch: branch, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)

    }
    def gerStudentId = {
        def resultMap = [:]
        def studentId = Student.findByRollNo(params.rollNo).id
        resultMap.studentId = studentId
        render resultMap as JSON
    }
    @Secured(["ROLE_ADMIN"])
    def feeStatusForRollNumber = {

    }
    @Secured(["ROLE_ADMIN"])
    def checkRollNoFeeStatus = {
        def resultMap = [:]
        resultMap = feeDetailService.rollNumberFeeStatus(params)
//        println(resultMap)
        render resultMap as JSON
    }

    def postAdmissionFeeAtIdol = {
        if(params.amount){
            def feeDetailIns =  FeeDetails.findById(Integer.parseInt(params.misFeeObject))
            def program = feeDetailIns.student.programDetail[0].courseName
            [misFeeObject:feeDetailIns , amount: params.amount, program:program]
        }
    }

    def searchDataByRollNumber = {
        def status = feeDetailService.searchByRollNumber(params)
        render status as JSON
    }

    def searchHomeAssignmentByRollNumber ={

    }

    def checkRollNoPreviousData = {
        def returnMap = [:]
        def studObj = Student.findByRollNo(params.rollNumberInput)
        def feeType = FeeType.findById(Long.parseLong(params.postFeeType))
        //code of ajay.............................
//        def misFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeType(studObj, Integer.parseInt(params.semester), FeeType.findById(Long.parseLong(params.postFeeType)))
//        println("+++++++++++++++++" + misFeeObj)
//        if (misFeeObj) {
//            returnMap.feePaid = true
//        } else if (Integer.parseInt(params.semester) > 1) {
//            def misFeeObj2 = FeeDetails.findByStudentAndSemesterValueAndFeeType(studObj, Integer.parseInt(params.semester) - 1, FeeType.findById(Long.parseLong(params.postFeeType)))
//            if (misFeeObj2) {
//            } else {
//                returnMap.feePaid = false
//            }
//
//        }
//        println(returnMap)
//        render returnMap as JSON
//*******************************************************************************************************
        if (feeType.type == 'Admission Fee') {
            println('checking for admission fee')
            def currFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, Integer.parseInt(params.semester), FeeType.findById(Long.parseLong(params.postFeeType)), Status.findById(4))
            if (currFeeObj) {
                println('fees of curren sem is paid')
                returnMap.feePaid = true

            } else {
                int semValue = Integer.parseInt(params.semester) - 1
                def misFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, semValue, FeeType.findById(Long.parseLong(params.postFeeType)), Status.findById(4))
                if (misFeeObj) {
                } else {
                    returnMap.feePaid = false
                }
            }
        } else if (feeType.type == 'Examination Fee') {
            def currFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, Integer.parseInt(params.semester), FeeType.findById(Long.parseLong(params.postFeeType)), Status.findById(4))
            if (currFeeObj) {
                returnMap.feePaid = true
            } else {
                def admissionFee = FeeType.findByType('Admission Fee')
                def misFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, Integer.parseInt(params.semester), admissionFee, Status.findById(4))
                if (misFeeObj) {

                } else {
                    returnMap.feePaid = false
                }
            }
        } else if (feeType.type == 'Certificate Fee') {
            println('in certivicate fee')
            def maxTime
            def currFeeObj = FeeDetails.findByStudentAndFeeTypeAndIsApproved(studObj, FeeType.findById(Long.parseLong(params.postFeeType)), Status.findById(4))
            if (currFeeObj) {
                returnMap.feePaid = true
            } else {
                println('in else of certificate fee')
                def programType = studObj.programDetail[0].programType.id
                if (programType == 1) {
                    maxTime = studObj.programDetail[0].noOfAcademicYears
                } else {
                    maxTime = studObj.programDetail[0].noOfTerms
                }
                println('this is the semester value ' + maxTime)
                def examFee = FeeType.findByType('Examination Fee')
                def feeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, maxTime, examFee, Status.findById(4))
                if (feeObj) {


                } else {
                    returnMap.feePaid = false
                }
            }
        }
        println(returnMap)
        render returnMap as JSON
    }

    def savePostExamFee = {
        def misFeeObj = new FeeDetails()
        misFeeObj.semesterValue = Integer.parseInt(params.semester)
        misFeeObj.challanNo = studentRegistrationService.getChallanNumber()
        misFeeObj.feeType = FeeType.findById(Long.parseLong(params.postFeeType))
        misFeeObj.student = Student.findByRollNo(params.rollNumberInput)
        misFeeObj.isApproved = Status.findById(1)
        def progmIns = ProgramDetail.findById(Long.parseLong(misFeeObj.student.programDetail[0].id.toString()))
        def feeSessionObj = FeeSession.findByProgramDetailId(progmIns)
        def amount
        if (Integer.parseInt(params.postFeeType) == 3) {
            def feeAmount= AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,Integer.parseInt(params.semester))
            amount = feeAmount.feeAmountAtIDOL+feeAmount.lateFeeAmount
        } else {
            def misFee = MiscellaneousFee.findByFeeTypeAndFeeSession(misFeeObj.feeType, feeSessionObj)
            amount = misFee.amount

        }
        misFeeObj.paidAmount= amount
        misFeeObj.save(failOnError: true)
        redirect(action: 'postAdmissionFeeAtIdol', params:[misFeeObject: misFeeObj.id, amount: amount])
//        stoping pdf download and opening popup window..................
//        def args = [template: "postFeeVoucher", model: [misFeeObject: misFeeObj, amount: amount], filename:misFeeObj.challanNo+".pdf"]
//        pdfRenderingService.render(args + [controller: this], response)


    }


    def challanNumberStatus = {

    }

    def challanDetails = {
        println(params)
        def result = feeDetailService.getChallanDetails(params)
        render result as JSON
    }
}
