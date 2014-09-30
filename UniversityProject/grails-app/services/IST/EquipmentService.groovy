package IST

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class EquipmentService {

    def serviceMethod() {

    }

    def saveEquipmentInformation(params, documentImage) {
        if (params.id) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
            def equipmentObj = Equipment.findByEquipmentId(Integer.parseInt(params.equipmentId))
            equipmentObj.equipmentId = Integer.parseInt(params.equipmentId)
            equipmentObj.equipmentName = params.equipmentName
            equipmentObj.equipmentType = params.equipmentType
            equipmentObj.labInventoryId = Integer.parseInt(params.labInventoryId)
            equipmentObj.dateOfPurchase = df.parse(params.dateOfPurchase)
            equipmentObj.manufacturer = params.manufacturer
            equipmentObj.warranty = params.warranty
            equipmentObj.documentImage = documentImage.bytes
            equipmentObj.save(failOnError: true)
        }
        else{
        def equipmentObj = new Equipment(params)
        equipmentObj.documentImage = documentImage.bytes
            equipmentObj.save(failOnError: true)
        }

      }

}
