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
        def marksTypeInstance
        def title
        if(params.marksTypeId){
            marksTypeInstance = MarksType.findById(Integer.parseInt(params.marksTypeId))
            title= "UPDATE MARKS TYPE"
        }else{
            marksTypeInstance = new MarksType()
            title="CREATE MARKS TYPE"
        }

        [marksTypeInstance:marksTypeInstance,title:title]
    }


    def saveMarksType= {
        println("******************"+params)
        def marksType
        if(!params.marksTypeId) {
            marksType = new MarksType(marksTypeName: params.marksTypeName, showValue: true)
            flash.message ="Marks Type Added Successfully"
        }else{
            marksType = MarksType.findById(Integer.parseInt(params.marksTypeId))
            marksType.marksTypeName = params.marksTypeName
            flash.message ="Marks Type Updated Successfully"
        }
        marksType.save(flush: true,failOnError: true)
        redirect(action: "createMarksType")


    }


    def deleteMarksType = {
    flash.message="Unable To Delete Marks Type"
    redirect(action: "marksTypeList")
    }
}
