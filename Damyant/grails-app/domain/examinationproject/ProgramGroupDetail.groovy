package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder


class ProgramGroupDetail implements Serializable  {
    ProgramGroup programGroupId
    SubjectSession subjectSessionId
    Date examDate
    String examTime



    boolean equals(other) {
        if (!(other instanceof ProgramGroupDetail)) {
            return false
        }

        other.programGroupId?.id == programGroupId?.id &&
        other.subjectSessionId?.id == subjectSessionId?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (programGroupId) builder.append(programGroupId.id)
        if (subjectSessionId) builder.append(subjectSessionId.id)
        builder.toHashCode()
    }


    static ProgramGroupDetail get(long programGroupId, long subjectSessionId) {
        find 'from ProgramGroupDetail where programGroupId.id=:programGroupId and subjectSessionId.id=:subjectSessionId',
                [programGroupId: programGroupId, subjectSessionId: subjectSessionId]
    }

    static ProgramGroupDetail create(ProgramGroup programGroupId, SubjectSession subjectSessionId,boolean flush = false) {

        new ProgramGroupDetail(programGroupId: programGroupId, subjectSessionId: subjectSessionId).save(failOnError: true)
    }

    static ProgramGroupDetail saveDate(ProgramGroup programGroupId,SubjectSession subjectSessionId, Date examDate,String examTime,boolean flush = false) {

        new ProgramGroupDetail(programGroupId: programGroupId, subjectSessionId: subjectSessionId,examDate:examDate,examTime:examTime).save()
    }

    static boolean remove(ProgramGroup programGroupId, SubjectSession subjectSessionId, boolean flush = false) {
        ProgramGroupDetail instance = ProgramGroupDetail.findByProgramGroupIdAndSubjectSessionId(programGroupId, subjectSessionId)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramGroup programGroupId,SubjectSession subjectSessionId) {
        executeUpdate 'DELETE FROM ProgramGroupDetail WHERE programGroupId=:programGroupId and  subjectSessionId=:subjectSessionId', [programGroupId: programGroupId,subjectSessionId:subjectSessionId]
    }

    static void removeAll(SubjectSession subjectSessionId) {
        executeUpdate 'DELETE FROM ProgramGroupDetail WHERE subjectSessionId=:subjectSessionId', [subjectSessionId: subjectSessionId]
    }

    static void removeAll(ProgramGroup programGroupId) {
        executeUpdate 'DELETE FROM ProgramGroupDetail WHERE programGroupId=:programGroupId', [programGroupId: programGroupId]
    }

    static mapping = {
        id composite: ['programGroupId', 'subjectSessionId']
        version false
    }



    static constraints = {
        examDate (nullable: true)
        examTime (nullable: true)
    }


}





