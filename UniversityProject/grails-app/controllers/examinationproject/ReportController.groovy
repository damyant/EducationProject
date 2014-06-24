package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import javax.activation.MimetypesFileTypeMap

class ReportController {
    def reportService
    def pdfRenderingService
    def springSecurityService
    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE"])


    def index ={
        render 'hello kuldeep'
    }


    def reportIndex = {
//        println('in report Index')

//        [totalList :params.totalList, sessionVal:params.sessionVal, flag:params.flag]
    }

    def getSessionList(){
//        println("in getSession List")
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
       render sessionList as JSON
    }
   def generateReport={
       println('hello ')
       println("in generate Report "+ params.value)
//       println('params value '+ params.value)
       if(params.value=='session' && (params.session || params.sessionStudentList)){
           println("getting the printable report of students of all course of session "+ params)
           if(params.inExcel){
               def webRootDir = servletContext.getRealPath("/")
               def userDir = new File(webRootDir,'/Report')
               userDir.mkdirs()
               def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_.xls'
//               println('this is the real path '+excelPath)
               def status = reportService.getReportDataSession(params, excelPath)

               if(status){
//                   println("hello kuldeep u r back in controller "+ status)
                   File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_.xls')
                   response.setHeader "Content-disposition", "attachment; filename="+'Student_List_.xls'
                   response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                   response.outputStream << myFile .bytes
                   response.outputStream.flush()
                   myFile.delete()
               }
           }
           else{
           def totalList = reportService.getReportDataSession(params, null)
           def sessionVal= Integer.parseInt(params.session)+1
           sessionVal= params.session+'-'+sessionVal
               def currentUser=springSecurityService.getCurrentUser()
               def studyCenterId = currentUser.studyCentreId
               def studyCenter= StudyCenter.findById(studyCenterId)
               println("back in controller with this "+ totalList)
               def args = [template: "generate", model: [totalListBySession :totalList, sessionVal:sessionVal, studyCentreName: studyCenter?studyCenter.name:''],filename:params.session+'_All_Course'+".pdf"]
               println("now going to render the pdf "+ this)
               pdfRenderingService.render(args + [controller: this ], response)
              // redirect( action: reportIndex)
           }

       }
      else if(params.value=='sessions' && params.fromSession && params.toSession){

//           println("getting the printable report of students of all course of sessions "+ params.fromSession+" and "+ params.toSession)
           def startSession=Integer.parseInt(params.fromSession)
           def endSession = Integer.parseInt(params.toSession)
           def totalList = reportService.getReportDataSessions(startSession, endSession)
//           println("back in controller with this "+ totalList)
           def sessionList=[]
           for(int i= startSession; i<=endSession;i++){
               def sessionVal= i+1
               sessionVal= i+'-'+sessionVal
               sessionList.add(sessionVal)
           }
           def args = [template: "generate", model: [totalListBySessions :totalList, sessionVal: sessionList, ],filename:params.session+'_All_Course'+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
                  //   redirect( action: reportIndex)


       }

      else if(params.value=='course' && params.course){
           def webRootDir = servletContext.getRealPath("/")
           def userDir = new File(webRootDir,'/Report')
           userDir.mkdirs()
           def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.courseSession+'.xls'
//           println('this is the real path '+excelPath)
           def status = reportService.getReportDataCourse(params, excelPath)

           if(status){
//               println("hello kuldeep u r back in controller "+ status)
               File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.courseSession+'.xls')
               response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.courseSession+".xls"
               response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
               response.outputStream << myFile .bytes
               response.outputStream.flush()
               myFile.delete()
           }


       }

      else if(params.value=='studyCentre' && params.studyCentre){
           if(params.inExcel){
               def webRootDir = servletContext.getRealPath("/")
               def userDir = new File(webRootDir,'/Report')
               userDir.mkdirs()
               def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studyCentreSession+'.xls'
//               println('this is the real path '+excelPath)
               def status = reportService.getReportDataStudyCentre(params, excelPath)

               if(status){
//                   println("hello kuldeep u r back in controller "+ status)
                   File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studyCentreSession+'.xls')
                   response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.studyCentreSession+".xls"
                   response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                   response.outputStream << myFile .bytes
                   response.outputStream.flush()
                   myFile.delete()
               }
           }
           else{
           def totalList = reportService.getReportDataStudyCentre(params, null)
           def studyCentre= StudyCenter.findById(Long.parseLong(params.studyCentre))
           def sessionVal= Integer.parseInt(params.studyCentreSession)+1
           sessionVal= params.studyCentreSession+'-'+sessionVal
           def args = [template: "generate", model: [totalListByStudyCentre :totalList, studyCentreSession:sessionVal, studyCentreName: studyCentre.name],filename:'Student_List_'+params.studyCentreSession+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
              // redirect( action: reportIndex)

           }
       }

      else if(params.value=='examinationCentre' && params.examCity){
           if(params.inExcel){
               def webRootDir = servletContext.getRealPath("/")
               def userDir = new File(webRootDir,'/Report')
               userDir.mkdirs()
               def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.examinationCentreSession+'.xls'
               println('this is the real path '+excelPath)
               def status = reportService.getReportDataExaminationCentre(params, excelPath)

               if(status){
                   println("hello kuldeep u r back in controller "+ status)
                   File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.examinationCentreSession+'.xls')
                   response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.studyCentreSession+".xls"
                   response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                   response.outputStream << myFile .bytes
                   response.outputStream.flush()
                   myFile.delete()
               }
           }

           else{
               def totalList = reportService.getReportDataExaminationCentre(params, null)
               def sessionVal= Integer.parseInt(params.examinationCentreSession)+1
               def examinationCentre = City.findById(Long.parseLong(params.examCity))
               sessionVal= params.examinationCentreSession+'-'+sessionVal
               def args = [template: "generate", model: [totalListByExaminationCentre :totalList, examinationCentreSession:sessionVal, examinationCentre: examinationCentre.cityName],filename:'Student_List_'+params.examinationCentreSession+".pdf"]
               pdfRenderingService.render(args + [controller: this], response)
              // redirect( action: reportIndex)

           }
       }

      else if(params.value=='category' && params.categorySession){
           def totalList = reportService.getReportDataCategory(params)
           def sessionVal= Integer.parseInt(params.categorySession)+1
           sessionVal= params.categorySession+'-'+sessionVal
           def args = [template: "generate", model: [totalListByCategory :totalList, categorySession:sessionVal],filename:params.categorySession+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
       }
      else if(params.value=='categoryGender' && params.categoryGenderSession){
           println("this function is called")
           def totalList = reportService.getReportDataCategoryGender(params)
           def sessionVal= Integer.parseInt(params.categoryGenderSession)+1
           sessionVal= params.categoryGenderSession+'-'+sessionVal
           def args = [template: "generate", model: [totalListByCategoryGender :totalList, categoryGenderSession:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           //redirect(action: 'reportIndex')
       }
      else if(params.value=='admissionUnapproved' && params.admissionUnapprovedSession || params.value=='admissionApproved' && params.admissionApprovedSession){
           println("this function is called")
           def sessionVal
           def totalList = reportService.getReportDataAdmissionApprovedUnapproved(params)
           if(params.value=='admissionUnapproved'){
           sessionVal= Integer.parseInt(params.admissionUnapprovedSession)+1
           sessionVal= params.admissionUnapprovedSession+'-'+sessionVal
           }
           else if(params.value=='admissionApproved'){
               sessionVal= Integer.parseInt(params.admissionApprovedSession)+1
               sessionVal= params.admissionApprovedSession+'-'+sessionVal
           }
           println('this is the value of total list '+ totalList)
           def args = [template: "generate", model: [totalListByAdmissionApprovedUnapproved :totalList, admissionApprovedUnapprovedSession:sessionVal, value:params.value],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
          // redirect(action: 'reportIndex')
      }


      else if(params.value=='admissionSelfRegistration' && params.admissionSelfRegistrationSession){
           println("this function is called "+ params)
           def totalList = reportService.getReportDataAdmissionSelfRegistration(params)
           def sessionVal= Integer.parseInt(params.admissionSelfRegistrationSession)+1
           sessionVal= params.admissionSelfRegistrationSession+'-'+sessionVal
           println('this is the list '+ totalList)
           def args = [template: "generate", model: [totalListBySelfRegistration :totalList, admissionSelfRegistrationSession:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
          // redirect(action: 'reportIndex')
      }


      else if(params.value=='studyCentreFeePaid' && params.studyCentreFeeFromDate && params.studyCentreFeeToDate ){
           println("this function is called")
           def totalList = reportService.getReportDataStudyCentreFeePaid(params)
           def feeType = FeeType.list()
//           def sessionVal= Integer.parseInt(params.studyCentreFeePaidSession)+1
//           sessionVal= params.studyCentreFeePaidSession+'-'+sessionVal
           def args = [template: "generate", model: [totalListByStudyCentreFeePaid :totalList, feeType: feeType],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
         //  redirect(action: 'reportIndex')
      }

      else if(params.value=='sessionsComparative' && params.fromSessionComparative && params.toSessionComparative){
           println("this function is called")
           def startSession=Integer.parseInt(params.fromSessionComparative)
           def endSession = Integer.parseInt(params.toSessionComparative)
           def totalList = reportService.getReportDataComparative(startSession, endSession)
           def sessionList=[]
           for(int i= startSession; i<=endSession;i++){
               def sessionVal= i+1
               sessionVal= i+'-'+sessionVal
               sessionList.add(sessionVal)
           }
           def args = [template: "generate", model: [totalListBySessionComprative :totalList, sessionVal:sessionList],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
      }
      else if(params.value=='categoryStudentList' && params.categoryStudentListSession){
           def webRootDir = servletContext.getRealPath("/")
           def userDir = new File(webRootDir,'/Report')
           userDir.mkdirs()
           def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+'.xls'
           println('this is the real path '+excelPath)
           def status = reportService.getReportDataStudentCategory(params, excelPath)

           if(status){
               println("hello kuldeep u r back in controller "+ status)
               File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+'.xls')
               response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+".xls"
               response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
               response.outputStream << myFile .bytes
               response.outputStream.flush()
               myFile.delete()
           }

      }

      else if(params.value=='courseUnapproved' && params.courseUnapprovedSession || params.value=='courseApproved' && params.courseApprovedSession){
           println("this function is called")
           def totalList = reportService.getReportDataCourseApprovedUnapproved(params)
           def sessionVal= Integer.parseInt(params.courseApprovedSession)+1
           sessionVal= params.courseApprovedSession+'-'+sessionVal
           def type
           if(params.value=='courseUnapproved')
                type = 'Unapproved'
           else
                type='Approved'
           def args = [template: "generate", model: [totalListApprovedUnapprovedRollNo :totalList, approvedUnapprovedSessionVal:sessionVal, type:type],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
          // redirect(action: 'reportIndex')
      }
//have to complete*****************************************************************
      else if(params.value=='examinationCentreCumulative' && params.examinationCentreCumulativeSchedule){
           println("this cumulative function is called")
           def totalList = reportService.getReportDataExaminationCentreCumulative(params)
           def sessionVal= Integer.parseInt(params.courseApprovedSession)+1
           sessionVal= params.courseApprovedSession+'-'+sessionVal
           def args = [template: "generate", model: [totalListApprovedUnapprovedRollNo :totalList, approvedUnapprovedSessionVal:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
         //  redirect(action: 'reportIndex')

       }
//************************************************************************************
       //Added By Digvijay on 13 May
       else if(params.value=='dailyFeePaid' && params.feeFromDate && params.feeToDate){
           println("Report Controller --> Daily Fee Report")
           def totalList = reportService.getReportDataDailyFeePaid(params)
//           def sessionVal= Integer.parseInt(params.studyCentreFeePaidSession)+1
//           sessionVal= params.studyCentreFeePaidSession+'-'+sessionVal
           def args = [template: "generate", model: [totalListByDailyFeePaid :totalList, studyCentreFeePaidSession:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
          // redirect(action: 'reportIndex')
       }
   }

}
