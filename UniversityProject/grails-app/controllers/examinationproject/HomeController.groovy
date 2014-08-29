package examinationproject

import com.university.Role
import com.university.User
import com.university.UserRole
import grails.converters.JSON

class HomeController {
    def springSecurityService
    def sessionRegistry
    def index() {

        if (springSecurityService.getCurrentUser()) {
            def currentUser = springSecurityService.getCurrentUser().getUsername()
            if (UserRole.findByUserAndRole(User.findByUsername(currentUser), Role.findById(7))) {

            } else if (UserRole.findByUserAndRole(User.findByUsername(currentUser), Role.findById(3))) {
                redirect(action: "studyCenterHome")
            }
        } else {
            redirect(action: "defaultIndex")
        }

    }
    def defaultIndex={

    }
    def studyCenterHome={

    }
    def showResults = {
        render(view: "viewResult")
    }
    def selfEnrollStatus={
        def returnMap = [:]
        def programName = [], programCount = []
        def selfEnrollmentCount = Student.findAllByReferenceNumberNotEqual('0').size()
        def rollNoNotCount = Student.findAllByReferenceNumberNotEqualAndRollNoIsNull('0').size()
        def progList = ProgramDetail.list(sort: 'courseCode')
        progList.each {
            Set<ProgramDetail> progInst = ProgramDetail.findAllById(it.id)
            def progId = it.id
            def stuObj = Student.createCriteria()
            def stuProgList = stuObj.list(params) {
                programDetail {
                    eq('id', progId)
                }
                and {
                    ne('referenceNumber', '0')
                }
                isNull("rollNo")
            }
            if(stuProgList.size()>0){
                programCount << stuProgList.size()
                programName << it.courseName
            }
        }
        returnMap.programName=programName
        returnMap.programListSize=programName.size()
        returnMap.programCount=programCount
        returnMap.selfEnrollmentCount=selfEnrollmentCount
        returnMap.rollNoNotCount=rollNoNotCount
        render returnMap as JSON
    }
    def feeChallanStatus={
        def returnMap = [:]
        def studyCentreName = [],studyCentreFeeAppCount = [], studyCentreFeeUnAppCount = []
        def studyCentreList = StudyCenter.list(sort: 'name')
        studyCentreList.each {
            def studyInst = it.id
            def studObj = Student.createCriteria()
            def studList = studObj.list(params) {
                studyCentre {
                    eq('id', studyInst)
                }
            }
            def feeApp = 0, feeUnapprov = 0
            if (studList) {
                studList.each {
                    def studentInst = it
                    def feeObj = FeeDetails.createCriteria()
                    def feeUnApprove = feeObj.list(params) {
                        eq('student', studentInst)
                        ne('isApproved', Status.findById(4))
                    }
                    def feeObj2 = FeeDetails.createCriteria()
                    def feeApprove = feeObj2.list(params) {
                        eq('student', studentInst)
                        eq('isApproved', Status.findById(4))
                    }
                    if (feeUnApprove.size() > 0) {
                        feeUnapprov = feeUnapprov + feeUnApprove.size()
                    }
                    if (feeApprove.size() > 0) {
                        feeApp = feeApp + feeApprove.size()
                    }
                }
            }
            if((feeApp>0)||(feeUnapprov>0)){
                studyCentreName << it.name
                studyCentreFeeAppCount << feeApp
                studyCentreFeeUnAppCount << feeUnapprov
            }
        }
        returnMap.studyCentreName=studyCentreName
        returnMap.studyCentreCount=studyCentreName.size()
        returnMap.studyCentreFeeAppCount=studyCentreFeeAppCount
        returnMap.studyCentreFeeUnAppCount=studyCentreFeeUnAppCount
        render returnMap as JSON
    }
    def studyCentreStudentStatus={
        def returnMap = [:]
        def studyCentreName = [], studyCentreAppCount = [], studyCentreUnAppCount = []
        def studyCentreList = StudyCenter.list(sort: 'name')
        studyCentreList.each {
            def studyInst = it.id

            def stuStudyObj = Student.createCriteria()
            def stuStudyAppList = stuStudyObj.list(params) {
                studyCentre {
                    eq('id', studyInst)
                }
                and {
                    eq('status', Status.findById(4))
                }
            }
            def stuUnStudyObj = Student.createCriteria()
            def stuStudyUnAppList = stuUnStudyObj.list(params) {
                studyCentre {
                    eq('id', studyInst)
                }
                and {
                    ne('status', Status.findById(4))
                }
            }
            if(stuStudyAppList.size()>0 || stuStudyUnAppList.size()>0){
                studyCentreAppCount << stuStudyAppList.size()
                studyCentreUnAppCount << stuStudyUnAppList.size()
                studyCentreName << it.name
            }
        }
        returnMap.studyCentreName=studyCentreName
        returnMap.studyCentreCount=studyCentreName.size()
        returnMap.studyCentreAppCount=studyCentreAppCount
        returnMap.studyCentreUnAppCount=studyCentreUnAppCount
        render returnMap as JSON
    }
}
