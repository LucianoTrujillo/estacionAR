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
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator).get()
        expect:"driver has reservation"
            supervisor.driverHasReservation(driver, LocalTime.of(3, 0), [reservation], parkingLocation)
    }

    void "test driverDoesntHaveReservation given driver doesn't have reserved parking, when supervisor asks if has reservation, then return false"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator).get()

        Driver driverThatDidNotReserveParking = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
        expect:"driver has reservation"
        !supervisor.driverHasReservation(driverThatDidNotReserveParking, LocalTime.of(3, 0), [reservation], parkingLocation)
    }

    void "test infractionIsNotGenerated given driver has reserved parking, when supervisor verifies reservation, then no infraction is created"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator).get()

        ParkingInfringement infringement = supervisor.validateParkingReservation(driver, LocalTime.of(3, 0), [reservation], parkingLocation).get()
        assert(infringement.isFor(driver));
    }

    void "test infractionIsNotGenerated given driver has not reserved parking, when supervisor verifies reservation, then infraction is created"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrame, parkingValidator).get()

        Driver driverThatDidNotReserveParking = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
        ParkingInfringement infringement = supervisor.validateParkingReservation(driverThatDidNotReserveParking, LocalTime.of(3, 0), [reservation], parkingLocation).get()
        assert(infringement.isFor(driverThatDidNotReserveParking));
    }



}
