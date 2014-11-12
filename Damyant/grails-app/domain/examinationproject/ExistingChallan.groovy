package examinationproject

class ExistingChallan {

    String challan
    int session

    static constraints = {
        session(nullable:false,unique: true)
    }
}
