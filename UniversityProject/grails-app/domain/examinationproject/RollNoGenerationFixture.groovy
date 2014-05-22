package examinationproject

class RollNoGenerationFixture {
    Date startD
    Date endD

    static constraints = {
        startD(nullable: true)
        endD(nullable: true)
    }


    static mapping = {
        startD column: 'startDate'
        endD column: 'endDate'
    }
}
