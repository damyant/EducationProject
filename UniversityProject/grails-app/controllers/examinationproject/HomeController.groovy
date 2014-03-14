package examinationproject

class HomeController {

    def index() { }



def showResults={
    println "here.. "+params
   render(view: "viewResult")
}

}
