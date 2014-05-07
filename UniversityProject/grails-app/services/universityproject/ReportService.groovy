package universityproject

import examinationproject.ProgramDetail
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class ReportService {

    def getReportdataSession(params) {
         def finalStudentMap = [:]
         def totalForSession=0
         def programList = ProgramDetail.list(sort: 'courseName')
         programList.each {
              def pId= it.id
              def stuObj= Student.createCriteria()
              def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                and{
                    eq('registrationYear' , 2014)
                }
                projections {
                    rowCount()
                }
            }

         totalForSession+=count
         finalStudentMap.put(it.courseName, count.getAt(0))
        }
        finalStudentMap.put("TOTAL STUDNETS", totalForSession)
        return finalStudentMap
    }


    def getReportDataSessions(params) {
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseName')

    }

    def getReportDataCourse(params){
        def stuObj= Student.createCriteria()
        def studentList = stuObj.list{
            programDetail{
                eq('id', Long.parseLong(params.course))
            }
            and{
                eq('registrationYear' , Integer.parseInt(params.courseSession))
            }
        }

//        println('--------------------------'+studentList)
        return studentList
    }


    def getReportDataStudyCentre(params){
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseName')
        programList.each {
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                studyCentre{
                    eq('id' , Long.parseLong(params.studyCentre))
                }
                projections {
                    rowCount()
                }
            }

            totalForSession+=count
//            println('final student map is '+ finalStudentMap)
            finalStudentMap.put(it.courseName, count.getAt(0))
        }
        finalStudentMap.put("TOTAL STUDNETS", totalForSession)
        return finalStudentMap
    }
    def getReportDataExaminationCentre(params){
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseName')
        programList.each {
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                studyCentre{
                    eq('id' , Long.parseLong(params.examinationCentre))
                }
                projections {
                    rowCount()
                }
            }

            totalForSession+=count
//            println('final student map is '+ finalStudentMap)
            finalStudentMap.put(it.courseName, count.getAt(0))
        }
        finalStudentMap.put("TOTAL STUDENTS", totalForSession)
        return finalStudentMap
    }
}
