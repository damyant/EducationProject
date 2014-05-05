package universityproject

import examinationproject.City
import examinationproject.CourseSubject
import examinationproject.ExaminationCentre
import examinationproject.ExaminationVenue
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class AdminInfoService {
def springSecurityService

    def serviceMethod() {

    }

    def provisionalStudentList(params){
//        println("==========="+springSecurityService.principal.id)

        def studyCenterId=0
        def statusObj
        if(params.studyCenterId){
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()

            studyCenterId=currentUser.studyCentreId
        }


        if(params.pageType=="Approve RollNo"){

            statusObj=Status.findById(2)
        }
        else{
            statusObj=Status.findById(1)
        }
        def obj=Student .createCriteria()
        def stuList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            studyCentre {
                eq('id', Long.parseLong(studyCenterId.toString()))
            }
            and{
                eq('status',statusObj)
                count()
            }


        }


        return  stuList



    }

    def subjectList(params){
        def subjectMap=[:]
        def subList=[],totalDateList=[]
        def dateList=[]
        def programIns=ProgramDetail.findById(Long.parseLong(params.programId))
        def semesterList=Semester.findAllByCourseDetail(programIns)
        def count=0
        semesterList.each{

          subList<<CourseSubject.findAllByCourseDetailAndSemester(programIns,it).subject
       
          dateList=subList.examDate
        }


        for(def i=0;i<subList.examDate.size();i++){

            for(def j=0;j<dateList[i].size();j++){
                if(dateList[i][j]!=null){
              totalDateList<<dateList[i][j].getDateString()
              ++count
            }
                else{
                    totalDateList<<'noo'
                }
            }
        }
        subjectMap.allSubjects=subList
        subjectMap.dateList=totalDateList

      return subjectMap

    }

    def saveExamDate(params){

        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        def subjectList=params.subjectIdList.split(",")
        def count=0
        subjectList.each{i ->
            if(params.examinationDate[count]!=""){
            def subjectIns=Subject.findById(Long.parseLong(i.toString()))
            subjectIns.examDate=f1.parse(params.examinationDate[count])
            subjectIns.examTime=params.examinationTime[count]
            subjectIns.save(failOnError: true)
            ++count;
            }
        }
    }

    def saveExamVenue(params){

        def courseIns=ProgramDetail.findById(Long.parseLong(params.programList))
        def examCentreIns=ExaminationCentre.findById(Long.parseLong(params.examinationCentre))
        def venueList=params.venueList.split(",")
        ProgramExamVenue.removeAll(examCentreIns)

        venueList.each {it ->
            ProgramExamVenue.create courseIns,examCentreIns, ExaminationVenue.findById(Integer.parseInt(it.toString()))

        }
    }
    def updateStudentList(params){
//        println("==========="+springSecurityService.principal.id)
        def studyCenterId=0
        def statusObj
        if(params.studyCenterId){
            studyCenterId=params.studyCenterId
        }
        else{
            def currentUser=springSecurityService.getCurrentUser()

            studyCenterId=currentUser.studyCentreId
        }

         def obj=Student .createCriteria()
         def studList= obj.list{
            programDetail{
                eq('id', Long.parseLong(params.programId))
            }
            studyCentre {
                eq('id', Long.parseLong(studyCenterId.toString()))
            }
        }
        return  studList
    }

}
