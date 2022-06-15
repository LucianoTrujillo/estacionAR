package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ParkingReservationSpec extends Specification implements DomainUnitTest<ParkingReservation> {

    def setup() {
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

    void "test aReservationIsCreatedWithCorrectValues"() {
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

        assert(reservation.isValidIn(parkingLocation) && reservation.isValidAt(timeFrame) && reservation.isFromDriver(driver));
    }

    void "test aReservationCannotBeCreated"() {
        LocalTime start = LocalTime.of(0, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)
        ParkingLocation parkingLocation = new ParkingLocation(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        StreetValidator validator = new StreetValidator(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])

        LocalTime startInvalid = LocalTime.of(6, 0)
        LocalTime endInvalid = LocalTime.of(7, 0)
        TimeFrame timeFrameInvalid = new TimeFrame(startTime: startInvalid, endTime: endInvalid)

        ParkingReservation reservation = driver.reserveParkingAt(parkingLocation, timeFrameInvalid, parkingValidator)
        assertThat(reservation).isEmpty();
    }

}
