package examinationproject

class Branch {

    String branchLocation

    static belongsTo = [bank:Bank]
    static constraints = {
        branchLocation(nullable: false)
    }


    static mapping = {
        id column: 'branchId'
        bank cascade: "none"
        branchLocation column: 'branchLocation'
    }
    def beforeDelete = {
        this.bank.each {
            it.removeFromBranch(this)
        }
    }


}
