package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class CourseSubject implements Serializable  {
    ProgramDetail courseDetail
    Subject subject
    Semester semester


    boolean equals(other) {
        if (!(other instanceof CourseSubject)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.subject?.id == subject?.id
        other.semester?.id==semester?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (subject) builder.append(subject.id)
        if (semester) builder.append(semester.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long subjectId, long semesterId) {
        find 'from CourseSubject where courseDetail.id=:courseId and subject.id=:subjectId and semester.id=:semesterId',
                [courseId: courseId, subjectId: subjectId,semesterId:semesterId]
    }

    static CourseSubject create(ProgramDetail courseDetail, Subject subject, Semester semester,boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subject: subject,semester:semester).save()
    }

    static boolean remove(ProgramDetail courseDetail, Subject subject, Semester semester, boolean flush = false) {
        CourseSubject instance = CourseSubject.findByCourseDetailAndSubjectAndSemester(courseDetail, subject,semester)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramDetail courseDetail) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail', [courseDetail: courseDetail]
    }

    static void removeAll(Subject subject) {
        executeUpdate 'DELETE FROM CourseSubject WHERE subject=:subject', [subject: subject]
    }

    static void removeAll(Semester semester) {
        executeUpdate 'DELETE FROM CourseSubject WHERE semester=:semester', [semester: semester]
    }

    static mapping = {
        id composite: ['courseDetail', 'subject','semester']
        version false
    }



static constraints = {
    }


}





