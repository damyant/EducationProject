package examinationproject

class MarksCategory {

    String categoryName
    Boolean showValue

    static belongsTo = [subject: Subject]

    static mapping = {
        categoryName column: "CategoryName"
        showValue column: "ShowValue"
        subject column: 'SubjectId'
    }

    static constraints = {
        categoryName(nullable: true)
        showValue(nullable: true)
    }
}
