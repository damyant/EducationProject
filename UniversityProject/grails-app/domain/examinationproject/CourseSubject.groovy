package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class CourseSubject implements Serializable  {
    ProgramDetail courseDetail
    Subject subject
    Semester semester
    ProgramSession programSession
    Date examDate
    String examTime



    boolean equals(other) {
        if (!(other instanceof CourseSubject)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.subject?.id == subject?.id
        other.semester?.id==semester?.id
        other.programSession?.id==programSession?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (subject) builder.append(subject.id)
        if (semester) builder.append(semester.id)
        if(programSession) builder.append(programSession.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long subjectId, long semesterId,long programSession) {
        find 'from CourseSubject where courseDetail.id=:courseId and subject.id=:subjectId and semester.id=:semesterId and programSession.id=:programSession',
                [courseId: courseId, subjectId: subjectId,semesterId:semesterId,programSession:programSession]
    }

    static CourseSubject create(ProgramDetail courseDetail, Subject subject, Semester semester,ProgramSession programSession,boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subject: subject,semester:semester,programSession:programSession).save(failOnError: true)
    }

    static CourseSubject saveDate(ProgramDetail courseDetail, Subject subject, Semester semester,ProgramSession programSession, Date examDate,String examTime,boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subject: subject,semester:semester,programSession:programSession,examDate:examDate,examTime:examTime).save()
    }

    static boolean remove(ProgramDetail courseDetail, Subject subject, Semester semester,ProgramSession programSession, boolean flush = false) {
        CourseSubject instance = CourseSubject.findByCourseDetailAndSubjectAndSemesterAndProgramSession(courseDetail, subject,semester,programSession)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramDetail courseDetail,ProgramSession programSession) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail and  programSession=:programSession', [courseDetail: courseDetail,programSession:programSession]
    }

    static void removeAll(Subject subject) {
        executeUpdate 'DELETE FROM CourseSubject WHERE subject=:subject', [subject: subject]
    }

    static void removeAll(Semester semester) {
        executeUpdate 'DELETE FROM CourseSubject WHERE semester=:semester', [semester: semester]
    }

    static void removeAll(ProgramSession programSession) {
        executeUpdate 'DELETE FROM CourseSubject WHERE programSession=:programSession', [programSession: programSession]
    }
    static mapping = {
        id composite: ['courseDetail', 'subject','semester','programSession']
        version false
    }



static constraints = {
    examDate (nullable: true)
    examTime (nullable: true)
    }


}





