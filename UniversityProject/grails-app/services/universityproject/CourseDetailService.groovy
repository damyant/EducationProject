package universityproject

import examinationproject.CourseDetail
import examinationproject.CourseMode
import examinationproject.CourseSubject
import examinationproject.CourseType
import examinationproject.Semester
import examinationproject.Subject
import grails.transaction.Transactional

@Transactional
class CourseDetailService {

    def serviceMethod() {

    }

    def saveCourseInfo(params) {

        def semObj
        def courseObj = new CourseDetail(params)
        courseObj.save(failOnError: true)
        for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
            semObj = new Semester()
            semObj.semesterNo = i
            semObj.save(failOnError: true)
            params.semesterList.each { i
                it."semester${i}".each { obj ->
                    CourseSubject.create courseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj
                }

            }

        }


    }


}
