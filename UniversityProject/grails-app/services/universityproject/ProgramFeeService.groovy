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
        println("#######@@@@@@@@@@@@>>>>>>>>>"+params)
        def status=false
        def feeTypeList=params.feeTypeList.split(',')
        def admissionFeeIns
        def program = ProgramDetail.findById(params.programDetail)
        if(params.admissionFee) {
            admissionFeeIns = AdmissionFee.findById(Integer.parseInt(params.admissionFee))
            println(">>>>>>>>?>>>>>> " +admissionFeeIns)
        }
        else {
            admissionFeeIns = new AdmissionFee()
        }
        Set<ProgramDetail> programDetail = ProgramDetail.findAllById(params.programDetail)
        println(">>>>>>>>?>>>>>>"+programDetail)
        admissionFeeIns.feeAmountAtIDOL=Integer.parseInt(params.feeAmountAtIDOL)
        admissionFeeIns.feeAmountAtSC=Integer.parseInt(params.feeAmountAtSC)
        admissionFeeIns.programDetail = programDetail[0]
        admissionFeeIns.lateFeeAmount = Integer.parseInt(params.lateFeeAmount)

        def session = ProgramSession.count()
        def programSessionIns
        if (session > 0) {
            println("Session    "+ProgramSession.findBySessionOfProgramAndProgramDetailId(params.programSession,program))
            if (ProgramSession.findBySessionOfProgramAndProgramDetailId(params.programSession,program)) {
                programSessionIns = ProgramSession.findBySessionOfProgramAndProgramDetailId(params.programSession,program)
            } else {
                programSessionIns = new ProgramSession(sessionOfProgram: params.programSession,programDetailId:program).save(flush: true, failOnError: true)
            }
        } else {
            programSessionIns = new ProgramSession(sessionOfProgram: params.programSession,programDetailId:program ).save(flush: true, failOnError: true)
        }
        admissionFeeIns.programSession= programSessionIns

        if(admissionFeeIns.save(failOnError: true)){
            status = true
        }

        def i=0;
        try{
        feeTypeList.each{
            def misFeeIns  =MiscellaneousFee.findByFeeTypeAndProgramDetail(FeeType.findById(it),program)
            if(!misFeeIns){
                println("else")
                misFeeIns=new MiscellaneousFee()
            }
            misFeeIns.programDetail=ProgramDetail.findById(params.programDetail)
            misFeeIns.feeType=FeeType.findById(Long.parseLong(it.toString()))
            misFeeIns.amount=Integer.parseInt(params.feeTypeAmount[i])
            misFeeIns.programSession=programSessionIns
            ++i;
           if(misFeeIns.save(failOnError: true)){
               status = true
           }

//            misFeeIns.programSession
        }
        }catch(Exception e){
            println("??????????????"+e)
        }
        return status
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
//            Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.program))
          //  endYear = (Integer.parseInt(year) + programDetail[0].noOfAcademicYears).toString()
            endYear = Integer.parseInt(year)+1
            currentSession = (startYear + "-" + endYear)
            nextSession    = endYear+ "-" + ++endYear
            programSessions.add(new ProgramSession(sessionOfProgram: currentSession))
            programSessions.add(new ProgramSession(sessionOfProgram: nextSession))
//
            println("ProgramSession"+programSessions)
            }catch(Exception ex){
                println("..........Problem in creating program session for programs"+ex)
         }
            return programSessions
    }

}
