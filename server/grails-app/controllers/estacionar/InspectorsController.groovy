package estacionar


import grails.rest.*
import grails.converters.*
import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class InspectorsController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [index: 'GET', test: 'GET', inspect: 'GET']

    InspectorService inspectorService

    def inspect(int inspectorId, String streetName, int streetNumber, String licensePlate) {
        Location location = Location.from(streetName, streetNumber)
        Optional<Infringement> result = inspectorService.createInfringementIfNoReservationFrom(licensePlate, inspectorId, location)
        if(result.isEmpty()) {
            def response = '{"status": "' + "SUCCESS" + '"}'
            render response, status: 200
        }
        else {
            respond result.get(), formats: ['json']
        }
    }

}
