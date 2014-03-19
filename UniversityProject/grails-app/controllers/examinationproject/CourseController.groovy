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

//    def multiSelect() {
//        def list1 = Subject.findAll()
//        def list2 = []
//        [index: params.int('index'), list1: list1, list2: list2,]
//
//    }
}