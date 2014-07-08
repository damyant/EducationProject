package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class CourseSubject implements Serializable  {
    ProgramDetail courseDetail
    SubjectSession subjectSessionId
    Semester semester
    ProgramSession programSession
    Date examDate
    String examTime



    boolean equals(other) {
        if (!(other instanceof CourseSubject)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.subjectSessionId?.id == subjectSessionId?.id
        other.semester?.id==semester?.id
        other.programSession?.id==programSession?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (subjectSessionId) builder.append(subjectSessionId.id)
        if (semester) builder.append(semester.id)
        if(programSession) builder.append(programSession.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long subjectSessionId, long semesterId,long programSession) {
        find 'from CourseSubject where courseDetail.id=:courseId and subjectSessionId.id=:subjectSessionId and semester.id=:semesterId and programSession.id=:programSession',
                [courseId: courseId, subjectSessionId: subjectSessionId,semesterId:semesterId,programSession:programSession]
    }

    static CourseSubject create(ProgramDetail courseDetail,   SubjectSession subjectSessionId, Semester semester,ProgramSession programSession,boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subjectSessionId: subjectSessionId,semester:semester,programSession:programSession).save(failOnError: true)
    }

    static CourseSubject saveDate(ProgramDetail courseDetail,   SubjectSession subjectSessionId, Semester semester,ProgramSession programSession, Date examDate,String examTime,boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subjectSessionId: subjectSessionId,semester:semester,programSession:programSession,examDate:examDate,examTime:examTime).save()
    }

    static boolean remove(ProgramDetail courseDetail,SubjectSession subjectSessionId, Semester semester,ProgramSession programSession, boolean flush = false) {
        CourseSubject instance = CourseSubject.findByCourseDetailAndSubjectSessionIdAndSemesterAndProgramSession(courseDetail, subjectSessionId,semester,programSession)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramDetail courseDetail,ProgramSession programSession) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail and  programSession=:programSession', [courseDetail: courseDetail,programSession:programSession]
    }

    static void removeAll(  SubjectSession subjectSessionId) {
        executeUpdate 'DELETE FROM CourseSubject WHERE subjectSessionId=:subjectSessionId', [subjectSessionId: subjectSessionId]
    }

    static void removeAll(Semester semester) {
        executeUpdate 'DELETE FROM CourseSubject WHERE semester=:semester', [semester: semester]
    }

    static void removeAll(ProgramSession programSession) {
        executeUpdate 'DELETE FROM CourseSubject WHERE programSession=:programSession', [programSession: programSession]
    }
    static mapping = {
        id composite: ['courseDetail', 'subjectSessionId','semester','programSession']
        version false
    }



static constraints = {
    examDate (nullable: true)
    examTime (nullable: true)
    }


}





