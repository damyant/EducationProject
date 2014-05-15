package universityproject


import examinationproject.AdmissionFee
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.ProgramDetail

import examinationproject.ProgramSession
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

    def saveProgramFeeType(params) {
        def feeTypeList=params.feeTypeList.split(',')
        def admissionFeeIns
        if(params.admissionFee)
           admissionFeeIns=AdmissionFee.findById(Integer.parseInt(params.admissionFee))
        else
           admissionFeeIns=new AdmissionFee()
        Set<ProgramDetail> programDetail = ProgramDetail.findAllById(params.programDetail)
        admissionFeeIns.feeAmountAtIDOL=Integer.parseInt(params.feeAmountAtIDOL)
        admissionFeeIns.feeAmountAtSC=Integer.parseInt(params.feeAmountAtSC)
        admissionFeeIns.programDetail = programDetail[0]
        admissionFeeIns.lateFeeAmount = Integer.parseInt(params.lateFeeAmount)

        def session = ProgramSession.count()
        def programSessionIns
        if (session > 0) {
            if (programDetail[0].programSession.id) {
                programSessionIns = ProgramSession.findById(programDetail[0].programSession.id)
            } else {
                programSessionIns = new ProgramSession(sessionOfProgram: params.programSession, programDetail: programDetail).save(flush: true, failOnError: true)
            }
        } else {
            programSessionIns = new ProgramSession(sessionOfProgram: params.programSession, programDetail: programDetail).save(flush: true, failOnError: true)
//            println("Session new" + programSessionIns.sessionOfProgram)
        }
        admissionFeeIns.programSession= programSessionIns
        admissionFeeIns.save(failOnError: true)
//        def misFeeIns=new MiscellaneousFee()


        def i=0;
        feeTypeList.each{
            def misFeeIns
            if(it)
                misFeeIns=MiscellaneousFee.findById(it)
            else
                misFeeIns=new MiscellaneousFee()
            misFeeIns.programDetail=ProgramDetail.findById(params.programDetail)
            misFeeIns.feeType=FeeType.findById(Long.parseLong(it.toString()))
            misFeeIns.amount=Integer.parseInt(params.feeTypeAmount[i])
            misFeeIns.programSession=programSessionIns
            ++i;
            misFeeIns.save(failOnError: true)
//            misFeeIns.programSession
        }
    }

/**
 * Service to delete a particular fee type
 * @param programFeeInstance
 */
    boolean deleteFeeType(params) {
        boolean isDeleted = false
        def programFeeInstance = AdmissionFee.findById(Integer.parseInt(params.id))
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
