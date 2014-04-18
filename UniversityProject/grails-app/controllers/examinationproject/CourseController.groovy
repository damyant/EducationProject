package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import universityproject.CourseDetailService
import java.io.File;

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


    def viewCourses(){
        def courseDetails  = courseDetailService.getFullDetailOfCourse(params)
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

        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def c = ProgramDetail.createCriteria()
        def courseList = c.list(params) {
         order("courseName", "asc")
        }

        [courseList:courseList,courseInstanceTotal:ProgramDetail.count()]


    }
    def updateCourses(){

        redirect(action: "listOfCourses", params:['type':"update"])
    }

    def deleteCourse(){
        courseDetailService.deleteCourse(params)
        redirect(action: "listOfCourses", params:['type':"update"])
    }
    def uploadSyllabus(){
//        println("Upload \t"+ params)
//        println("Upload \t"+ params.syllabusCourse)
        try {
            if (params.syllabusFile) {
                String ext = "";
                def fileToBeUploaded = request.getFile('syllabusFile')
//                println(fileToBeUploaded)
                String fileName = fileToBeUploaded.originalFilename
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    ext = fileName.substring(i+1);
                }
                def servletContext = ServletContextHolder.servletContext
                def storagePath = servletContext.getRealPath( 'syllabus' )
                def dir = new File(storagePath+System.getProperty("file.separator") +params.syllabusCourse+System.getProperty("file.separator")+params.syllabusOfSemester+System.getProperty("file.separator") +params.syllabusOfSubject+System.getProperty("file.separator"))
                if ((dir.exists())){
//                    println("Deleting Files")
                    File[] listOfFiles = dir.listFiles();
                    for (File file: listOfFiles){
                    file.delete();
                   }
                }
                else{
                   dir.mkdirs()
               }
//                println(params.int('syllabusOfSubject'))
                def subName = Subject.findAllById(params.int('syllabusOfSubject'))
                fileToBeUploaded.transferTo(new File(dir, fileName))
                println(subName.subjectName)
                def fullPath = new File(storagePath+System.getProperty("file.separator") +params.syllabusCourse+System.getProperty("file.separator")+params.syllabusOfSemester+System.getProperty("file.separator") +params.syllabusOfSubject+System.getProperty("file.separator")+fileName)
                def newFullPath = new File(storagePath+System.getProperty("file.separator") +params.syllabusCourse+System.getProperty("file.separator")+params.syllabusOfSemester+System.getProperty("file.separator") +params.syllabusOfSubject+System.getProperty("file.separator")+subName[0].subjectName+'.'+ext)
//                println(fullPath)
                fullPath.renameTo(newFullPath)
            }
        }
        catch (Exception e){
            flash.message = "There is some problem parsing Document file"
        }

    }




}