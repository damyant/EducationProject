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
        if(params.catalogIsbn){

        }
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
