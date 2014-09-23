package IST

class Equipment {
    String equipmentName
    int equipmentId
    int labInventoryId
    String equipmentType
    String manufacturer
    Date dateOfPurchase
    String description
    String warranty
    byte[] documentImage

    static constraints = {
        equipmentName(nullable:true)
        equipmentId(nullable:true,unique: true)
        labInventoryId(nullable:true)
        equipmentType(nullable:true)
        manufacturer(nullable:true)
        dateOfPurchase(nullable:true)
        description(nullable:true)
        warranty(nullable:true)
        documentImage(nullable:true)
    }

    static mapping = {
        equipmentName column: 'EquipmentName'
        equipmentId column: 'EquipmentId'
        labInventoryId column: 'LabInventoryId'
        equipmentType column: 'EquipmentType'
        manufacturer column: 'Manufacturer'
        dateOfPurchase column: 'DateOfPurchase'
        description column: 'Description'
        warranty column: 'Warranty'
        documentImage column: 'DocumentImage' ,sqlType: "blob"
    }
}
