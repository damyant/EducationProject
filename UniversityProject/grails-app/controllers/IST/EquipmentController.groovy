package IST

import grails.converters.JSON


class EquipmentController {
    def equipmentService

    def index() {}

    def createEquipment(){
        if(params.id){
            def equipmentObj=Equipment.get(params.id)
            [equipmentObj: equipmentObj]
        }

    }

    def listOfEquipment(){
    def equipmentObjList=Equipment.list()
        [equipmentObjList:equipmentObjList]
    }
    def saveEquipment(){
        def documentImage = request.getFile("documentImage")
        equipmentService.saveEquipmentInformation(params,documentImage)
        redirect(action: "createEquipment")

    }
    def viewEquipmentDetail(){
        def equipmentObj=Equipment.get(params.id)
        [equipmentObj: equipmentObj]
    }

    def deleteEquipment(){
        def returnMap=[:]
        def equipmentObj=Equipment.get(params.equipmentId)
        equipmentObj.delete(failOnError:true,flush: true)
        if(Equipment.exists(params.equipmentId)){
            returnMap.status=false
        }else{
            returnMap.status=true
        }
        render returnMap as JSON

    }

    def showImage(){
        def id= Integer.parseInt(params.id)
        def equipmentObj = Equipment.get(id)
        byte[] image = equipmentObj.documentImage
        response.setContentType(params.mime)
        response.outputStream << image
    }
}
