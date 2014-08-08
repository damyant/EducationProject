package postExamination

import com.university.Role
import com.university.TabulatorProgram
import com.university.TabulatorSemester
import com.university.UserRole
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
    def springSecurityService

    def serviceMethod() {

    }

    def getGroupDetail(params) {
        println("***"+params)
        def programSessionObj = ProgramSession.findByProgramDetailId(ProgramDetail.get(params.program))
        def programGroupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionObj, Semester.get(params.semester))
        return programGroupIns

    }

    def getCourseDetail(params) {
        println(" =="+params)
        def subjectList
        def programDetail = ProgramDetail.findById(params.program)
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        println(programDetail)
        println(programSession)
        println(semester)
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
            def programSession = ProgramSession.get(Integer.parseInt(params.session))
            def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
            def currentUser = springSecurityService.currentUser
            def role=UserRole.findAllByUser(currentUser).role
            def role_id
            role.each {
                def tabSemIns=TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it))
                if(tabSemIns){
                    role_id = TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it)).tabulatorProgram.role.id
                }
            }

            def studentMarksIns = new StudentMarks()
            studentMarksIns.subjectId = Subject.get(Integer.parseInt(params.subjectId))
            studentMarksIns.semesterNo = Integer.parseInt(params.semester)
            studentMarksIns.marksObtained = Integer.parseInt(params.marksValue)
            studentMarksIns.student=Student.findByRollNo(params.rollNoId)
            studentMarksIns.marksTypeId = MarksType.get(Integer.parseInt(params.marksType))
            studentMarksIns.roleId = Role.get(role_id)
            studentMarksIns.save(flush: true, failOnError: true)
        }
        catch (Exception e) {
            println("Exception in saving marks" + e)
        }

    }

    def getRollNumbers(params) {
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        def currentUser = springSecurityService.currentUser
        def role=UserRole.findAllByUser(currentUser).role
        def role_id
        role.each {
            def tabSemIns=TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it))
            if(tabSemIns){
                role_id = TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it)).tabulatorProgram.role.id
            }
        }

        def stuObject = StudentMarks.createCriteria()
        def stuList1 = stuObject.list {
            eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
            eq('semesterNo', Integer.parseInt(params.semester))
            eq('roleId', Role.get(role_id))
            eq('marksTypeId', MarksType.get(Integer.parseInt(params.marksType)))
            order('student', 'asc')
        }.student.rollNo

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
            }
        }.rollNo

        stuList1.each {
            if(it){
            def rollNoIndex=stuList.findIndexOf{ it in stuList1 }
            stuList.remove(rollNoIndex)
            }
        }

        return stuList

    }


}
