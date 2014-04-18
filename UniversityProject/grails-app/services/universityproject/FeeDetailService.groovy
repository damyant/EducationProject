package universityproject

import examinationproject.FeeDetails
import examinationproject.FeeType
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
    def saveFeeDetails (params){
        def feeDetailsInstance = new FeeDetails()
        def student = Student.findById(params.studentId)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        feeDetailsInstance.studentId = student
        feeDetailsInstance.draftDate=df.parse(params.draftDate)
        feeDetailsInstance.paymentDate=df.parse(params.paymentDate)
        feeDetailsInstance.draftNumber= params.draftNumber
        feeDetailsInstance.feeType = FeeType.findById(params.feeType)
        feeDetailsInstance.issuingBank = params.issuingBank
        feeDetailsInstance.issuingBranch = params.issuingBranch
        feeDetailsInstance.paymentMode = params.paymentMode

        if(feeDetailsInstance.save(flush: true,failOnError: true)){
            student.status = Status.findById(3)
            student.save(flush: true,failOnError: true)
        }

        return feeDetailsInstance
    }

    /**
     * Service to get provisional students
     * @param params
     * @return
     */
    def  provisionalStudentList(params){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        def studyCenterId=0
        def statusObj
        Date date=null
        if(params.admissionDate){
             date = df.parse(params.admissionDate)
        }
        if(params.studyCenterId){
            println("assign Study Centre1")
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()
            println("assign Study Centre2")
            studyCenterId=currentUser.studyCentreId
        }
        statusObj=Status.findById(2)


        def obj=Student .createCriteria()
        def stuList
        if(params.programId!='null'){
        stuList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            and{
                eq('status',statusObj)
            }
        }
        } else if(params.studyCenterId!='null'){
            stuList= obj.list{
                studyCentre{
                    eq('id', Long.parseLong(studyCenterId))
                }
                and{
                    eq('status',statusObj)
                }
            }
       }
        else if(params.admissionDate!='null'){

            stuList = Student.findAllByAdmissionDateAndStatus(date,statusObj)
        }

        return  stuList
    }
}
