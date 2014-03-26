package com.university

import examinationproject.ProgramDetail
import examinationproject.StudyCenter

class AdminController {

    def viewProvisionalStudents() {

        def studyCenterList=StudyCenter.findAll()
        def programList=ProgramDetail.findAll()
       [studyCenterList:studyCenterList,programList:programList]
    }

    def generateRollNo(){

    }
}
