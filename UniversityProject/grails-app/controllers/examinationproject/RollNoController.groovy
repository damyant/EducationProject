package examinationproject

class RollNoController {
   def rollNoInfoService
    def index() {}
    def create(){
        println("hello kuldeep")
    }
    def edit(){
        println("hello kuldeep again")

    }
    def issueRollNo(){
     println("ya these are the params "+params)
        try {
            def objStatus= rollNoInfoService.saveInfo(params)
            if(objStatus=='created'){
                redirect(action: "createNewStudyCenter", params:['status':objStatus])
            }
            else if (objStatus=='updated'){
                redirect(action: "createNewStudyCenter", params:['status':objStatus,'studyCenterId':params.studyCenterId,'type':'edit'])
            }
        }
        catch (Exception e) {
            println("<<<<<<<<<<<There is some problem in saving new study center" + e)
        }
    }
}
