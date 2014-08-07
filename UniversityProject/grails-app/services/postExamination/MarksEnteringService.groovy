package postExamination

import com.university.Role
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramGroup
import examinationproject.ProgramGroupDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import grails.transaction.Transactional
import postexamination.MarksType
import postexamination.StudentMarks

import javax.activation.MimetypesFileTypeMap

@Transactional
class MarksEnteringService {

    def serviceMethod() {

    }

    def getGroupDetail(params) {
        println("***"+params)
        def programSessionObj = ProgramSession.findByProgramDetailId(ProgramDetail.get(params.program))
        def programGroupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionObj, Semester.get(params.semester))
        return programGroupIns

    }

    def getCourseDetail(params) {
        def subjectList
        def programDetail = ProgramDetail.findById(params.program)
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))

        if (params.groupType == "0") {
            subjectList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programDetail, semester, programSession).subjectSessionId.subjectId
        } else {
            def programGroupIns = ProgramGroup.findByProgramSessionAndSemester(programSession, semester)
            subjectList = ProgramGroupDetail.findAllByProgramGroupId(programGroupIns).subjectSessionId.subjectId

        }
        return subjectList

    }


    def saveMarks(params) {
        try {
            def studentMarksIns = new StudentMarks()
            studentMarksIns.subjectId = Subject.get(Integer.parseInt(params.subjectId))
            studentMarksIns.semesterNo = Integer.parseInt(params.semester)
            studentMarksIns.marksObtained = Integer.parseInt(params.marksValue)
            studentMarksIns.student = Student.get(Integer.parseInt(params.rollNoId))
            studentMarksIns.marksTypeId = MarksType.get(Integer.parseInt(params.marksType))
            studentMarksIns.roleId = Role.get(8)
            studentMarksIns.save(flush: true, failOnError: true)
        }
        catch (Exception e) {
            println("Exception in saving marks" + e)
        }

    }

    def getRollNumbers(params) {
        def finalList = []
        def stuObject = StudentMarks.createCriteria()
        def stuList1 = stuObject.list {
            eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
            eq('semesterNo', Integer.parseInt(params.semester))
            eq('roleId', Role.get(8))
            eq('marksTypeId', MarksType.get(Integer.parseInt(params.marksType)))
            order('student', 'asc')
        }.student
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        def studentObj = Student.createCriteria()
        def stuList = studentObj.list {
            programDetail {
                eq('id', Long.parseLong(params.program))
            }
            and {
                eq('semester', semester.semesterNo)
                eq('programSession', programSession)
                eq("admitCardGenerated", true)
                eq('status', Status.get(4))
//            eq('registrationYear', Integer.parseInt(params.stuSession))
//            eq('ProgramDetail', ProgramDetail.findById(Long.parseLong(params.program)))
            }
        }

        for (def i = 0; i < stuList.size(); i++) {

            for (def j = i; j < stuList1.size(); j++) {

                if (stuList1[j].id == stuList[i].id) {

                    break;
                } else {
                    finalList << stuList[i]
                }
            }

            if (i > stuList1.size() - 1) {

                finalList << stuList[i]
            }

        }

        return finalList

    }


}
