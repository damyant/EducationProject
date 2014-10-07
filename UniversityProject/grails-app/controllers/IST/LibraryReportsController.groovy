package IST

class LibraryReportsController {

    def index() {}

    def issuedBooks(){

    }

    def listOfCatalogs(){


    }
    def overdueBooks(){

    }

    def byType(){
        redirect(action: 'listOfCatalogs', params: [by:'byType'])
    }
    def byCategory(){
        redirect(action: 'listOfCatalogs', params: [by:'byCategory'])
    }
    def byIsbn(){
        redirect(action: 'listOfCatalogs', params: [by:'byIsbn'])
    }
    def byTitle(){
        redirect(action: 'listOfCatalogs', params: [by:'byTitle'])
    }
    def byAuthor(){
        redirect(action: 'listOfCatalogs', params: [by:'byAuthor'])
    }
}
