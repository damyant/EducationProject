package com.university

import examinationproject.ProgramDetail

//ADDED BY Chandan
class TabulatorProgram implements Serializable{

    User user
    Role role
    ProgramDetail program
    static hasMany = [
            tabulatorSemester : TabulatorSemester
    ]
    static mapping = {
    }
    static constraints = {
        program(nullable: false)
    }


}
