package examinationproject

import grails.converters.JSON

class CourseController {
    def courseDetailService


    def createNewCourse() {
        def courseDetail=[:]
        def subObj = Subject.findAll()

        if(params.courseId){
        courseDetail= courseDetailService.getFullDetailOfCourse(params)
        }

       [courseDetail:courseDetail as JSON ,subjList:subObj as JSON]
    }

    def saveCourse() {

        def data = request.JSON
        courseDetailService.saveCourseInfo(data)

    }

    def viewCourses(){

      def courseList=  courseDetailService.getAllCourses()
        [courseList:courseList]

    }
    def updateCourses(){

        redirect(action: "viewCourses", params:['type':"update"])
    }

    def deleteCourse(){
        courseDetailService.deleteCourse(params)
        redirect(action: "viewCourses", params:['type':"update"])
    }


}