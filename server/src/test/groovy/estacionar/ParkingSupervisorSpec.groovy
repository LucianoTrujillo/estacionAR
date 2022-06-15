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
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator)
        expect:"driver has reservation"
            supervisor.driverHasReservation(driver, LocalTime.of(3, 0), [reservation])
    }

    void "test driverDoesntHaveReservation: given driver doesn't have reserved parking, when supervisor asks if has reservation, then return false"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator)

        Driver driverThatDidNotReserveParking = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
        expect:"driver has reservation"
        !supervisor.driverHasReservation(driverThatDidNotReserveParking, LocalTime.of(3, 0), [reservation])
    }

    void "test infractionIsNotGenerated: given driver has reserved parking, when supervisor verifies reservation, then no infraction is created"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator)

        Driver driverThatDidNotReserveParking = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
        Infrigement infrigement = supervisor.validateParkingReservation(driver, LocalTime.of(3, 0), [reservation])
        assertThat(infrigement).isNotEmpty();
        assert(infrigement.value == 1000);

    }



}
