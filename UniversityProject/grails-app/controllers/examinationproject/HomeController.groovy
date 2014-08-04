package examinationproject

class HomeController {

    def index() {
        def programName=[],programCount=[],studyCentreName=[],studyCentreAppCount=[],studyCentreUnAppCount=[],studyCentreFeeAppCount=[],studyCentreFeeUnAppCount=[]
        def selfEnrollmentCount=Student.findAllByReferenceNumberNotEqual('0').size()
        def rollNoNotCount=Student.findAllByReferenceNumberNotEqualAndRollNoIsNull('0').size()
        def progList=ProgramDetail.list(sort:'courseCode')
        progList.each {
            Set <ProgramDetail> progInst=ProgramDetail.findAllById(it.id)
            programName<<it.courseName
            def progId=it.id
            def stuObj=Student.createCriteria()
            def stuProgList=stuObj.list(params){
                programDetail {
                    eq('id', progId)
                }
                and{
                ne('referenceNumber','0')
                }
                isNull("rollNo")
            }
                programCount<<stuProgList.size()

        }
        def studyCentreList=StudyCenter.list(sort: 'centerCode')
        studyCentreList.each {
            def studyInst=it.id
            studyCentreName<<it.name
            def stuStudyObj=Student.createCriteria()
            def stuStudyAppList=stuStudyObj.list(params){
                studyCentre {
                    eq('id',studyInst)

                }
                and {
                    eq('status',Status.findById(4))
                }
            }
            def stuUnStudyObj=Student.createCriteria()
            def stuStudyUnAppList=stuUnStudyObj.list(params){
                studyCentre {
                    eq('id',studyInst)

                }
                and {
                    ne('status',Status.findById(4))
                }
            }
                    studyCentreAppCount<<stuStudyAppList.size()

                    studyCentreUnAppCount<<stuStudyUnAppList.size()
            def studObj=Student.createCriteria()
            def studList=studObj.list(params){
                studyCentre {
                    eq('id',studyInst)

                }
            }
                def feeApp=0,feeUnapprov=0
            studList.each{
                def studentInst=it
                    def feeObj=FeeDetails.createCriteria()
                    def feeUnApprove=feeObj.list(params){

                            eq('student',studentInst)
                            ne('isApproved',Status.findById(4))
                    }
                    def feeObj2=FeeDetails.createCriteria()
                    def feeApprove=feeObj2.list(params){
                        eq('student',studentInst)
                        eq('isApproved',Status.findById(4))
                    }
                        if(feeUnApprove.size()>0){
                            feeUnapprov=feeUnapprov+feeUnApprove.size()
                        }
                        if(feeApprove.size()>0){
                            feeApp=feeApp+feeApprove.size()
                        }
                }
                studyCentreFeeAppCount<<feeApp
                studyCentreFeeUnAppCount<<feeUnapprov
            }
        [studyCentreName:studyCentreName,programName:programName,studyCentreFeeUnAppCount:studyCentreFeeUnAppCount,studyCentreFeeAppCount:studyCentreFeeAppCount,selfEnrollmentCount:selfEnrollmentCount,rollNoNotCount:rollNoNotCount,programCount:programCount]

    }



def showResults={
//    println "here.. "+params
   render(view: "viewResult")
}

}
