package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.LocalTime

class ParkingReservationSpec extends Specification implements DomainUnitTest<ParkingReservation> {

    Driver driver;
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

    void "test aReservationIsCreatedWithCorrectValues: given that the location and timeframe are valid, when a driver wants to make a parking reservation, then it is created"() {
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

        assert(reservation.isValidIn(parkingLocation) && reservation.isValidAt(LocalTime.of(4, 0)) && reservation.isFromDriver(driver));
    }

    void "test aReservationCannotBeCreated: given that the location and timeframe are not valid, when a driver wants to make a parking reservation, then it is not created"() {
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

        Optional<ParkingReservation> reservation = driver.reserveParkingAt(parkingLocation, timeFrameInvalid, parkingValidator)
        assert(!reservation.isPresent());
    }

}
