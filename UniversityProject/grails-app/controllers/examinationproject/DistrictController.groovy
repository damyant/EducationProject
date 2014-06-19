package examinationproject


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class DistrictController {

    static allowedMethods = [save: "POST", update: "PUT", get: "GET", delete: "DELETE"]

    def districtList(Integer max) {
//        params.max = Math.min(max ?: 20, 100)
        respond District.list(params), model: [districtInstanceCount: District.count()]
    }


    def createDistrict() {
        respond new District(params)
    }

    @Transactional
    def saveDistrict() {
        println(params)

        if (new District(districtName: params.districtName, stateId: 1).save(flush: true)) {
            flash.message = "District Added SuccessFully "
        } else {
            flash.message = "Unable To District "
        }
        redirect(action: "createDistrict")

    }

    def editDistrict() {
        def districtInstance = District.findById(Integer.parseInt(params.districtId))
        [districtInstance: districtInstance]
    }

    @Transactional
    def updateDistrict() {
        println("params" + params)

        def districtInstance = District.findById(Integer.parseInt(params.districtId))
        districtInstance.districtName = params.districtName
        if (districtInstance.save(flush: true)) {
            flash.message = "District Updated SuccessFully "
        } else {
            flash.message = "Unable To Update District "
        }
        redirect(action: "createDistrict")
    }

    @Transactional
    def deleteDistrict() {
        println("params" + params)
        def districtInstance = District.findById(Integer.parseInt(params.districtId))
        try {
            districtInstance.delete(flush: true)
            flash.message = "District Deleted Successfully "
            redirect(action: "districtList")
        } catch (Exception e) {
            // e.printStackTrace()
            flash.message = "Unable To Delete District "
            redirect(action: "districtList")
        }

    }
}
