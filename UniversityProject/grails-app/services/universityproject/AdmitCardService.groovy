package universityproject

import examinationproject.ExaminationCentre
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class AdmitCardService {

    def serviceMethod() {

    }


    def getStudents(params) {

        def obj = Student.createCriteria()
        def studentList = obj.list {
            programDetail {
                eq('id', Long.parseLong(params.programList))
            }
            examinationCentre {
                eq('id', Long.parseLong(params.examinationVenue))
            }
            and {
                eq('programSession', ProgramSession.findById(Integer.parseInt(params.programSession)))
            }
            and {
                eq('status', Status.findById(3))
            }

            and {
                eq('semester', Integer.parseInt(params.programTerm))
            }
            and {
                eq('admitCardGenerated', false)

            }


        }

        return studentList


    }
}
