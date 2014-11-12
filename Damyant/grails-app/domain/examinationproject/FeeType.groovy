package examinationproject

class FeeType {

    String type
    Boolean showValue

    static constraints = {
        showValue(nullable: true)
    }
}
