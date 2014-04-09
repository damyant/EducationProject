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

    def serviceMethod() {

    }

    def saveFeeDetails (params){
        println("params  >>>>>>>>>>>>"+params)
        def feeDetailsInstance = new FeeDetails()
        def student = Student.findById(params.studentId)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        println("params  >>>>>>>>>>>>1")
        feeDetailsInstance.studentId = student
        println("params  >>>>>>>>>>>>2")
        feeDetailsInstance.draftDate=df.parse(params.draftDate)
        feeDetailsInstance.paymentDate=df.parse(params.paymentDate)
        println("params  >>>>>>>>>>>>3")
        feeDetailsInstance.draftNumber= params.draftNumber
        feeDetailsInstance.feeType = FeeType.findById(params.feeType)
        println("params  >>>>>>>>>>>>4")
        feeDetailsInstance.issuingBank = params.issuingBank
        feeDetailsInstance.issuingBranch = params.issuingBranch
        println("params  >>>>>>>>>>>>5")
        feeDetailsInstance.paymentMode = params.paymentMode

        feeDetailsInstance.save(flush: true)

        return feeDetailsInstance
    }

    def  provisionalStudentList(params){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        def studyCenterId=0
        def statusObj
        Date date=null
        if(params.admissionDate){
             date = df.parse(params.admissionDate)
        }
        if(params.studyCenterId){
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()

            studyCenterId=currentUser.studyCentreId
        }


        if(params.pageType=="Registered RollNo"){

            statusObj=Status.findById(2)
        }
        else{
            statusObj=Status.findById(1)
        }
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
                    eq('id', Long.parseLong(params.studyCenterId))
                }
                and{
                    eq('status',statusObj)
                }
            }
        }else if(params.admissionDate!='null'){

            stuList = Student.findAllByAdmissionDateAndStatus(date,statusObj)
        }

        return  stuList
    }
}
