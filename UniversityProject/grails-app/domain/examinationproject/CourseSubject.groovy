package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class CourseSubject implements Serializable  {
    CourseDetail courseDetail
    Subject subject


    boolean equals(other) {
        if (!(other instanceof CourseSubject)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.subject?.id == subject?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (subject) builder.append(subject.id)
        builder.toHashCode()
    }


    static CourseSubject get(long userId, long roleId) {
        find 'from CourseSubject where courseDetail.id=:userId and subject.id=:roleId',
                [userId: userId, roleId: roleId]
    }

    static CourseSubject create(CourseDetail courseDetail, Subject subject, boolean flush = false) {

        new CourseSubject(courseDetail: courseDetail, subject: subject).save()
    }

    static boolean remove(CourseDetail courseDetail, Subject subject, boolean flush = false) {
        CourseSubject instance = CourseSubject.findByCourseDetailAndSubject(courseDetail, subject)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(CourseDetail courseDetail) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail', [courseDetail: courseDetail]
    }

    static void removeAll(Subject subject) {
        executeUpdate 'DELETE FROM CourseSubject WHERE subject=:subject', [subject: subject]
    }

    static mapping = {
        id composite: ['courseDetail', 'subject']
        version false
    }



static constraints = {
    }


}





