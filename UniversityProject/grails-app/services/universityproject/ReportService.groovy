package universityproject

import examinationproject.ProgramDetail
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class ReportService {

    def getReportdataSession(params) {
         def finalStudentMap = [:]
         def totalForSession=0
         def programList = ProgramDetail.list(sort: 'courseCode')
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
        def programList = ProgramDetail.list(sort: 'courseCode')

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

        println('--------------------------'+studentList)
        return studentList
    }


    def getReportDataStudyCentre(params){
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseCode')
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
        finalStudentMap.put("TOTAL STUDENTS", totalForSession)
        return finalStudentMap
    }
    def getReportDataExaminationCentre(params){
        def finalStudentMap = [:]
        def totalForSession=0
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def pId= it.id
            def stuObj= Student.createCriteria()
            def count = stuObj.list{
                programDetail{
                    eq('id', pId)
                }
                examinationCentre{
                    eq('id' , Long.parseLong(params.examCity))
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

    def getReportDataCategory(params){
        def categoryList=['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        println("this function is called "+ params)
        def finalStudentMap = [:]
        def  totalByCategory= [0,0,0,0,0,0]
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def sizeList=[]
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
                    groupProperty("category")
                    rowCount()
                }
            }

            println('count is '+ count)
           for(int i=0; i<categoryList.size(); i++){
                if(count.size()){
                    boolean flag = false
                    for(int j=0;j<count.size();j++){

                        if(count[j]?.getAt(0)==categoryList[i]){
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i]+count[j]?.getAt(1))
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                }
                else{
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        println('totalByCategory is '+ totalByCategory)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }



    def getReportDataCategoryGender(params){
        println('params in service '+params)

        def categoryList=['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        def genderList=['Male', 'Female']
        println("this function is called "+ params)
        def finalStudentMap = [:]
        def  totalByCategoryGender= [0,0,0,0,0,0,0,0,0,0,0,0]
        def programList = ProgramDetail.list(sort: 'courseCode')

        programList.each {
            def sizeList=[]
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
                    groupProperty("category")
                    groupProperty("gender")
                    rowCount()
                }
            }
//            println("result of count is "+count)
            int l=0
            for(int i=0; i<categoryList.size(); i++){

                    for(int k=0; k< genderList.size() ;k++){
                          if(count.size()){
                              boolean flag = false
                                for(int j=0;j<count.size();j++){

                                    if(count[j]?.getAt(0)==categoryList[i] && count[j].getAt(1)==genderList[k]){
                                        sizeList.add(l, count[j]?.getAt(2))
//                                        println("match value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                                        totalByCategoryGender.set(l, totalByCategoryGender[l]+count[j]?.getAt(2))
                                        l=l+1
                                        flag=true;
                                        break;
                                    }
                                }
                                if(flag==false){
                                    sizeList.add(l, 0)
//                                    println("flag value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                                    l=l+1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                                }
                          }
                          else{
                              sizeList.add(l, 0)
//                              println("else value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                              l=l+1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                          }
//                     println("value of l is "+ l)
                    }
            }
            println("this is the sizeList "+ sizeList)
            finalStudentMap.put(it.courseName, sizeList)
        }
        println("total  "+ totalByCategoryGender)
        finalStudentMap.put('TOTAL STUDENTS', totalByCategoryGender)
        return finalStudentMap
    }


    def getReportDataAdmissionUnapproved(params){


            def stuObj= Student.createCriteria()
            def studentList = stuObj.list{

                studyCentre{
                    eq('id' , Long.parseLong(params.admissionUnapprovedStudyCentre))
                }
                and{
                    eq('registrationYear' , Integer.parseInt(params.admissionUnapprovedSession))
                }
                and{
                    eq('status', Status.findById(3))
                }

                order('programDetail', 'asc')
            }

       println("this is the result of query "+ studentList)
        return studentList
    }

    def getReportDataStudyCentreFeePaid(params){

    }

}
