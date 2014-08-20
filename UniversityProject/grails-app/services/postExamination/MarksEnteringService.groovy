package postExamination

import com.sun.org.apache.xerces.internal.parsers.DOMParser
import com.sun.xml.internal.txw2.Document
import com.university.Role
import com.university.TabulatorProgram
import com.university.TabulatorSemester
import com.university.UserRole
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramGroup
import examinationproject.ProgramGroupDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import grails.transaction.Transactional
import groovy.xml.MarkupBuilder
import org.w3c.dom.DOMException
import org.w3c.dom.UserDataHandler
import postexamination.StudentMarks
import javax.xml.parsers.*
import javax.xml.transform.stream.StreamResult
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Transactional
class MarksEnteringService {
    def springSecurityService
    def grailsApplication

    def serviceMethod() {

    }

    def getGroupDetail(params) {
        def programSessionObj = ProgramSession.findByProgramDetailId(ProgramDetail.get(params.program))
        def programGroupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionObj, Semester.get(params.semester))
        return programGroupIns

    }

    def getCourseDetail(params) {
        println('============================='+params)
        def subjectList
        def programDetail = ProgramDetail.findById(params.program)
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))

        if (params.groupType == "0") {
            subjectList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programDetail, semester, programSession).subjectSessionId.subjectId
        } else {
            def programGroupIns = ProgramGroup.findByProgramSessionAndSemester(programSession, semester)
            subjectList = ProgramGroupDetail.findAllByProgramGroupId(programGroupIns).subjectSessionId.subjectId

        }
        return subjectList

    }


    def saveMarks(params) {
        def result = [:]
        try {

            def studentMarksIns
            def programSession = ProgramSession.get(Integer.parseInt(params.session))
            def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
            def currentUser = springSecurityService.currentUser
            def role = UserRole.findAllByUser(currentUser).role
            def role_id
            role.each {
                def tabSemIns=TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRoleAndUser(ProgramDetail.findById(params.program),it,currentUser))
                if(tabSemIns){
                    role_id = tabSemIns.tabulatorProgram.role.id

                }
            }
            if (StudentMarks.findBySubjectIdAndSemesterNoAndStudentAndRoleId(Subject.get(Integer.parseInt(params.subjectId)), Integer.parseInt(params.semester), Student.findByRollNo(params.rollNoId), Role.get(role_id))) {

                studentMarksIns = StudentMarks.findBySubjectIdAndSemesterNoAndStudentAndRoleId(Subject.get(Integer.parseInt(params.subjectId)), Integer.parseInt(params.semester), Student.findByRollNo(params.rollNoId), Role.get(role_id))
                if (Integer.parseInt(params.marksType) == 1) {
                    studentMarksIns.theoryMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 2) {
                    studentMarksIns.practicalMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 3) {
                    studentMarksIns.homeAssignmentMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 4) {
                    studentMarksIns.project = params.marksValue
                }
                studentMarksIns.totalMarks = Integer.parseInt(studentMarksIns.totalMarks) + Integer.parseInt(params.marksValue)
                if (studentMarksIns.save(flush: true, failOnError: true)) {
                    result.status = true
                }
            } else {
                studentMarksIns = new StudentMarks()
                studentMarksIns.subjectId = Subject.get(Integer.parseInt(params.subjectId))
                studentMarksIns.semesterNo = Integer.parseInt(params.semester)
                if (Integer.parseInt(params.marksType) == 1) {
                    studentMarksIns.theoryMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 2) {
                    studentMarksIns.practicalMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 3) {
                    studentMarksIns.homeAssignmentMarks = params.marksValue
                } else if (Integer.parseInt(params.marksType) == 4) {
                    studentMarksIns.project = params.marksValue
                }
                studentMarksIns.totalMarks = params.marksValue
                studentMarksIns.student = Student.findByRollNo(params.rollNoId)
                studentMarksIns.roleId = Role.get(role_id)
                if (studentMarksIns.save(flush: true, failOnError: true)) {
                    result.status = true
                }
            }

        }
        catch (Exception e) {
            println("Exception in saving marks" + e)
            result.status = false
        }
        return result

    }

    def getRollNumbers(params) {
        def stuList1 = []
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        def currentUser = springSecurityService.currentUser
        def role = UserRole.findAllByUser(currentUser).role
        def role_id
        role.each {
            def tabSemIns=TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRoleAndUser(ProgramDetail.findById(params.program),it,currentUser))
            if(tabSemIns){
                role_id = tabSemIns.tabulatorProgram.role.id

            }
        }

        def stuObject = StudentMarks.createCriteria()
        if (Integer.parseInt(params.marksType) == 1) {
            stuList1 = stuObject.list {
                eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
                eq('semesterNo', Integer.parseInt(params.semester))
                eq('roleId', Role.get(role_id))
                isNotNull('theoryMarks')
                order('student', 'asc')
            }.student.rollNo
        } else if (Integer.parseInt(params.marksType) == 2) {
            stuList1 = stuObject.list {
                eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
                eq('semesterNo', Integer.parseInt(params.semester))
                eq('roleId', Role.get(role_id))
                isNotNull('practicalMarks')
                order('student', 'asc')
            }.student.rollNo
        } else if (Integer.parseInt(params.marksType) == 3) {
            stuList1 = stuObject.list {
                eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
                eq('semesterNo', Integer.parseInt(params.semester))
                eq('roleId', Role.get(role_id))
                isNotNull('homeAssignmentMarks')
                order('student', 'asc')
            }.student.rollNo
        } else if (Integer.parseInt(params.marksType) == 4) {
            stuList1 = stuObject.list {
                eq('subjectId', Subject.get(Integer.parseInt(params.subjectId)))
                eq('semesterNo', Integer.parseInt(params.semester))
                eq('roleId', Role.get(role_id))
                isNotNull('project')
                order('student', 'asc')
            }.student.rollNo
        }

        def studentObj = Student.createCriteria()
        def stuList = studentObj.list {
            programDetail {
                eq('id', Long.parseLong(params.program))
            }
            and {
                eq('semester', semester.semesterNo)
                eq('programSession', programSession)
                eq("admitCardGenerated", true)
                eq('status', Status.get(4))
            }
        }.rollNo
//        println("stuList1==="+stuList1)
//        println("stuList==="+stuList)
        stuList1.each {
            if (it) {
                def rollNoIndex = stuList.findIndexOf { it in stuList1 }
                stuList.remove(rollNoIndex)
            }
        }

        return stuList

    }


    def createXMLFile(params) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("web-app/subjectPassMarks/subjectMarksRule.xml");
        Node employee = document.getElementsByTagName("rule").item(0);
        NodeList nodes = employee.getChildNodes();
        Element subject = document.createElement("subject");
        subject.setAttribute('code', params.subjectCode)
        subject.setAttribute('credit', params.creditPoints)
        if (params.theoryTotal != '' || params.theoryTotal != '0') {
            Element theory = document.createElement("theory");
            theory.setAttribute('total', params.theoryTotal)
            theory.setAttribute('minMarks', params.theoryPass)
            subject.appendChild(theory)
        }
        if (params.homeTotal != '' || params.homeTotal != '0') {
            Element home = document.createElement("home");
            home.setAttribute('total', params.homeTotal)
            home.setAttribute('minMarks', params.homePass)
            subject.appendChild(home)
        }
        if (params.practicalTotal != '' || params.practicalTotal != '0') {
            Element practical = document.createElement("practical");
            practical.setAttribute('total', params.practicalTotal)
            practical.setAttribute('minMarks', params.practicalPass)
            subject.appendChild(practical)
        }
        if (params.projectTotal != '' || params.projectTotal != '0') {
            Element project = document.createElement("project");
            project.setAttribute('total', params.projectTotal)
            project.setAttribute('minMarks', params.projectPass)
            subject.appendChild(project)
        }


        def updateStatus=false
        for (int i = 0; i < nodes.getLength(); i++) {
            if(nodes.item(i).getNodeName()!="#text" && updateStatus==false){
                if ((params.subjectCode).equals(nodes.item(i).getAttribute("code"))) {
                    updateStatus=true
                    nodes.item(i).setAttribute('code', params.subjectCode)
                    nodes.item(i).setAttribute('credit', params.creditPoints)
                    for (int j = 0; j < nodes.item(i).getChildNodes().getLength(); j++) {
                        if('theory'.equals(nodes.item(i).item(j).getNodeName())) {
                            nodes.item(i).item(j).setAttribute('total', params.theoryTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.theoryPass)
                        }
                        else if('home'.equals(nodes.item(i).item(j).getNodeName())){
                            nodes.item(i).item(j).setAttribute('total', params.homeTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.homePass)
                        }
                        else if('practical'.equals(nodes.item(i).item(j).getNodeName())){
                            nodes.item(i).item(j).setAttribute('total', params.practicalTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.practicalPass)
                        }
                        else if('project'.equals(nodes.item(i).item(j).getNodeName())){
                            nodes.item(i).item(j).setAttribute('total', params.projectTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.projectPass)
                        }
                    }
                }
            }

        }
        if(!updateStatus){
            employee.appendChild(subject)
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File("web-app/subjectPassMarks/subjectMarksRule.xml"));
        transformer.transform(domSource, streamResult);
    }
}
