package estacionar

import grails.gorm.services.Service
import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Service(Inspector)

class InspectorService {

    def createInfringementIfNoReservationFrom(String driverLicensePlate, int inspectorId, Location parkingLocation) {
        Driver driver = Driver.findByLicensePlate(driverLicensePlate)
        Inspector inspector = Inspector.get(inspectorId)
        return inspector.createInfringementIfNoReservationFrom(driver, LocalDateTime.now(), parkingLocation)
    }
}
