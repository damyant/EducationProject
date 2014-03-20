package examinationproject

import grails.converters.JSON

class CourseController {
    def courseDetailService


    def createNewCourse() {

        def subObj = Subject.findAll()

       [subjList:subObj as JSON]
    }

    def saveCourse() {

        def data = request.JSON
        courseDetailService.saveCourseInfo(data)

    }

    def viewCourses(){


    }


}