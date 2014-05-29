package examinationproject

class RollNoGenerationFixture {
    Date startD=new  Date()
    Date endD=new  Date()

    static constraints = {
        startD(nullable: true)
        endD(nullable: true)
    }


    static mapping = {
        startD column: 'startDate'
        endD column: 'endDate'
    }
}
