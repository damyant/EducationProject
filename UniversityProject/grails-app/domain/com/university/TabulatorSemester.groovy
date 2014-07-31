package com.university

//ADDED BY Chandan
class TabulatorSemester {

    String semester

    static belongsTo = [tabulatorProgram:TabulatorProgram]
    static mapping = {
        tabulatorProgram cascade: "none"
        semester column: 'semester'
    }
    static constraints = {
        semester(nullable: false)
    }


}
