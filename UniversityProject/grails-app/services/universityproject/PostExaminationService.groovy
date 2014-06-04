package universityproject

import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Subject
import grails.transaction.Transactional

@Transactional
class PostExaminationService {

    def serviceMethod() {

    }

    def generateMarksFoil(params){
        println("Inside PostExaminationService 1")
        def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
//        def programIns=ProgramDetail.findById(Long.parseLong(params.programList))
        def programSessionIns=ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterList=Semester.findAllByProgramSession(programSessionIns)
        def courseSubjectObj=CourseSubject.findBySubjectAndProgramSession(Subject.findById(Long.parseLong(it)),sessionObj)

    }
}//
