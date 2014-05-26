package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class StudyMaterial implements Serializable  {
    Student studentId
    Subject subjectId


    boolean equals(other) {
        if (!(other instanceof StudyMaterial)) {
            return false
        }

                other.studentId?.id == studentId?.id &&
                other.subjectId?.id == subjectId?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (studentId) builder.append(studentId.id)
        if (subjectId) builder.append(subjectId.id)
        builder.toHashCode()
    }


    static StudyMaterial get(long studentId, long subjectId) {
        find 'from StudyMaterial where studentId.id=:studentId and subjectId.id=:subjectId',
                [studentId: studentId, subjectId: subjectId]
    }


    static StudyMaterial create(Student studentId, Subject subjectId,boolean flush = false) {

        new StudyMaterial(studentId: studentId, subjectId: subjectId).save(failOnError: true)
    }

    static boolean remove(Student studentId, Subject subjectId, boolean flush = false) {
        StudyMaterial instance = StudyMaterial.findByStudentIdAndStudentId(studentId, subjectId)
//        ProgramExamVenue instance = ProgramExamVenue.findByCourseDetailAndCity(courseDetail, city)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(Student studentId) {
        executeUpdate 'DELETE FROM StudyMaterial WHERE studentId=:studentId', [studentId: studentId]
    }

    static void removeAll(Student studentId, Subject subjectId) {
        executeUpdate 'DELETE FROM StudyMaterial WHERE studentId=:studentId and subjectId=:subjectId', [studentId: studentId,subjectId:subjectId]
    }

    static void removeAll(Subject subjectId) {
        executeUpdate 'DELETE FROM StudyMaterial WHERE subjectId=:subjectId', [subjectId: subjectId]
    }

    static mapping = {
        id composite: ['studentId', 'subjectId']
        version false
    }



    static constraints = {
    }



}
