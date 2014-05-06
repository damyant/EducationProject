package com.university

import examinationproject.Bank
import examinationproject.Branch
import examinationproject.ExaminationCentre
import examinationproject.ExaminationVenue
import examinationproject.FeeType
import examinationproject.ProgramDetail
import examinationproject.ProgramFee
import examinationproject.Student
import examinationproject.Status
import examinationproject.StudyCenter
import grails.converters.JSON
import javax.activation.MimetypesFileTypeMap
import grails.plugins.springsecurity.Secured



class AdminController {

    def adminInfoService
    def pdfRenderingService
    def studentRegistrationService
    def springSecurityService

    def attendanceService
    @Secured(["ROLE_GENERATE_ROLL_NO"])

    def viewProvisionalStudents() {

        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseName')
       [studyCenterList:studyCenterList,programList:programList]
    }

    @Secured("ROLE_ADMIN")
    def viewApprovedStudents(){
        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseName')
        [studyCenterList:studyCenterList,programList:programList]
    }

    def getStudentList(){
        def responseMap=[:]
        def stuList= adminInfoService.provisionalStudentList(params)
        responseMap.status="referenceNo"
        responseMap.label=params.pageType
        responseMap.stuList=stuList
        render responseMap as JSON

    }
    @Secured("ROLE_ADMIN")
    def generateRollNo(){
        String rollNumber=null
        def stuObj
        def stuList=[]
        def responseMap=[:]
        def status
        if(params.pageType=="Approve RollNo"){
            status= studentRegistrationService.approvedStudents(params)
        }
        else{
            def studentIdList = params.studentList.split(",")
            studentIdList.each { i ->
                rollNumber =studentRegistrationService.getStudentRollNumber(params)
                 stuObj = Student.findById(i)
                 stuObj.rollNo = rollNumber
                 stuObj.status = Status.findById(Long.parseLong("2"))
                 stuObj.save(flush: true,failOnError: true)
            }
        }

        if(stuObj){
        stuList= adminInfoService.provisionalStudentList(params)
        }
        responseMap.status='rollNo'
        responseMap.stuList=stuList
        render responseMap as JSON
        render stuList as JSON
    }

    @Secured(["ROLE_ADMIN","ROLE_IDOL_USER"])
    def feeVoucher={
        def feeType = FeeType.list(sort:'type')
        def selectFeeType=FeeType.findAllById(1)
        [feeType:feeType,selectFeeType:selectFeeType]
    }


    def examFeeVoucher = {
        def feeType = FeeType.list(sort:'type')

        [feeType:feeType]
    }

    def checkFeeByRollNo = {
        def student = Student.findByRollNo(params.rollNo)
        def program= student.programDetail
        def programName=program[0].courseName
        boolean status
        def programFee = ProgramFee.findByProgramDetail(program)
        if(programFee)
            status=true
        else
            status = false
        def response =[id:student.id,feeStatus:status,program:programName]
        render response as JSON
    }

    @Secured(["ROLE_ADMIN","ROLE_IDOL_USER"])
    def generateFeeVoucher={
        println(">>>>>>>>????????>>"+params)
        def student = Student.findByRollNo(params.rollNo)
        if(!(student.studyCentre[0].centerCode=="11111")){
             redirect(action: "feeVoucher",params:[error:"error"])
        }



        def program= student.programDetail
        println("&&&&&&&&&&&&&&&&&&&&&&&"+program)
        def feeTypeId =Integer.parseInt(params.feeType)
        def feeType = FeeType.findById(feeTypeId)
        def programFee = ProgramFee.findByProgramDetail(program)
        def programFeeAmount

            switch(feeTypeId){
                case 1:
                    programFeeAmount = programFee.feeAmountAtIDOL
                    break;
                case 2:
                    programFeeAmount = programFee.examinationFee
                    break;
                case 3:
                    programFeeAmount = programFee.certificateFee
                    break;
            }
        println("feeTypeId    "+feeTypeId+"********"+programFee.feeAmountAtIDOL)
        def args = [template:"feeVoucher", model:[student:student, programFee:programFee,programFeeAmount:programFeeAmount,feeType:feeType]]
        pdfRenderingService.render(args+[controller:this],response)

    }

    @Secured("ROLE_ADMIN")
    def assignExaminationDate={
        def programList = ProgramDetail.list(sort:'courseName')
        [programList: programList]
    }
    @Secured("ROLE_ADMIN")
    def assignExaminationVenue={
        def programList = ProgramDetail.list(sort:'courseName')
        def examinationCenter=ExaminationCentre.list(sort:'examinationCentreName')

        [programList: programList,examinationCenterList:examinationCenter]
    }

    def getSubjectList={
        def subMap=[:]
       subMap= adminInfoService.subjectList(params)

        if(subMap.allSubjects.size()<1){
            subMap.noSubjects=true
            render subMap as JSON
        }
        else{
            render subMap as JSON
        }
    }

    def saveExamDate={
        def checkStatus=[:]
        def status=adminInfoService.saveExamDate(params)

        if(status.size()>1){
            checkStatus.saveFlag=true
        }
        else{
            checkStatus.saveFlag=false
        }
        render checkStatus as JSON

    }

    def saveExamVenue={

        def status=adminInfoService.saveExamVenue(params)
        if(status){
            render status
        }
        else{
            render "<h5>Error In Assigning Examination Venue</h5>"
        }

    }
    def generateStudentList={
        def studList= adminInfoService.updateStudentList(params)
        render studList as JSON
    }


    def downloadAttendanceSheet = {
        if(params.programSession){
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Attendance')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/")+'Attendance'+System.getProperty('file.separator')+'Output'+'.xls'
            println('this is the real path '+excelPath)
            def status= attendanceService.getStudentList(params,excelPath)
            if(status){
                println("hello kuldeep u r back in controller "+ status)
                File myFile = new File(servletContext.getRealPath("/")+'Attendance'+System.getProperty('file.separator')+'Output'+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'Output'+".xls"
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{

            }
        }

        else{
            println("there is no parameters")
        }

    }
    def uploadInternalMarks={
        def studyCentreList = StudyCenter.list(sort:'name')
        def programList = ProgramDetail.list(sort:'courseName')
        [programList: programList, studyCentreList: studyCentreList]
    }
    def approvePayInSlip={
        def bankList= Bank.list(sort:'bankName');
        [bankList:bankList]
    }
    def getBranchList={
        def list=Bank.findAllById(Integer.parseInt(params.bank));
        println("))))))))@@@@@@@@@@@@@@@@@@"+list.branch[0].branchLocation)
        render list.branch[0] as JSON
    }
    def studyCentreFeeApproval={
        def studyCenterList=StudyCenter.list(sort: 'name');
        def programList = ProgramDetail.list(sort:'courseName')
        [studyCenterList:studyCenterList,programList:programList]
    }
}

