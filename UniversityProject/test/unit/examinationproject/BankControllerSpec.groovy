package examinationproject



import grails.test.mixin.*
import spock.lang.*

@TestFor(BankController)
@Mock(Bank)
class BankControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.bankInstanceList
        model.bankInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.bankInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        def bank = new Bank()
        bank.validate()
        controller.save(bank)

        then: "The create view is rendered again with the correct model"
        model.bankInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        bank = new Bank(params)

        controller.save(bank)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/bank/show/1'
        controller.flash.message != null
        Bank.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def bank = new Bank(params)
        controller.show(bank)

        then: "A model is populated containing the domain instance"
        model.bankInstance == bank
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def bank = new Bank(params)
        controller.edit(bank)

        then: "A model is populated containing the domain instance"
        model.bankInstance == bank
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/bank/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def bank = new Bank()
        bank.validate()
        controller.update(bank)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.bankInstance == bank

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        bank = new Bank(params).save(flush: true)
        controller.update(bank)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/bank/show/$bank.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/bank/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def bank = new Bank(params).save(flush: true)

        then: "It exists"
        Bank.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(bank)

        then: "The instance is deleted"
        Bank.count() == 0
        response.redirectedUrl == '/bank/index'
        flash.message != null
    }
}
