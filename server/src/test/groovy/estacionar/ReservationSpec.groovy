package estacionar

import grails.testing.gorm.DomainUnitTest
import location.Location
import reservationDetails.ReservationDetails
import spock.lang.Specification
import timeFrame.LocalTimeFrame
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import validations.*

class ReservationSpec extends Specification implements DomainUnitTest<Reservation> {

    Driver driver;
    def setup() {
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

    void "a reservation is created on valid location and time"() {
        given: "parking is only available on street 'Siempre Viva' from 0:00AM to 05:00AM"
        LocalTimeFrame availableParkingTimeFrame = new LocalTimeFrame(
                startTime: LocalTime.of(0, 0),
                endTime: LocalTime.of(5, 0))

        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: availableParkingTimeFrame)
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        when: "driver tries to make reservation on 'Siempre Viva' from 04:00AM to 04:30AM"
        Location reservationLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        ReservationDetails details = ReservationDetails.from(
                LocalDateTime.of(2000, 1, 1, 4, 0),
                Duration.ofMinutes(30),
                reservationLocation)

        Reservation reservation = driver.reserveParkingAt(details, parkingValidator);

        then: "reservation from driver is made"
        driver.hasReservation(reservation);
    }

    void "a reservation is not created because of invalid location and time"() {
        given: "parking is only available on street 'Siempre Viva' from 0:00AM to 05:00AM"
        LocalTimeFrame availableParkingTimeFrame = new LocalTimeFrame(
                startTime: LocalTime.of(0, 0),
                endTime: LocalTime.of(5, 0))

        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: availableParkingTimeFrame)
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        when: "driver tries to make reservation on 'Siempre Viva' from 06:00AM to 07:00AM"
        Location reservationLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        ReservationDetails details = ReservationDetails.from(
                LocalDateTime.of(2000, 1, 1, 6, 0),
                Duration.ofMinutes(60),
                reservationLocation)

        Reservation.from(details, parkingValidator)
        then: "reservation from driver is not made and exception is thrown"
        thrown(Exception)
    }

}
