package postexamination

import examinationproject.Bank

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MarksTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def marksTypeList(Integer max) {
        def marksTypeList=null
      if(MarksType.count()>0){
          marksTypeList= MarksType.list()
      }else{
          flash.message = "No Marks Type Found"
      }
      [marksTypeList:marksTypeList]
    }


    def createMarksType() {
        def marksTypeInstance = new MarksType()
        [marksTypeInstance:marksTypeInstance]
    }


    def saveMarksType= {
        println("******************"+params)

        def marksType = new MarksType(marksTypeName: params.marksTypeName,showValue: true)

        if(marksType.save(flush: true,failOnError: true)){
            flash.message ="MarksType Added Successfully"
        }else{
            flash.message ="Unable To Add MarksType"
        }
        redirect(action: "createMarksType")


    }

    def editMarksType = {
        def marksTypeInstance = MarksType.findById(Integer.parseInt(params.marksTypeId))
        [marksTypeInstance:marksTypeInstance]
    }

    @Transactional
    def updateMarksType(MarksType marksTypeInstance) {

    }

    @Transactional
    def deleteMarksType(MarksType marksTypeInstance) {


    }
}
