package universityproject

import examinationproject.ExaminationVenue
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
                eq('id', Long.parseLong(params.examinationCentre))
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

    def getStudentByRollNo(user,params){

        def obj=Student .createCriteria()
       def stuList= obj.list{
            studyCentre{
                eq('id', Long.parseLong(user.studyCentreId.toString()))
            }
            and{
                eq('admitCardGenerated', true)

            }
            and{
                eq('rollNo',params.rollNumber.trim())
            }

        }

        return stuList

    }

    def getStudentByStudyCenter(user){

        def obj=Student .createCriteria()
        def stuList= obj.list{
            studyCentre{
                eq('id', Long.parseLong(user.studyCentreId.toString()))
            }
            and{
                eq('admitCardGenerated', true)

            }

        }

        return stuList

    }

    def updateStudentRecord(stuList,examVenueId){
        def examVenueObj=ExaminationVenue.findById(Long.parseLong(examVenueId))
        stuList.each{
            it.admitCardGenerated=true
            it.examinationVenue=examVenueObj
            it.save(failOnError: true)
        }
        return true

    }
}
