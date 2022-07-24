package estacionar

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class InspectorServiceSpec extends Specification {

    InspectorService inspectorService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Inspector(...).save(flush: true, failOnError: true)
        //new Inspector(...).save(flush: true, failOnError: true)
        //Inspector inspector = new Inspector(...).save(flush: true, failOnError: true)
        //new Inspector(...).save(flush: true, failOnError: true)
        //new Inspector(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //inspector.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        inspectorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Inspector> inspectorList = inspectorService.list(max: 2, offset: 2)

        then:
        inspectorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        inspectorService.count() == 5
    }

    void "test delete"() {
        Long inspectorId = setupData()

        expect:
        inspectorService.count() == 5

        when:
        inspectorService.delete(inspectorId)
        datastore.currentSession.flush()

        then:
        inspectorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Inspector inspector = new Inspector()
        inspectorService.save(inspector)

        then:
        inspector.id != null
    }
}
