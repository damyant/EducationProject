package examinationproject

class CourseController {
    def courseDetailService


def createNewCourse() {

}
 def saveCourse(){
     println("????"+params)
     courseDetailService.saveCourseInfo(params)

 }
    def multiSelect(){
        def list1 = Subject.findAll()
        def list2 = []

        println "Rendering List1 : "+list1
        println "Rendering List2 : "+list2
        [index:params.int('index'), list1:list1, list2:list2]

    }
}
