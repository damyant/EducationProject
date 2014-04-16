package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class ProgramExamVenue implements Serializable  {
    ProgramDetail courseDetail
    ExaminationCentre examCenter
    City city


    boolean equals(other) {
        if (!(other instanceof ProgramExamVenue)) {
            return false
        }

        other.courseDetail?.id == courseDetail?.id &&
        other.examCenter?.id == examCenter?.id &&
        other.city?.id==city?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (examCenter) builder.append(examCenter.id)
        if (city) builder.append(city.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long examCenterId, long city) {
        find 'from ProgramExamVenue where courseDetail.id=:courseId and examCenter.id=:examCenterId and city.id=:city',
                [courseId: courseId, examCenterId: examCenterId, city: city]
    }

    static ProgramExamVenue create(ProgramDetail courseDetail, ExaminationCentre examCenter,City city,boolean flush = false) {

        new ProgramExamVenue(courseDetail: courseDetail, examCenter: examCenter, city:city).save()
    }

    static boolean remove(ProgramDetail courseDetail, ExaminationCentre examCenter,City city, boolean flush = false) {
        ProgramExamVenue instance = ProgramExamVenue.findByCourseDetailAndExamCenterAndCity(courseDetail, examCenter, city)
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

    static void removeAll(City city) {
        executeUpdate 'DELETE FROM ProgramExamVenue WHERE city=:city', [city: city]
    }

    static mapping = {
        id composite: ['courseDetail', 'examCenter', 'city']
        version false
    }



    static constraints = {
    }


}






