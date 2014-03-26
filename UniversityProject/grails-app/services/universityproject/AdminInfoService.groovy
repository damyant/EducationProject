package universityproject

import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class AdminInfoService {

    def serviceMethod() {

    }

    def provisionalStudentList(params){
        def obj=Student .createCriteria()
        def statusObj=Status.findById(1)
        def stuList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            studyCentre {
                eq('id', Long.parseLong(params.studyCenterId))
            }
            and{
                eq('status',statusObj)
            }

        }
        return  stuList



    }
}
