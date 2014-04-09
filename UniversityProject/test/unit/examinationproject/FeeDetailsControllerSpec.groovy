package examinationproject



import grails.test.mixin.*
import spock.lang.*

@TestFor(FeeDetailsController)
@Mock(FeeDetails)
class FeeDetailsControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.feeDetailsInstanceList
        model.feeDetailsInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.feeDetailsInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        def feeDetails = new FeeDetails()
        feeDetails.validate()
        controller.save(feeDetails)

        then: "The create view is rendered again with the correct model"
        model.feeDetailsInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        feeDetails = new FeeDetails(params)

        controller.save(feeDetails)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/feeDetails/show/1'
        controller.flash.message != null
        FeeDetails.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def feeDetails = new FeeDetails(params)
        controller.show(feeDetails)

        then: "A model is populated containing the domain instance"
        model.feeDetailsInstance == feeDetails
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def feeDetails = new FeeDetails(params)
        controller.edit(feeDetails)

        then: "A model is populated containing the domain instance"
        model.feeDetailsInstance == feeDetails
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/feeDetails/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def feeDetails = new FeeDetails()
        feeDetails.validate()
        controller.update(feeDetails)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.feeDetailsInstance == feeDetails

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        feeDetails = new FeeDetails(params).save(flush: true)
        controller.update(feeDetails)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/feeDetails/show/$feeDetails.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/feeDetails/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def feeDetails = new FeeDetails(params).save(flush: true)

        then: "It exists"
        FeeDetails.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(feeDetails)

        then: "The instance is deleted"
        FeeDetails.count() == 0
        response.redirectedUrl == '/feeDetails/index'
        flash.message != null
    }
}
