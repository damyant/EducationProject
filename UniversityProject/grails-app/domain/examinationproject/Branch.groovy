package examinationproject

class Branch {

    String branchLocation
    Bank bank

    static constraints = {
        branchLocation(nullable: false)
    }


    static mapping = {
        id column: 'branchId'
        bankId column: 'bankId'
        branchLocation column: 'branchLocation'

    }
}
