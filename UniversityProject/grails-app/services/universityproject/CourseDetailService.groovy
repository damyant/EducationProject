package universityproject

import examinationproject.CourseMode
import examinationproject.CourseType
import examinationproject.ProgramDetail
import examinationproject.CourseSubject
import examinationproject.ProgramGroup
import examinationproject.ProgramGroupDetail
import examinationproject.ProgramType

import examinationproject.ProgramSession

import examinationproject.Semester
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import postexamination.MarksType
import postexamination.SubjectMarksDetail

import java.io.File;

@Transactional
class CourseDetailService {

    def serviceMethod() {

    }

    def saveCourseInfo(params) {
        def status = ""
        def updateStatus=""
        def existingCourseObj
        println(params)
        if (params.courseId) {
            existingCourseObj = ProgramDetail.findById(Integer.parseInt(params.courseId))
            updateStatus="update"

      }

        def session = ProgramSession.count()
        def sessionObj

        if (existingCourseObj) {
            existingCourseObj.courseName = params.courseName
            existingCourseObj.courseCode = params.courseCode
            existingCourseObj.courseMode = CourseMode.findById(params.courseMode)
            existingCourseObj.courseType = CourseType.findById(params.courseType)
            existingCourseObj.noOfTerms = Integer.parseInt(params.noOfTerms)
            existingCourseObj.noOfAcademicYears = Integer.parseInt(params.noOfAcademicYears)
            existingCourseObj.noOfPapers = Integer.parseInt(params.noOfPapers)
            existingCourseObj.totalMarks = Integer.parseInt(params.totalMarks)
//            existingCourseObj.marksPerPaper = Integer.parseInt(params.marksPerPaper)
            existingCourseObj.totalCreditPoints = Integer.parseInt(params.totalCreditPoints)
            existingCourseObj.save(failOnError: true, flush: true)

            if (session > 0) {
                if (ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)) {
                    sessionObj = ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
                }
            } else {

                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
            }
            def semesterList = Semester.findAllByProgramSession(sessionObj)

            if (sessionObj) {

            }
            CourseSubject.removeAll(existingCourseObj, sessionObj)
            def programGroupIns= ProgramGroup.findAllByProgramSession(sessionObj)
            semesterList.each {
                it.delete(failOnError: true)
//                it.removeFromProgramGroup(programGroupIns)
            }

        programGroupIns.each {
            ProgramGroupDetail.removeAll(it)
            it.delete()
        }




            status=  saveAndUpdateCourseInformation(params,sessionObj,existingCourseObj)

//            for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
//                semObj = new Semester()
//                semObj.semesterNo = i
//                semObj.programSession = sessionObj
//                semObj.save(failOnError: true)
//
//                params.semesterList.each {
//                    i
//                    def subjectList = it."semester${i}".sort()
//                    subjectList.each { obj ->
//                        CourseSubject.create existingCourseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj, sessionObj
//                    }
//                    status = 'updated'
//                }
//
//            }
            if(status){
                status=updateStatus
            }
            println("************"+status)
            return status
        }
        else {

            def courseObj = new ProgramDetail(params)
            courseObj.save(failOnError: true, flush: true)
            if (session > 0) {

                if (ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)) {
                    sessionObj = ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)

                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
                }
            } else {
                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
            }

            status=  saveAndUpdateCourseInformation(params,sessionObj,courseObj)
            println("*******4343*****"+status)
            return status
        }


    }

    def getAllCourses() {

        def courseObj = ProgramDetail.list(sort: 'courseName')


    }

    def getFullDetailOfCourse(params) {
        def courseDetail = [:], subMap = [:]
        def subList = []

        def programSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))

        courseDetail.course = programSession.programDetailId
        courseDetail.courseType = programSession.programDetailId.courseType.courseTypeName
        courseDetail.ProgramType = programSession.programDetailId.programType.type
        courseDetail.courseMode = programSession.programDetailId.courseMode.modeName
        courseDetail.sessionOfCourse = programSession.sessionOfProgram

        programSession.semester.sort().each {
            def totalList=[],courseList=[]

            courseList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programSession.programDetailId, it, programSession).subjectSessionId
            courseList.each{
                def returnMap=[:]
                returnMap["id"]=it.id
                returnMap["subjectName"]=it.subjectId.subjectName
                totalList<<returnMap;
            }


            def groupIns=  ProgramGroup.findAllByProgramSessionAndSemester(programSession,it)
            if(groupIns){

                def groupList=[],groupSelectionType=[],groupNoOfSubjects=[]
                groupIns.each {
                groupList<<it.groupName
                if(groupSelectionType.size()<1){
                    groupSelectionType<<it.groupSelectionType
                    groupNoOfSubjects<<it.numberOfSubjectsToSelect
                }
                def groupSubjectIns= ProgramGroupDetail.findAllByProgramGroupId(it).subjectSessionId
                    groupSubjectIns.each{
                        def returnMap=[:]
                        returnMap["id"]=it.id
                        returnMap["subjectName"]=it.subjectId.subjectName
                        groupList<<returnMap
                    }
               }
                totalList<<groupList
                totalList<<groupSelectionType
                totalList<<groupNoOfSubjects
            }

            subMap[it.semesterNo]=totalList
            subList = subMap
        }
//        courseDetail.groupSelectionType=groupSelectionType
//        courseDetail.groupNoOfSubjects=groupNoOfSubjects
        courseDetail.semesterList = subList

        println("=="+courseDetail)

        return courseDetail

    }

    def deleteCourse(params) {
        def status = false

        def existingProgramSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))
        if (existingProgramSession) {
            def semesterList = Semester.findAllByProgramSession(existingProgramSession)
            CourseSubject.removeAll(existingProgramSession.programDetailId, existingProgramSession)
            semesterList.each {
                it.delete(failOnError: true, flush: true)
            }
            existingProgramSession.delete(flush: true)
            def courseSubject = ProgramSession.findByProgramDetailId(existingProgramSession.programDetailId)

            if (courseSubject == null) {
                if (ProgramDetail.findById(existingProgramSession.programDetailId.id).delete(failOnError: true, flush: true)) {
                    status = true
                }
            }
        }
        return status

    }

    def saveCourseDetail(params) {
        def subjectIns
        def isSaved =""

        try {
            if (params.subjectId) {
                subjectIns = Subject.get(params.subjectId)
                subjectIns.subjectCode = params.subjectCode
                subjectIns.subjectName = params.subjectName
                subjectIns.aliasCode = params.aliasCode
                subjectIns.creditPoints = Integer.parseInt(params.creditPoints)
                subjectIns.save(flush: true)
                isSaved ="update"
            } else {
                subjectIns = new Subject(params)
                subjectIns.save(failOnError: true, flush: true)
                isSaved ="create"
            }
            def session = SubjectSession.count()
            def sessionObj
            if (session > 0) {
                if (SubjectSession.findBySubjectIdAndSessionOfSubject(subjectIns, params.session)) {
                    sessionObj = SubjectSession.findBySubjectIdAndSessionOfSubject(subjectIns, params.session)
                } else {
                    sessionObj = new SubjectSession(sessionOfSubject: params.session, subjectId: subjectIns).save(flush: true, failOnError: true)
                }
            } else {

                sessionObj = new SubjectSession(sessionOfSubject: params.session, subjectId: subjectIns).save(flush: true, failOnError: true)
            }

            println("***"+sessionObj.subjectMarksDetail)

            if(sessionObj.subjectMarksDetail) {
                  sessionObj.subjectMarksDetail.toList().each {
                      sessionObj.removeFromSubjectMarksDetail(it)
                    it.delete()
                }
            }

            def marksTypeList = MarksType.list()
            def i = 0

            marksTypeList.each {
                if (params.totalMarks[i]) {
                    def subjectMarksDetailIns = new SubjectMarksDetail()
                    subjectMarksDetailIns.marks = Integer.parseInt(params.totalMarks[i].toString())
                    subjectMarksDetailIns.minPassingMarks = Integer.parseInt(params.minPassingMarks[i].toString())
                    subjectMarksDetailIns.marksTypeId = it
                    subjectMarksDetailIns.subjectSession = sessionObj
                    subjectMarksDetailIns.save(failOnError: true)
                }
                ++i
            }


        }

        catch (Exception e) {
            println(" There is some problem in saving Course=" + e)
            isSaved= false
        }

        return isSaved
    }

    def getCourseOnProgramCode(params){
        def resultList=[],courseNameList=[],returnList=[],courseCodeList=[]
        def counter=0

        def subList=Subject.createCriteria()
        def finalSubjectList= subList.list {
            like("subjectCode",params.courseCode+"%")
           and {
               eq('programTypeId',ProgramType.get(params.programType))
           }
         }
//        println("++++++++++"+finalSubjectList)
        finalSubjectList.each{
            resultList<< SubjectSession.findBySubjectId(it)
//            println("++++++++++"+SubjectSession.findBySubjectId(it))
            courseNameList<<SubjectSession.findBySubjectId(it).subjectId.subjectName
            courseCodeList<<SubjectSession.findBySubjectId(it).subjectId.subjectCode

        }

        resultList.each{
           def returnMap=[:]
            returnMap["id"]=it.id
            returnMap["subjectCode"]=courseCodeList[counter]
            returnMap["subjectName"]=courseNameList[counter]
            ++counter;
            returnList<<returnMap
        }

        return returnList


    }

    def saveAndUpdateCourseInformation(params,sessionObj,courseObj){
        def semObj,status
        def indexVal=0;
        for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
            semObj = new Semester()

            semObj.semesterNo = i
            semObj.programSession = sessionObj
            semObj.save(failOnError: true)

            params.semesterList.each {
                i
                def subjectList = it."semester${i}".sort()

                for(def j=0;j<subjectList.size();j++){
//                        println("*********"+subjectList[j])
                    def groupIns
                    for(def k=0;k<subjectList[j].size();k++){

                        if(subjectList[j][k].toString().contains("Group")){
                            groupIns= new ProgramGroup()
                            groupIns.groupName=subjectList[j][k].toString()
                            groupIns.programSession=sessionObj
                            groupIns.semester= semObj
                            groupIns.groupSelectionType=params.groupSelectionType[indexVal]
                            if(params.noOfSubjects[indexVal]){
                                groupIns.numberOfSubjectsToSelect=Integer.parseInt(params.noOfSubjects[indexVal])
                            }
                            groupIns.save(failOnError: true,flush: true)
                        }
                        else{
                            if(groupIns){
                                ProgramGroupDetail.create groupIns, SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString()))
                            }
                            else{

                                CourseSubject.create courseObj, SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString())), semObj, sessionObj

                            }

                        }
                    }
                }

                status = 'Created'
            }
            ++indexVal;
        }

        return status

    }

}

