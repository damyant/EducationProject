package postexamination

class MarksType {

    String marksTypeName
    Boolean showValue

    static constraints = {
    }
    static mapping = {
        marksTypeName column: 'MarksTypeName'
        showValue column: 'ShowValue'
    }
}
