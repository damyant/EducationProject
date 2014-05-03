package universityproject

import examinationproject.ProgramFee
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
    def saveProgramFeeType(ProgramFee programFeeInstance) {
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

}
