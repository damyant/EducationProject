package universityproject

import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.ProgramSession
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class AdmitCardService {

    def serviceMethod() {

    }


    def getStudents(params) {
        println('these are the params ' + params)
        def obj = Student.createCriteria()
        def studentList=[]
        def stuList = obj.list {
            programDetail {
                eq('id', Long.parseLong(params.programList))
            }
            city {
                eq('id', Long.parseLong(params.examinationCentre))
            }
            and {
                eq('programSession', ProgramSession.findById(Integer.parseInt(params.programSession)))
            }
            and {
                eq('status', Status.findById(4))
            }
            and {
                eq('semester', Integer.parseInt(params.programTerm))
            }
            and {
                eq('admitCardGenerated', false)
            }
        }
        println("list " + stuList)
        stuList.each {
            def stuAdmissionFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(it, Integer.parseInt(params.programTerm), FeeType.findById(3), Status.findById(4))
            if (stuAdmissionFeeInst) {
                def stuExamFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(it, Integer.parseInt(params.programTerm), FeeType.findById(1), Status.findById(4))
                if (stuExamFeeInst) {
                    studentList << it
                }
            }
        }
        return studentList
    }

    def getStudentByRollNo(user, params) {

        def obj = Student.createCriteria()
        def stuList = obj.list {
            studyCentre {
                eq('id', Long.parseLong(user.studyCentreId.toString()))
            }
            and {
                eq('admitCardGenerated', true)

            }
            and {
                eq('rollNo', params.rollNumber.trim())
            }

        }

        return stuList

    }

    def getStudentByStudyCenter(user) {

        def obj = Student.createCriteria()
        def stuList = obj.list {
            studyCentre {
                eq('id', Long.parseLong(user.studyCentreId.toString()))
            }
            and {
                eq('admitCardGenerated', true)

            }

        }

        return stuList

    }

    def updateStudentRecord(stuList, examVenueId) {
        def examVenueObj = ExaminationVenue.findById(Long.parseLong(examVenueId))
        stuList.each {
            it.admitCardGenerated = true
            it.examinationVenue = examVenueObj
            it.save(failOnError: true)
        }
        return true

    }
}
