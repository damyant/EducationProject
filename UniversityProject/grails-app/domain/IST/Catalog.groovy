package IST

class Catalog {

    transient springSecurityService

    CatalogType type
    String isbn
    String title
    String author
    String publisher
    Integer year
    Integer quantity

    static constraints = {
    }

    static mapping = {
        type column: "type"
        isbn column: 'isbn'
        title column: "title"
        author column: "author"
        publisher column: "publisher"
        year column:"year"
        quantity column:"quantity"
    }

}
