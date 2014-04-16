package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class ProgramExamVenue implements Serializable  {
    ProgramDetail courseDetail
    ExaminationCentre examCenter


    boolean equals(other) {
        if (!(other instanceof ProgramExamVenue)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.examCenter?.id == examCenter?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (examCenter) builder.append(examCenter.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long examCenterId) {
        find 'from ProgramExamVenue where courseDetail.id=:courseId and examCenter.id=:examCenterId',
                [courseId: courseId, examCenterId: examCenterId]
    }

    static ProgramExamVenue create(ProgramDetail courseDetail, ExaminationCentre examCenter,boolean flush = false) {

        new ProgramExamVenue(courseDetail: courseDetail, examCenter: examCenter).save()
    }

    static boolean remove(ProgramDetail courseDetail, ExaminationCentre examCenter, boolean flush = false) {
        ProgramExamVenue instance = ProgramExamVenue.findByCourseDetailAndExamCenter(courseDetail, examCenter)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramDetail courseDetail) {
        executeUpdate 'DELETE FROM ProgramExamVenue WHERE courseDetail=:courseDetail', [courseDetail: courseDetail]
    }

    static void removeAll(ExaminationCentre examCenter) {
        executeUpdate 'DELETE FROM ProgramExamVenue WHERE examCenter=:examCenter', [examCenter: examCenter]
    }


    static mapping = {
        id composite: ['courseDetail', 'examCenter']
        version false
    }



    static constraints = {
    }


}






