package universityproject

import examinationproject.City
import examinationproject.ExaminationVenue
import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.ProgramType
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class AdmitCardService {

    def serviceMethod() {

    }


    def getStudents(params) {

        def obj = Student.createCriteria()
        def studentList = []
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
            order("id", "asc")
        }
        stuList.each {
            def semValue
            if (it.programDetail.programType == ProgramType.findById(1)) {
                semValue = (int) Math.ceil(Long.parseLong(params.programTerm) / 2)
            } else {
                semValue = Integer.parseInt(params.programTerm)
            }

            def stuAdmissionFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(it, semValue, FeeType.findById(3), Status.findById(4))
            if (stuAdmissionFeeInst && (it.programDetail[0].programType.id == ProgramType.findById(2).id)) {
                def stuExamFeeInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(it, semValue, FeeType.findById(1), Status.findById(4))

                if (stuExamFeeInst) {
                    studentList << it
                }
            } else if (stuAdmissionFeeInst && (it.programDetail[0].programType.id == ProgramType.findById(1).id)) {
                studentList << it
            }
        }

        return studentList
    }
    def getBulkStudents(params) {
        def semValue = Integer.parseInt(params.programTerm)
        def feePaidList = FeeDetails.findAllByFeeTypeAndIsApprovedAndSemesterValue(FeeType.findById(1),Status.findById(4),semValue)
        def studentList = [],stuList=[]
        DateFormat df = new SimpleDateFormat("dd/MM/yy")
        def fromDate=df.parse(params.fromDate)
        def toDate=df.parse(params.toDate)
        feePaidList.each {
            def paymentDate=df.parse(df.format(it.paymentDate))
            if((paymentDate.compareTo(fromDate)>=0)&& (paymentDate.compareTo(toDate)<=0)){
                stuList<<it.student
            }
        }
        stuList.each {
            if((it.programSession.id==ProgramSession.findById(Integer.parseInt(params.programSession)).id)&&(it.city[0].id==City.findById(params.examinationCentre).id)&&(it.programDetail[0].id==ProgramDetail.findById(params.programList).id)){
                 studentList << it
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
        println("innnnnnnnnn")

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
