package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalTime

class ParkingSupervisorSpec extends Specification implements DomainUnitTest<ParkingSupervisor> {

    ParkingSupervisor supervisor
    Driver driver

    def setup() {
        supervisor = new ParkingSupervisor(license: "AAA 000")
        driver = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
    }

    def cleanup() {
    }

    void "supervisor does not create infringement if driver has valid reservation"() {
        given: "driver has reserved parking"
        TimeFrame parkingTimeFrame = new TimeFrame(
                startTime: LocalTime.of(0, 0),
                endTime: LocalTime.of(5, 0))
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: parkingTimeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(streetValidations: [streetValidation])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, parkingTimeFrame, parkingValidator)

        when: "when supervisor verifies reservation"
        Optional<ParkingInfringement> infringement = supervisor.validateParkingReservation(driver, LocalTime.of(3, 0), [reservation], parkingLocation)

        then:"no infringement is created"
        !infringement.isPresent()
    }

    void "supervisor creates infringement if driver has no reservation"() {
        given: "driver has not reserved parking"

        when: "supervisor validates if driver has reservation"
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        ParkingInfringement infringement = supervisor.validateParkingReservation(driver, LocalTime.of(3, 0), [], location).get()

        then:"infringement for driver is created"
        infringement.isFor(driver)
    }



}
