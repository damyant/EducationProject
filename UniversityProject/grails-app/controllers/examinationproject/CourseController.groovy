package examinationproject

import grails.converters.JSON

class CourseController {
    def courseDetailService


    def createNewCourse() {

        def subObj = Subject.findAll()

       [subjList:subObj as JSON]
    }

    def saveCourse() {
        println("????" + params)
        courseDetailService.saveCourseInfo(params)

    }

    def viewCourses(){


    }


}