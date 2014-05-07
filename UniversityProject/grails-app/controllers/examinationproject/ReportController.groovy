package examinationproject

import grails.converters.JSON

class ReportController {
    def reportService
    def pdfRenderingService
    def reportIndex = {
        println('in report Index')

//        [totalList :params.totalList, sessionVal:params.sessionVal, flag:params.flag]
    }

    def getSessionList(){
        println("in getSession List")
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
       render sessionList as JSON
    }
   def generateReport={
       println("in generate Report "+ params)
       println('parmas value '+ params.value)
       if(params.value=='session' && params.session){
           println("getting the printable report of students of all course of session "+ params.session)
           def totalList = reportService.getReportdataSession(params)

           println("back in controller with this "+ totalList)
           def args = [template: "generate", model: [totalListBySession :totalList, sessionVal:params.session],filename:params.session+'_All_Course'+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }
      else if(params.value=='sessions' && params.fromSession && params.toSession){
           println("getting the printable report of students of all course of sessions "+ params.fromSession+" and "+ params.toSession)
           def totalList = reportService.getReportDataSessions(params)
       }

      else if(params.value=='course' && params.course){
           def totalList = reportService.getReportDataCourse(params)
           def args = [template: "generate", model: [totalListByCourse :totalList, courseSession:params.courseSession],filename:params.session+'_ALL_Subjects'+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')

       }

      else if(params.value=='studyCentre' && params.studyCentre){
           def totalList = reportService.getReportDataStudyCentre(params)
           def args = [template: "generate", model: [totalListByStudyCentre :totalList, studyCentreSession:params.studyCentreSession],filename:params.studyCentreSession+'_'+params.studyCentre+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }

      else if(params.value=='examinationCentre' && params.examinationCentre){
           def totalList = reportService.getReportDataExaminationCentre(params)
           def args = [template: "generate", model: [totalListByExaminationCentre :totalList, examinationCentreSession:params.examinationCentreSession],filename:params.examinationCentre+'_'+params.examinationCentre+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }

      else if(params.value=='category' && params.categorySession){
           def totalList = reportService.getReportDataCategory(params)
           def args = [template: "generate", model: [totalListByCategory :totalList, categorySession:params.categorySession],filename:params.categorySession+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }
      else if(params.value=='categoryGender' && params.categoryGenderSession){
           println("this function is called")
           def totalList = reportService.getReportDataCategoryGender(params)
           def args = [template: "generate", model: [totalListByCategoryGender :totalList, categoryGenderSession:params.categoryGenderSession],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }
       else if(params.value=='admissionUnapproved' && params.admissionUnapprovedSession){
           println("this function is called")
           def totalList = reportService.getReportDataAdmissionUnapproved(params)
           def args = [template: "generate", model: [totalListByAdmissionUnapproved :totalList, admissionUnapprovedSession:params.admissionUnapprovedSession],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }
       else if(params.value=='studyCentreFeePaid' && params.studyCentreFeePaidSession){
           println("this function is called")
           def totalList = reportService.getReportDataStudyCentreFeePaid(params)
           def args = [template: "generate", model: [totalListByAdmissionUnapproved :totalList, admissionUnapprovedSession:params.admissionUnapprovedSession],filename:params.session+'_All_Course_'+params.value+".pdf"]
           pdfRenderingService.render(args + [controller: this], response)
           redirect(action: 'reportIndex')
       }
   }

}
