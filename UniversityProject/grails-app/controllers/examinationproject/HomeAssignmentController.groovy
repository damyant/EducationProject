package examinationproject

import grails.converters.JSON

class HomeAssignmentController {
      def springSecurityService
    def index() {}
    def submitHomeAssignment={
        if(params.studentId){
            println('this is the id of student '+ params.termList.size())
            def termList= []
            params.termList.each{
               termList.add(Integer.parseInt(it))
            }
            def studentIns = Student.findById(Integer.parseInt(params.studentId))
            def homeAssignmentIns = HomeAssignment.findAllBySemesterInListAndStudent(termList, studentIns)
            println('this is the homeAssignment '+ homeAssignmentIns)
            def studentName
            def rollNo
            def semList =[]
            def courseName
            if(homeAssignmentIns){
                 studentName = homeAssignmentIns[0].student?.firstName+' '+(homeAssignmentIns[0].student?.middleName? homeAssignmentIns[0].student?.middleName:'')+' '+(homeAssignmentIns[0].student?.lastName)
                 rollNo = homeAssignmentIns[0].student?.rollNo
                 courseName = homeAssignmentIns[0].student?.programDetail[0]?.courseName
                 homeAssignmentIns.each{
                    semList.add(it.semester)

                }
            }
            [studentName: studentName, rollNo:rollNo,semList:semList, courseName:courseName]

        }
        else{

        }
    }
    def  searchHomeAssignmentByRollNumber ={
        def returnMap = [:]
        def assignmentSubmitList =[]
        def stuIns = Student.findByRollNo(params.rollNumber)
        if (stuIns) {
            returnMap.courseName = stuIns.programDetail.courseName
            returnMap.totalYears = stuIns.programDetail.noOfAcademicYears
            returnMap.programType = stuIns.programDetail.programType.type
            returnMap.currrentSemester = stuIns.semester
            returnMap.feeType = FeeType.list()
            returnMap.status = true
            def studentHomeAssignmentList = HomeAssignment.findAllByStudent(stuIns)
            if(studentHomeAssignmentList){
                studentHomeAssignmentList.each{
                    assignmentSubmitList.add(it.semester)
                }
            }
            println('this is the list of semesters '+ assignmentSubmitList)
            returnMap.submitList = assignmentSubmitList
            if (springSecurityService.isLoggedIn()){
                def userDetails = springSecurityService.principal.getAuthorities()
                boolean isAdmin = false
                for (def role in userDetails) {
                        if (role.getAuthority() == "ROLE_ADMIN") {
                            isAdmin = true
                        }
                }
                if (isAdmin){
                  returnMap.user='Admin'
                } else {

                }
            }

        } else {
            returnMap.status = false
        }
        render returnMap  as JSON
    }

    def saveHomeAssignment ={
        println('these are the params '+ params)
        if (springSecurityService.isLoggedIn()){
            def userDetails = springSecurityService.principal.getAuthorities()
            boolean isAdmin = false
            for (def role in userDetails) {
                if (role.getAuthority() == "ROLE_ADMIN") //do something }
                    if (role.getAuthority() == "ROLE_ADMIN") {
                        isAdmin = true
                    }
            }
            if (isAdmin){
                println('admin')
                def studentInstance= Student.findByRollNo(params.rollNumberInput)
                def studentHomeAssignments = HomeAssignment.findAllByStudent(studentInstance)
                studentHomeAssignments.each{
                    it.delete(flush:true)
                }
                    params.terms.each{
                            println('value '+ it)
                            def homAssignmentIns = new HomeAssignment()
                            homAssignmentIns.student = studentInstance;
                            homAssignmentIns.semester = Integer.parseInt(it)
                            homAssignmentIns.save(flush: true, failOnError: true)
                    }
                    redirect(action: 'submitHomeAssignment')
            } else {
                def studentInstance= Student.findByRollNo(params.rollNumberInput)
                params.terms.each{
                    println('value '+ it)
                    def homAssignmentIns = new HomeAssignment()
                    homAssignmentIns.student = studentInstance;
                    homAssignmentIns.semester = Integer.parseInt(it)
                    homAssignmentIns.save(flush: true, failOnError: true)
                }
                redirect(action: 'submitHomeAssignment',params:[studentId:studentInstance.id, termList: params.terms])
            }
        }

    }


    def studentAddress={
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
        [sessionList:sessionList]
    }
}
