package com.university

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.ExaminationCentre
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.ProgramDetail

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
    def feeDetailService
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
        def feeType = []
        feeType = FeeType.list(sort:'type')
      // def selectFeeType=FeeType.findAllById(1)
       // feeType.add(new FeeType(type:"Admission Fee"))
        [feeType:feeType]
    }


    def examFeeVoucher = {
        def feeType = FeeType.list(sort:'type')

        [feeType:feeType]
    }

    def checkFeeByRollNo = {
        println("?????????????"+params)

        def response
        try{
        def student = Student.findByRollNo(params.rollNo)
            println("??????????"+student)
        def program= student.programDetail[0]
        def feeType
        def programName=program.courseName
        boolean status
        def admissionFee = AdmissionFee.findByProgramDetail(program)
        def mFee

       if(Integer.parseInt(params.feeType)>0){
           println("Hello")
           feeType = FeeType.findById(params.feeType)
           mFee = MiscellaneousFee.findByFeeTypeAndProgramDetailAndProgramSession(feeType,program,student.programSession)
           if(mFee)
               status=true
           else
               status= false
       }else{
        if(admissionFee)
            status=true
        else
            status = false

}
        response =[id:student.id,feeStatus:status,program:programName,feeType:feeType]
        }catch(Exception ex){
            println("problem in checking the existence of roll number"+ex)
        }
        render response as JSON
    }

    @Secured(["ROLE_ADMIN","ROLE_IDOL_USER"])
    def generateFeeVoucher={
       println(">>>>>>>>????????>>"+params)
        def student = Student.findByRollNo(params.rollNo)
        def program = student.programDetail[0]
        def feeTypeId
        def feeType=null
        def args
        def programFeeAmount=0
        def programFee = AdmissionFee.findByProgramDetailAndProgramSession(program,student.programSession)

//        println("&&&&&&&&&&&&&&&&&&&&&&&"+program)
        if(Integer.parseInt(params.feeType)>0){
            println("hfdsfsfgs???????????????????????????")
            try{
            feeTypeId =Integer.parseInt(params.feeType)
            feeType = FeeType.findById(feeTypeId)
            def mFee = MiscellaneousFee.findByFeeTypeAndProgramDetailAndProgramSession(feeType,program,student.programSession)
            programFeeAmount= mFee.amount
            }catch(NullPointerException ex){
                println("MiscellaneousFee does not exists"+ex)

            }
        }else{
            println("hfdsfsfgs?????????????????>>>>>>>>>>>>>>>>?")
            feeType = null
            programFeeAmount=programFee.feeAmountAtIDOL
        }

//        println("feeTypeId    "+feeTypeId+"********"+programFee.feeAmountAtIDOL)
        if(params.idol=="idol")
             args = [template:"feeVoucherAtIdol", model:[student:student, programFee:programFee,programFeeAmount:programFeeAmount,feeType:feeType]]
        else
            args = [template:"feeVoucher", model:[student:student, programFee:programFee,programFeeAmount:programFeeAmount,feeType:feeType]]
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
        println("******************"+studList.firstName);
        render studList as JSON
    }


    def downloadAttendanceSheet = {
        if(params.programSession){
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Attendance')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/")+'Attendance'+System.getProperty('file.separator')+'Output'+'.xls'
//            println('this is the real path '+excelPath)
            def status= attendanceService.getStudentList(params,excelPath)
            if(status){
//                println("hello kuldeep u r back in controller "+ status)
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
//            println("there is no parameters")
        }

    }
    def uploadInternalMarks={
        def studyCentreList = StudyCenter.list(sort:'name')
        def programList = ProgramDetail.list(sort:'courseName')
        [programList: programList, studyCentreList: studyCentreList]
    }
    def approvePayInSlip={
        def bankList= Bank.list(sort:'bankName');
        def feeTypeList = FeeType.list(sort: 'type');
        [bankList:bankList,feeTypeList:feeTypeList]
    }
    def getBranchList={
        def list=Bank.findAllById(Integer.parseInt(params.bank));
//        println("))))))))@@@@@@@@@@@@@@@@@@"+list.branch[0].branchLocation)
        render list.branch[0] as JSON
    }
    def studyCentreFeeApproval={
        def studyCenterList=StudyCenter.list(sort: 'name');
        def programList = ProgramDetail.list(sort:'courseName')
        [studyCenterList:studyCenterList,programList:programList]
    }
    def getChallanDetailsforStudent={
        def resultMap=[:]
        println(">>>>>>>>>>>>>>>>>>>"+params)
        resultMap = feeDetailService.studentDetailByChallanNumber(params)


//        def studentInst=Student.findAllByChallanNo(params.challanNo);
//        println("%%%%%%%%%%%%%%%%%%%%%% "+studentInst);
//        def feeAmount=[]
//        for(int i=0;i<studentInst.size();i++){
//           def amount=AdmissionFee.findAllByProgramDetail(studentInst[i].programDetail);
//            if(studentInst[i].studyCentre[0].centerCode=='11111'){
//                feeAmount.add(amount[0].feeAmountAtIDOL)
//            }
//            else{
//                feeAmount.add(amount.feeAmountAtSC)
////                println("feeAmountAtSC"+amount[0].feeAmountAtSC)
//            }
//
//        }
//        resultMap.studentInst=studentInst;
//        resultMap.feeAmount=feeAmount;
        render resultMap as JSON
    }
    def saveApprovePayInSlip={
//        println("saving  "+params);
        def returnMap=[:]
        Boolean result=adminInfoService.savePayInSlip(params);
        returnMap.flag=result
//        println(result);
        render returnMap as JSON
    }
    def getFeeAmount={

        def resultMap=[:]
        println(params)
        def feeAmount=AdmissionFee.findByProgramDetail(ProgramDetail.findById(Integer.parseInt(params.program)));
        resultMap.feeAmount=feeAmount.feeAmountAtIDOL;
        render resultMap as JSON
    }

    def searchListStudentByChallanNo(){
        def returnMap=[:]
        def courseNameList=[],courseFee=[]
        def stuList=  Student.findAllByChallanNo(params.challanNo)
        stuList.each{
            println("==="+it.programDetail[0])
            courseNameList<<it.programDetail[0].courseName
            courseFee<<AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC
        }
        returnMap.stuList=stuList
        returnMap.courseNameList=courseNameList
        returnMap.courseFee=courseFee
        render  returnMap as JSON
    }
    def searchByChallanNo(){


        def returnMap = [:]
        println("???????/"+params)
        returnMap = feeDetailService.studentDetailByChallanNumber(params)
        render  returnMap as JSON
    }

    def approveFeeForStudents = {
   println(">>>>>>>>>>>>>"+params.studentListId)
        def student
      params.studentListId.each{
          student = Student.findById(it)
          def status = Status.findById(4)
          student.status = status
          student.save(flush: true)
    }
        if(student){
            flash.message = "Approved Successfully"
            redirect(action: "approvePayInSlip")
        }

    }
}

