package com.university

import examinationproject.Semester

//ADDED BY Chandan
class TabulatorSemester {

    Semester semesterId

    static belongsTo = [tabulatorProgram:TabulatorProgram]
    static mapping = {
        tabulatorProgram cascade: "none"
        semesterId column: 'SemesterId'
        tabulatorProgram column: 'TabulatorProgramId'
    }
    static constraints = {
        semesterId(nullable: false)
    }


}
