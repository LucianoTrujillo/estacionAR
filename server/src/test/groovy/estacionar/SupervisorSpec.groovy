package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import validations.*

import java.time.Duration
import java.time.LocalTime

class SupervisorSpec extends Specification implements DomainUnitTest<Supervisor> {

    Supervisor supervisor
    Driver driver

    def setup() {
        supervisor = new Supervisor(license: "AAA 000")
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
        TimeFrame parkingTimeFrame = new TimeFrame(
                startTime: LocalTime.of(0, 0),
                endTime: LocalTime.of(5, 0))
        Location parkingLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: parkingTimeFrame, availableTimeFrameLeftSide: parkingTimeFrame)
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        given: "driver has reserved parking"
        ReservationDetails details = ReservationDetails.from(
                LocalTime.of(0, 0),
                Duration.ofMinutes(30),
                new Location(streetName: "Siempre Viva", streetNumber: 123))
        driver.reserveParkingAt(details, parkingValidator)

        when: "when supervisor verifies reservation"
        Optional<Infringement> infringement = supervisor.createInfringementIfNoReservationFrom(driver, LocalTime.of(0, 10), parkingLocation)

        then:"no infringement is created"
        !infringement.isPresent()
    }

    void "supervisor creates infringement if driver has no reservation"() {
        given: "driver has not reserved parking"

        when: "supervisor validates if driver has reservation"
        Location location = new Location(streetName: "Siempre Viva", streetNumber: 123)
        Infringement infringement = supervisor.createInfringementIfNoReservationFrom(driver, LocalTime.of(3, 0), location).get()

        then:"infringement for driver is created"
        infringement.isFor(driver)
    }



}
