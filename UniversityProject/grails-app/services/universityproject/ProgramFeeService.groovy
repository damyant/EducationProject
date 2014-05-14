package universityproject

import examinationproject.ProgramDetail
import examinationproject.ProgramFee
import examinationproject.ProgramSession
import examinationproject.Student
import grails.converters.JSON
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class ProgramFeeService {

    def serviceMethod() {

    }

/**
 * Service to update fee type
 * @param programFeeInstance
 * @return
 */
    def saveProgramFeeType(ProgramFee programFeeInstance,params) {

        println("in new Fee creation"+params)
        programFeeInstance.save(flush: true)
    }
/**
 * Service to delete a particular fee type
 * @param programFeeInstance
 */
    boolean deleteFeeType(ProgramFee programFeeInstance) {
        boolean isDeleted = false
        if (programFeeInstance.delete(flush: true)) {
            isDeleted = true
        } else {
            isDeleted = false
        }

    }

    def getProgramSessions(params){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        String year = sdf.format(Calendar.getInstance().getTime());
        def startYear = year
        def endYear
        def currentSession
        def nextSession
        def programSessions= []
        try{
            Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.program))
            endYear = (Integer.parseInt(year) + programDetail[0].noOfAcademicYears).toString()
            currentSession = (startYear + "-" + endYear)
            nextSession    =  ((Integer.parseInt(startYear)+1) + "-" + ++Integer.parseInt(endYear))
            programSessions.add(new ProgramSession(programDetail:programDetail,sessionOfProgram: currentSession))
            programSessions.add(new ProgramSession(programDetail:programDetail,sessionOfProgram: nextSession))
//
            println("ProgramSession"+programSessions)
            }catch(Exception ex){
                println("..........Problem in creating program session for programs"+ex)
         }
            return programSessions
    }

}
