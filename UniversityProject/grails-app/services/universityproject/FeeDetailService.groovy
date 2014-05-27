package universityproject

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
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
          def studentIns=Student.findById(Long.parseLong(params.studentId))
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

    def StudentList(params){

        def resultMap=[:]
        def obj = Student.createCriteria()
        def currentUser=springSecurityService.getCurrentUser()
        def stuList=[]
        if(params.program!='All' && params.semester!='All'){
            stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.program))
                }
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                    eq('semester', Integer.parseInt(params.semester))
                }
            }
        }
        else if(params.program=='All' && params.semester!='All'){
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                    eq('semester', Long.parseLong(params.semester))
                }
            }

        }
        else if(params.program!='All' && params.semester=='All'){
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
        }
        else {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(2))
                }
            }
        }
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        def feeList = FeeType.list(sort:'type')
        def feeAmountList=[]
        def lateFee=0
         println("this is the size "+stuList.size())
        for (int i=0;i<stuList.size();i++){
            def lateFeeDate=stuList[i].programDetail.lateFeeDate[0]
            println("this is late fee Date "+stuList[i].programDetail.courseName)
            def today=new Date()
            def amount=AdmissionFee.findAllByProgramDetail(stuList[i].programDetail)
            println("this is the amount "+ amount)
            if(today.compareTo(lateFeeDate) > 0){
                lateFee=amount[0].lateFeeAmount
            }
            def payableAmount=amount[0].feeAmountAtSC+lateFee
            feeAmountList.add(payableAmount)
        }
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeList=feeList
        resultMap.feeAmount=feeAmountList
        return resultMap
    }

    def StudentListForMFee(params){

        def resultMap=[:]
        def obj = Student.createCriteria()
        def currentUser=springSecurityService.getCurrentUser()
        def stuList=[]
        if(params.program!='All' && params.semester!='All'){
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
        }
        else if(params.program=='All' && params.semester!='All'){
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                    eq('semester', Long.parseLong(params.semester))
                }
            }

        }
        else if(params.program!='All' && params.semester=='All'){
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
        }
        else {
            stuList = obj.list {
                studyCentre {
                    eq('id', Long.parseLong(currentUser.studyCentreId.toString()))
                }
                and {
                    eq('status', Status.findById(4))
                }
            }
        }
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        def feeList= FeeType.list(sort:'type')
        def feeAmountList=[]
        for (int i=0;i<stuList.size();i++){
            def amount=MiscellaneousFee.findAllByProgramDetailAndFeeType(stuList[i].programDetail,FeeType.findById(params.feeType))
            println(amount)
            feeAmountList.add(amount.amount)
        }
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeList=feeList
        resultMap.feeAmount=feeAmountList
        return resultMap
    }
    def AllProgramStudentList={
        def resultMap=[:]
        def obj = Student.createCriteria()
        def currentUser=springSecurityService.getCurrentUser()
        def stuList = obj.list {
            studyCentre {
                eq('id',Long.parseLong(currentUser.studyCentreId.toString()))
            }
            and {
                eq('status', Status.findById(2))
            }
        }
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        def feeList = FeeType.list(sort:'type')
        def feeAmountList=[]
        for (int i=0;i<stuList.size();i++){
            def amount=AdmissionFee.findAllByProgramDetail(stuList.programDetail)
            feeAmountList.add(amount.feeAmountAtSC)
        }
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeList=feeList
        resultMap.feeAmountList=feeAmountList
        return resultMap
    }


    def studentDetailByChallanNumber(params){
        def returnMap=[:]
        def stuList =[]
        def lateFee=0
        def courseNameList=[],courseFee=[]
        def tempStuList=  Student.findAllByChallanNo(params.challanNo)
//        if(!tempStuList){
//            return null
//        }
        def currentUser = springSecurityService.currentUser
        def feeDetails = FeeDetails.findByChallanNo(params.challanNo)
        tempStuList.each{
            if(!(it.status.id==4))
                stuList.add(it)
        }
        stuList.each{
//            println("==="+it.programDetail[0])
            lateFee=0
            def lateFeeDate=it.programDetail.lateFeeDate[0]
            def today=new Date()
            if(today.compareTo(lateFeeDate) > 0){
                lateFee=AdmissionFee.findByProgramDetail(it.programDetail[0]).lateFeeAmount
            }
            courseNameList<<it.programDetail[0].courseName
            //if(StudyCenter.findAllById(.studyCentreId).centerCode[0]=="11111") {
            if(it.studyCentre.centerCode[0]=="11111"){
            courseFee<<AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtIDOL+lateFee
            }else{
                courseFee<<AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC+lateFee
            }
        }
        returnMap.stuList=stuList
        returnMap.courseNameList=courseNameList
        returnMap.courseFee=courseFee
        returnMap.paymentReferenceNumber=feeDetails.paymentReferenceNumber
        returnMap.bank=feeDetails.bankId.bankName
        returnMap.branch=feeDetails.branchId.branchLocation
//        println("vvvvvvvvv"+returnMap)
        return returnMap

    }
}


