package universityproject


import examinationproject.AdmissionFee
import examinationproject.FeeSession
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.ProgramDetail

import examinationproject.ProgramSession
import examinationproject.ProgramType
import examinationproject.Semester
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
//        println(" paramters are ==="+params)
        def status=false
        def newSessionStatus=false
        def feeTypeList=params.feeTypeList.split(',')
        def admissionFeeIns,sessionObj
//        def program = ProgramDetail.findById(params.programDetail)
//        println(params.programId)
        def programDetailIns = ProgramDetail.findById(Integer.parseInt(params.programDetailId))
        def session = FeeSession.count()
//        println("<<<<<<<<<<<<<<<<<"+params.programSessionId)
        if (session > 0) {
            if (FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)) {
//                println("me in iffff")
                sessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)
            } else {
//                println("**********"+programDetailIns)
                sessionObj = new FeeSession(sessionOfFee: params.programSessionId, programDetailId:programDetailIns).save(flush: true, failOnError: true)
                newSessionStatus=true
            }
        } else {
//            println("me in last els")
            sessionObj = new FeeSession(sessionOfFee: params.programSessionId, programDetailId:programDetailIns).save(flush: true, failOnError: true)
            newSessionStatus=true
        }

//        println("???"+params.admissionFee)
//        if(params.admissionFee && newSessionStatus==false) {
////            println(">>>>>>>>?>>>>>> " +admissionFeeIns)
//            admissionFeeIns = AdmissionFee.findById(Integer.parseInt(params.admissionFee))
//
//        }
//        else if(newSessionStatus==true) {
////            println("elssssssssssssss")
//            admissionFeeIns = new AdmissionFee()
//        }
//
////        println(">>>>>>>>?>>>>>>"+programDetailIns)
////        println("adm fee ins=="+admissionFeeIns)
//        admissionFeeIns.feeAmountAtIDOL=Integer.parseInt(params.feeAmountAtIDOL)
//        admissionFeeIns.feeAmountAtSC=Integer.parseInt(params.feeAmountAtSC)
////        admissionFeeIns.programDetail = programDetail[0]
//        admissionFeeIns.lateFeeAmount = Integer.parseInt(params.lateFeeAmount)
//
//       admissionFeeIns.feeSession= sessionObj
//
//        if(admissionFeeIns.save(failOnError: true)){
//            status = true
//        }

        def i=0;
        try{
//            println("******"+sessionObj)
        feeTypeList.each{
            def misFeeIns  =MiscellaneousFee.findByFeeTypeAndFeeSession(FeeType.findById(it),sessionObj)
            if(!misFeeIns){
//                println("else")
                misFeeIns=new MiscellaneousFee()
            }
//            misFeeIns.programDetail=ProgramDetail.findById(params.programDetail)
            misFeeIns.feeType=FeeType.findById(Long.parseLong(it.toString()))
            misFeeIns.amount=Integer.parseInt(params.feeTypeAmount[i])
            misFeeIns.feeSession=sessionObj
            ++i;
           if(misFeeIns.save(failOnError: true)){
               status = true
           }

//            misFeeIns.programSession
        }
        }catch(Exception e){
//            println("??????????????"+e)
        }
        return status
    }

    def saveAdmissionFeeType(params) {
        def status=false
        def newSessionStatus=false
        def admissionFeeIns,sessionObj
        def programDetailIns = ProgramDetail.findById(Integer.parseInt(params.programDetailId))
        def session = FeeSession.count()
//        println("<<<<<<<<<<<<<<<<<"+params.programSessionId)
//        println("session<<<<<<<<<<<<<<<<<"+session)

        if (session > 0) {
            if (FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)) {
                sessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)
            }
        } else {
            sessionObj = new FeeSession(sessionOfFee: params.programSessionId, programDetailId:programDetailIns).save(flush: true, failOnError: true)
            newSessionStatus=true
        }
//        println("______________"+params.term)
        if(newSessionStatus==false) {
            if(AdmissionFee.findByFeeSessionAndTerm(sessionObj,Integer.parseInt(params.semesterList))){
                admissionFeeIns = AdmissionFee.findByFeeSessionAndTerm(sessionObj,Integer.parseInt(params.semesterList))
            }
            else{
                admissionFeeIns = new AdmissionFee()
            }
        }
        else if(newSessionStatus==true) {
            admissionFeeIns = new AdmissionFee()
        }
        if(params.semesterList!='0'){
            admissionFeeIns.feeAmountAtIDOL=Integer.parseInt(params.feeAmountAtIDOL)
            admissionFeeIns.term=Integer.parseInt(params.semesterList)
            admissionFeeIns.feeAmountAtSC=Integer.parseInt(params.feeAmountAtSC)
            admissionFeeIns.lateFeeAmount = Integer.parseInt(params.lateFeeAmount)
            admissionFeeIns.feeSession= sessionObj
            if(admissionFeeIns.save(failOnError: true)){
                status = true
            }
        }
        else{
            def termValue
            if(programDetailIns.programType==ProgramType.findById(1)){
                termValue=programDetailIns.noOfAcademicYears
            }
            else{
                termValue=programDetailIns.noOfTerms
            }
            for (int i=0;i<=termValue;i++){
                admissionFeeIns.feeAmountAtIDOL=Integer.parseInt(params.feeAmountAtIDOL)
                admissionFeeIns.term=i
                admissionFeeIns.feeAmountAtSC=Integer.parseInt(params.feeAmountAtSC)
                admissionFeeIns.lateFeeAmount = Integer.parseInt(params.lateFeeAmount)
                admissionFeeIns.feeSession= sessionObj
                if(admissionFeeIns.save(failOnError: true)){
                    status = true
                }
            }
        }
        return status
    }
/**
 * Service to delete a particular fee type
 * @param programFeeInstance
 */
    boolean deleteFeeType(params) {
        boolean isDeleted = false
        def programFeeInstance = FeeSession.findById(Integer.parseInt(params.id))
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
//            println("ProgramSession"+programSessions)
            }catch(Exception ex){
                println("..........Problem in creating program session for programs"+ex)
         }
            return programSessions
    }

}
