package examinationproject

class Bank {

    String bankName

    static hasMany = [
            branch : Branch,
    ]

    static constraints = {
        bankName(nullable: false)
    }


}
