package universityproject

import examinationproject.Bank
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramFee
import examinationproject.Status
import examinationproject.Student
import grails.converters.JSON
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
        def stuList = obj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
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
        def programDetail=ProgramDetail.findAllById(params.programId)
        def feeAmount=ProgramFee.findAllByProgramDetail(programDetail)
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeList=feeList
        resultMap.feeAmount=feeAmount.feeAmountAtSC
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
            def amount=ProgramFee.findAllByProgramDetail(stuList.programDetail)
            feeAmountList.add(amount.feeAmountAtSC)
        }
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeList=feeList
        resultMap.feeAmountList=feeAmountList
        return resultMap
    }
}


