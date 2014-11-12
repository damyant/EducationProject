package examinationproject



import grails.test.mixin.*
import spock.lang.*

@TestFor(ProgramFeeController)
@Mock(ProgramFee)
class ProgramFeeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.programFeeInstanceList
        model.programFeeInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.programFeeInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        def programFee = new ProgramFee()
        programFee.validate()
        controller.save(programFee)

        then: "The create view is rendered again with the correct model"
        model.programFeeInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        programFee = new ProgramFee(params)

        controller.save(programFee)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/programFee/show/1'
        controller.flash.message != null
        ProgramFee.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def programFee = new ProgramFee(params)
        controller.show(programFee)

        then: "A model is populated containing the domain instance"
        model.programFeeInstance == programFee
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def programFee = new ProgramFee(params)
        controller.edit(programFee)

        then: "A model is populated containing the domain instance"
        model.programFeeInstance == programFee
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/programFee/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def programFee = new ProgramFee()
        programFee.validate()
        controller.update(programFee)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.programFeeInstance == programFee

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        programFee = new ProgramFee(params).save(flush: true)
        controller.update(programFee)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/programFee/show/$programFee.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/programFee/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def programFee = new ProgramFee(params).save(flush: true)

        then: "It exists"
        ProgramFee.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(programFee)

        then: "The instance is deleted"
        ProgramFee.count() == 0
        response.redirectedUrl == '/programFee/index'
        flash.message != null
    }
}
