package universityproject

import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class AdminInfoService {
def springSecurityService
    def serviceMethod() {

    }

    def provisionalStudentList(params){
//        println("==========="+springSecurityService.principal.id)

        def studyCenterId=0
        def statusObj
        if(params.studyCenterId){
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()

            studyCenterId=currentUser.studyCentreId
        }


        if(params.pageType=="Approve RollNo"){

            statusObj=Status.findById(2)
        }
        else{
            statusObj=Status.findById(1)
        }
        def obj=Student .createCriteria()
        def stuList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            studyCentre {
                eq('id', Long.parseLong(studyCenterId.toString()))
            }
            and{
                eq('status',statusObj)
            }

        }
        return  stuList



    }
}
