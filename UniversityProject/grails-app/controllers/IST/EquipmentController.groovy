package IST

class EquipmentController {

    def index() {}

    def createEquipment(){

    }

    def listOfEquipment(){


    }
    def viewEquipmentDetail(){
        def equipmentObj=Equipment.get(params.id)
        [equipmentObj: equipmentObj]
    }
}
