package IST

import grails.converters.JSON

import java.text.SimpleDateFormat


class LibraryReportsController {

    def index() {}

    def issuedBooks(){

    }

    def listOfCatalogs(){
        def catalogTypeList = CatalogType.list()
        def catalogCatagoryList = CatalogCatagory.list()
        [catalogTypeList: catalogTypeList,catalogCatagoryList: catalogCatagoryList]
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
    def getIssuedBooks={
        def finalList=[]
        def bookIns=BookIssue.findAllByIssuingPersonId(params.id)
        if(bookIns){
            bookIns.eachWithIndex{itr, i ->
                def subList=[]
                subList<<itr.catalog.title
                subList<<itr.catalogType.catalogTypeName
                subList<<itr.catalog.isbn
                subList<<itr.issuedDate.getDateString()
                finalList<<subList
            }
        }
        else{

        }

        render finalList as JSON

    }

    def getCatalogList={
        def finalList=[]
        if(params.catalogIsbn){
            def catalogObj=Catalog.findByIsbn(params.catalogIsbn)
            if(catalogObj){
            def subList=[]
            subList<<catalogObj.title
            subList<<catalogObj.publisher
            subList<<catalogObj.isbn
            subList<<catalogObj.author
            finalList<<subList
            }
        }
        if(params.catalogTitle){
            def catalogObj=Catalog.findAllByTitle(params.catalogTitle)
            if(catalogObj){
                catalogObj.each{
                def subList=[]
                subList<<it.title
                subList<<it.publisher
                subList<<it.isbn
                subList<<it.author
                finalList<<subList
                }
            }
        }
        if(params.catalogCatagory && params.catalogType){
            def catalogCategory=CatalogCatagory.get(params.catalogCatagory)
            def catalogType=CatalogType.get(params.catalogType)
            def catalogObj=Catalog.findAllByCatagoryAndType(catalogCategory,catalogType)
            if(catalogObj){
                catalogObj.each{
                    def subList=[]
                    subList<<it.title
                    subList<<it.publisher
                    subList<<it.isbn
                    subList<<it.author
                    finalList<<subList
                }
            }
        }
        if(params.catalogAuthor){
            def catalogObj=Catalog.findAllByAuthor(params.catalogAuthor)
            if(catalogObj){
                catalogObj.each{
                    def subList=[]
                    subList<<it.title
                    subList<<it.publisher
                    subList<<it.isbn
                    subList<<it.author
                    finalList<<subList
                }
            }
        }
        render finalList as JSON



    }

    def getOverDueBooks={

        def finalList=[]
        def bookObj=BookIssue.findAllByIssuingPersonId(params.id)
        Calendar c = Calendar.getInstance();
        bookObj.each{
        c.setTime(it.issuedDate);
        c.add(Calendar.DATE, 1);
            if(c.getTime()<new Date()){
                def subList=[]
                subList<<it.catalog.title
                subList<<it.catalogType.catalogTypeName
                subList<<it.catalog.isbn
                subList<<it.issuedDate.getDateString()
                finalList<<subList
            }
        }
        render finalList as JSON
    }
}
