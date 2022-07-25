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

    InspectorService inspectorService

    def inspect(int inspectorId, Location location, String licensePlate) {
        Optional<Infringement> result = inspectorService.createInfringementIfNoReservationFrom(licensePlate, inspectorId, location)
        if(result.isPresent()) {
            respond result.get(), formats: ['json']
        }
        else {
            def response = '{"status": "' + "SUCCESS" + '"}'
            render response, status: 200
        }
    }

}
