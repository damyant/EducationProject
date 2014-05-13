package universityproject

import examinationproject.AdmissionFee
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.ProgramDetail
import examinationproject.ProgramFee
import examinationproject.ProgramSession
import grails.transaction.Transactional

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
        def admissionFeeIns=new AdmissionFee(params)
        admissionFeeIns.save(failOnError: true)
//        def misFeeIns=new MiscellaneousFee()
        def i=0;
        feeTypeList.each{

            def misFeeIns=new MiscellaneousFee()
            misFeeIns.programDetail=ProgramDetail.findById(Long.parseLong(params.programDetail))
            misFeeIns.feeType=FeeType.findById(Long.parseLong(it.toString()))
            misFeeIns.amount=Integer.parseInt(params.feeTypeAmount[i])
            ++i;
            misFeeIns.save(failOnError: true)
//            misFeeIns.programSession
        }
//        programFeeInstance.save(flush: true)
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

}
