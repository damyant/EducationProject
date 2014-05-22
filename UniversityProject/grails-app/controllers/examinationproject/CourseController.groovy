package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import universityproject.CourseDetailService
import java.io.File;

@Secured("ROLE_ADMIN")
class CourseController {
    def courseDetailService
    def programFeeService


    def createNewCourse() {
        boolean updateFlag = false
        def courseDetail = [:]
        def subObj = Subject.findAll()
        def programSessions=   programFeeService.getProgramSessions(params)

        println("these are the sessions"+ programSessions)
        if (params.courseSessionId) {
            courseDetail = courseDetailService.getFullDetailOfCourse(params)
            updateFlag = true
        }
//        println(updateFlag)

        [courseDetail: courseDetail as JSON, subjList: subObj as JSON, updateFlag: updateFlag, programSessions:programSessions]
    }


    def viewCourses() {
        def courseDetails = courseDetailService.getFullDetailOfCourse(params)
        [courseDetail: courseDetails as JSON]

    }

    def saveCourse() {
        def response = [:]
        def data = request.JSON
        println("============================="+data);
        try {
            if (data.uploadSyllabus) {
                println("############>>" + data.uploadSyllabus);
                String ext = "";
                def fileToBeUploaded = request.getFile("data.uploadSyllabus")
                println("############>>" + fileToBeUploaded);
                String fileName = fileToBeUploaded.originalFilename
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    ext = fileName.substring(i + 1);
                }
                def servletContext = ServletContextHolder.servletContext
                def storagePath = servletContext.getRealPath('syllabus')
                def dir = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator"))
                println("==============>>" + dir);
                if ((dir.exists())) {
                    File[] listOfFiles = dir.listFiles();
                    for (File file : listOfFiles) {
                        file.delete();
                    }
                } else {
                    dir.mkdirs()
                }

                fileToBeUploaded.transferTo(new File(dir, fileName))
                println("File Saved");
                def fullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + fileName)
                def newFullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + data.courseName + '.' + ext)
                fullPath.renameTo(newFullPath)
            }
        }
        catch (Exception e) {
            println("There is some problem parsing Document file" + e);
        }
        def status = courseDetailService.saveCourseInfo(data)
        response.response1 = status
//        println(response)
        render response as JSON

    }

    def listOfCourses() {

        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        def c = ProgramDetail.createCriteria()
//        def courseList = c.list(params) {
//            order("courseName", "asc")
//        }
//       def courseSessionList=[]
//        println("***********="+courseList)
//        courseList.each{
//            def programSession= ProgramSession.findAllByProgramDetailId(it)
//            if(programSession)
//            courseSessionList.add(programSession)
//        }
        def programList=ProgramSession.list()



        [courseSessionList:programList, courseInstanceTotal: ProgramSession.count()]


    }

    def updateCourses() {

        redirect(action: "listOfCourses", params: ['type': "update"])
    }

    def deleteCourse() {
        courseDetailService.deleteCourse(params)
        redirect(action: "listOfCourses", params: ['type': "update"])
    }

    def uploadSyllabus() {
//        println("params>>>>>>>>>>>>"+params.syllabusFile)
        try {
            if (data.uploadSyllabus) {
                println("############>>" + data.uploadSyllabus);
                String ext = "";
                def fileToBeUploaded = request.getfile('uploadSyllabus')
                println("############>>" + fileToBeUploaded);
                String fileName = fileToBeUploaded.originalFilename
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    ext = fileName.substring(i + 1);
                }
                def servletContext = ServletContextHolder.servletContext
                def storagePath = servletContext.getRealPath('syllabus')
                def dir = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator"))
                println("==============>>" + dir);
                if ((dir.exists())) {
                    File[] listOfFiles = dir.listFiles();
                    for (File file : listOfFiles) {
                        file.delete();
                    }
                } else {
                    dir.mkdirs()
                }

                fileToBeUploaded.transferTo(new File(dir, fileName))
                println("File Saved");
                def fullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + fileName)
                def newFullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + data.courseName + '.' + ext)
                fullPath.renameTo(newFullPath)
            }
        }
        catch (Exception e) {
            println("There is some problem parsing Document file" + e);
        }

    }

    def checkCourseCode() {
        def status = [:]
        def courseCodeIns = ProgramDetail.findAllByCourseCode(params.courseCode)
        if (courseCodeIns) {
            status.courseCode = 'true'
        } else {
            status.courseCode = 'false'
        }
        render status as JSON

    }
}