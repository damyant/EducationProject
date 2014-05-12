package universityproject

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.PaymentMode
import examinationproject.ProgramDetail

import examinationproject.Status
import examinationproject.Student
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
        for (int i=0;i<stuList.size();i++){
            def amount=AdmissionFee.findAllByProgramDetail(stuList[i].programDetail)
            feeAmountList.add(amount.feeAmountAtSC)
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
        def courseNameList=[],courseFee=[]
        def stuList=  Student.findAllByChallanNo(params.challanNo)
        def feeDetails = FeeDetails.findByChallanNo(params.challanNo)

        stuList.each{
            println("==="+it.programDetail[0])
            courseNameList<<it.programDetail[0].courseName
            courseFee<<AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC
        }
        returnMap.stuList=stuList
        returnMap.courseNameList=courseNameList
        returnMap.courseFee=courseFee
        returnMap.bank=feeDetails.bankId.bankName
        returnMap.branch=feeDetails.branchId.branchLocation
        return returnMap
//=======
//        def StudentListByChallan={
//            def resultMap=[:]
//            def studList=Student.findAllByChallanNo(params.challanNo)
//            def feeAmountList=[]
//            for (int i=0;i<stuList.size();i++){
//                def amount=AdmissionFee.findAllByProgramDetail(stuList[i].programDetail)
//                feeAmountList.add(amount.feeAmountAtSC)
//            }
//            resultMap.studList=studList
//            resultMap.feeAmountList=feeAmountList
//            return resultMap
//            >>>>>>> 6dde9414035ea88164f9f1e9b7c2f804c236d5a1
    }
}


