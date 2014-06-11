package examinationproject

class Branch {

    String branchLocation

    static belongsTo = Bank

    static constraints = {
        branchLocation(nullable: false)
    }


    static mapping = {
        id column: 'branchId'
        branchLocation column: 'branchLocation'
    }
}
