package IST

class BookIssue {

    String issueTo
    Catalog catalog
    String issuingPersonId
    CatalogCatagory catalogCategory
    CatalogType catalogType
    Date issuedDate=new Date()

    static constraints = {
    }

    static mapping = {
         issueTo column: "IssueTo"
         catalog column: "Catalog"
         issuingPersonId column: "IssuingPersonId"
         catalogCategory column: "CatalogCategory"
         catalogType column: "CatalogType"
         issuedDate column: "IssuedDate"

    }
}
