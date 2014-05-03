package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class ProgramExamVenue implements Serializable  {
    ProgramDetail courseDetail
    ExaminationVenue examVenue
    ExaminationCentre examCenter


    boolean equals(other) {
        if (!(other instanceof ProgramExamVenue)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
                other.examCenter?.id == examCenter?.id &&
                other.examVenue?.id==examVenue?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (examCenter) builder.append(examCenter.id)
        if (examVenue) builder.append(examVenue.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long examCenterId, long examVenue) {
        find 'from ProgramExamVenue where courseDetail.id=:courseId and examCenter.id=:examCenterId and examVenue.id=:examVenue',
                [courseId: courseId, examCenterId: examCenterId, examVenue: examVenue]
    }

    static ProgramExamVenue create(ProgramDetail courseDetail,ExaminationCentre examCenter,ExaminationVenue examVenue,boolean flush = false) {

        new ProgramExamVenue(courseDetail: courseDetail, examCenter: examCenter, examVenue:examVenue).save()
    }

    static boolean remove(ProgramDetail courseDetail, ExaminationCentre examCenter,ExaminationVenue examVenue, boolean flush = false) {
        ProgramExamVenue instance = ProgramExamVenue.findByCourseDetailAndExamCenterAndExamVenue(courseDetail, examCenter, examVenue)
//        ProgramExamVenue instance = ProgramExamVenue.findByCourseDetailAndCity(courseDetail, city)
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

    static void removeAll(ExaminationVenue examVenue) {
        executeUpdate 'DELETE FROM ProgramExamVenue WHERE examVenue=:examVenue', [examVenue: examVenue]
    }

    static mapping = {
        id composite: ['courseDetail', 'examCenter', 'examVenue']
        version false
    }



    static constraints = {
    }


}