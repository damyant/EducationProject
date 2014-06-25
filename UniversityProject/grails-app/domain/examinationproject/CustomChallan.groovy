package examinationproject

class CustomChallan {
    String challanNo
    String typeOfFee
    String challanName
    int amount
    Date challanDate

    static constraints = {
    }
    static mapping = {
        challanNo column: "challanNo"
        typeOfFee column: "typeOfFee"
        challanName column: "challanName"
        amount column: "amount"
    }
}
