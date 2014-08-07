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
        user column: 'UserId'
        role column: 'RoleId'
        program column: 'ProgramId'
    }
    static constraints = {
        program(nullable: false)
    }


}
