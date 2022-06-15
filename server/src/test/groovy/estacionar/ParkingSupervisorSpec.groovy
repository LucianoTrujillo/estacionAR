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

    void "test driverHasReservation: given driver has reserved parking, when supervisor asks if has reservation, then return true "() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(0, 0)
        TimeFrame localDateTimeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(restrictingStreets: ["Siempre Viva"], availableTimeFrameRightSide: localDateTimeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        def parkingReservation = ParkingReservation.from(driver, parkingLocation, localDateTimeFrame, parkingValidator)

        expect:"driver has reservation"
            supervisor.driverHasReservation(driver, LocalTime.of(0, 0), [parkingReservation])
    }


}
