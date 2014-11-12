package examinationproject


import grails.test.mixin.*
import spock.lang.*

@TestFor(DistrictController)
@Mock(District)
class DistrictControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.districtInstanceList
        model.districtInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.districtInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        def district = new District()
        district.validate()
        controller.save(district)

        then: "The create view is rendered again with the correct model"
        model.districtInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        district = new District(params)

        controller.save(district)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/district/show/1'
        controller.flash.message != null
        District.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def district = new District(params)
        controller.show(district)

        then: "A model is populated containing the domain instance"
        model.districtInstance == district
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def district = new District(params)
        controller.edit(district)

        then: "A model is populated containing the domain instance"
        model.districtInstance == district
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/district/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def district = new District()
        district.validate()
        controller.update(district)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.districtInstance == district

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        district = new District(params).save(flush: true)
        controller.update(district)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/district/show/$district.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/district/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def district = new District(params).save(flush: true)

        then: "It exists"
        District.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(district)

        then: "The instance is deleted"
        District.count() == 0
        response.redirectedUrl == '/district/index'
        flash.message != null
    }
}
