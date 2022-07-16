package estacionar

import grails.testing.gorm.DomainUnitTest
import location.Location

import spock.lang.Specification
import street.Street
import timeFrame.LocalDateTimeFrame
import timeFrame.LocalTimeFrame
import validations.*

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class InspectorSpec extends Specification implements DomainUnitTest<Inspector> {

    Inspector supervisor
    Driver driver

    def setup() {
        supervisor = new Inspector(license: "AAA 000")
        driver = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111",
                reservations: []
        )
    }

    def cleanup() {
    }

    void "supervisor does not create infringement if driver has valid reservation"() {
        LocalTimeFrame parkingTimeFrame = new LocalTimeFrame(
                startTime: LocalTime.of(0, 0),
                endTime: LocalTime.of(5, 0))
        Location parkingLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        def street = Street.from("Siempre Viva", Street.Type.STREET);

        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streets: [street])
        given: "driver has reserved parking"
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2000, 1, 1, 0, 0),
                Duration.ofMinutes(30))

        driver.reserveParkingAt(timeFrame, new Location(streetName: "Siempre Viva", streetNumber: 123), parkingValidator)

        when: "when supervisor verifies reservation"
        Optional<Infringement> infringement = supervisor.createInfringementIfNoReservationFrom(driver,
                LocalDateTime.of(2000, 1, 1, 0, 0)
                , parkingLocation)

        then:"no infringement is created"
        !infringement.isPresent()
    }

    void "supervisor creates infringement if driver has no reservation"() {
        given: "driver has not reserved parking"

        when: "supervisor validates if driver has reservation"
        Location location = new Location(streetName: "Siempre Viva", streetNumber: 123)
        Infringement infringement = supervisor.createInfringementIfNoReservationFrom(driver,
                LocalDateTime.of(2000, 1, 1, 3, 0),
                location).get()

        then:"infringement for driver is created"
        infringement.isFor(driver)
    }



}
