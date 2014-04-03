package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import universityproject.CourseDetailService

@Secured("ROLE_ADMIN")
class CourseController {
    def courseDetailService


    def createNewCourse() {
        boolean updateFlag=false
        def courseDetail=[:]
        def subObj = Subject.findAll()
        println("create"+ params)
        if(params.courseId){
        courseDetail= courseDetailService.getFullDetailOfCourse(params)
            updateFlag=true
        }
        println(updateFlag)

       [courseDetail:courseDetail as JSON ,subjList:subObj as JSON,updateFlag:updateFlag]
    }

    /* ********* viewCourses Method Added By Digvijay ******** */
    def viewCourses(){
//        def courseIns= ProgramDetail.findById(params.courseId)
//        println("Inside View"+params)

        def courseDetails  = courseDetailService.getFullDetailOfCourse(params)
        println("aaaaaaaaaaaaaaa"+courseDetails.course.courseMode.modeName)
//
//
//
//        def semDetails = courseIns.semester
//        for (int i=0; i<semDetails.size();i++){
//            println("Semester Number  = "+semDetails[i].semesterNo)
//            //if()
//        }
//
//        println("SemDetails==========================================="+semDetails)
        [courseDetail:courseDetails as JSON]

    }

    def saveCourse() {
        def response=[:]
        def data = request.JSON
        println(data)
        def status=courseDetailService.saveCourseInfo(data)
        response.response1=status
        println(response)
        render response as JSON

    }

    def listOfCourses(){

      def courseList=  courseDetailService.getAllCourses()
        [courseList:courseList]

    }
    def updateCourses(){

        redirect(action: "listOfCourses", params:['type':"update"])
    }

    def deleteCourse(){
        courseDetailService.deleteCourse(params)
        redirect(action: "listOfCourses", params:['type':"update"])
    }


}