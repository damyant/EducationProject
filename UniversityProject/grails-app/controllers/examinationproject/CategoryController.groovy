package examinationproject

import grails.plugins.springsecurity.Secured

class CategoryController {

    def index() { }
    @Secured(["ROLE_ADMIN"])
    def createNewCategory(){
        if(params.categoryId){
//            println("this is the name of the category "+ params.categoryName)
            def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
            [programType: programType]
           }
    }
    def saveNewCategory(){
        if(params.categoryId){
//            println("this is the name of the category "+ params.categoryName)
            def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
            programType.type= params.categoryName
            if(programType.save(flush: true, failOnError: true)) {
                flash.message = "${message(code: 'category.crated.message')}"
                redirect(action: 'createNewCategory')
            }
            else{
                flash.message = "${message(code: 'category.notCreated.message')}"
            }
        }
    else{
//            println("this is the name of the category "+ params.categoryName)
            def programType = new ProgramType()
            programType.type= params.categoryName
            if(programType.save(flush: true, failOnError: true)) {
                flash.message = "${message(code: 'category.crated.message')}"
                redirect(action: 'createNewCategory')
            }
            else{
                flash.message = "${message(code: 'category.notCreated.message')}"
                redirect(action: 'createNewCategory')
            }
        }
    }
    @Secured(["ROLE_ADMIN"])
    def categoryList(){
        def programTypeList = ProgramType.list()
        [programTypeList:programTypeList]
    }

    def deleteCategory(){
        def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
        try{
            programType.delete(flush: true)
//            println('category deleted successfully')
            flash.message = "${message(code: 'category.deleted.message')}"
            redirect(action: 'categoryList')
        }
        catch(Exception e){
//            println('category not deleted successfully' + e)
            flash.message = "${message(code: 'category.notDeleted.message')}"
            redirect(action: 'categoryList')
        }
    }
}
