package examinationproject

//ADDED BY RAJ
class Bank {
    String bankName
    static hasMany = [branch : Branch]
    static constraints = {
        bankName(nullable: false)
    }
}
