package universityproject

import examinationproject.CourseDetail
import examinationproject.Subject
import grails.transaction.Transactional

@Transactional
class CourseDetailService {

    def serviceMethod() {

    }

    def saveCourseInfo(params){
        new CourseDetail(params).addToSubject(params).save()


//        if(courseDetailObj.save(flush: true,failOnError: true)){
//
//        }

    }
}
