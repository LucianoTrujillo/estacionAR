package estacionar

import grails.testing.gorm.DomainUnitTest
import location.Location

import spock.lang.Specification
import street.Street
import timeFrame.LocalDateTimeFrame
import java.time.Duration
import java.time.LocalDateTime
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
        def street = Street.from("Siempre Viva", Street.Type.STREET);

        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streets: [street])

        when: "driver tries to make reservation on 'Siempre Viva' from 04:00AM to 04:30AM"
        Location reservationLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))

        def reservation = driver.reserveParkingAt(timeFrame, reservationLocation, parkingValidator)

        then: "reservation from driver is made"
        driver.hasReservation(reservation);
    }

    void "driver cannot make reservation if already has one intersecting with the timeFrame"() {
        given: "driver already has a reservation from 04:00AM to 04:30AM"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streets: [street])

        Location reservationLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 00),
                LocalDateTime.of(2023, 1, 1, 4, 30))
        driver.reserveParkingAt(timeFrame, reservationLocation, parkingValidator)

        when: "driver tries to make reservation from 04:20AM to 04:50AM"
        LocalDateTimeFrame newTimeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 20),
                LocalDateTime.of(2023, 1, 1, 4, 30))
        driver.reserveParkingAt(newTimeFrame, reservationLocation, parkingValidator)

        then: "an exception is thrown"
        Exception e = thrown()
        'Ya tienes una reserva en este horario' == e.message

    }

    void "driver can make reservation if same place but not intersecting timefrimes"() {
        given: "driver already has a reservation from 04:00AM to 04:30AM in Siempre viva"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(
                streetName: "Siempre Viva",
                streetNumber: 123
        )
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 00),
                LocalDateTime.of(2023, 1, 1, 4, 30))
        driver.reserveParkingAt(timeFrame, reservationLocation, parkingValidator)

        when: "driver tries to make reservation from 04:20AM to 04:30AM of the other day"
        LocalDateTimeFrame newTimeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 2, 4, 20),
                LocalDateTime.of(2023, 1, 2, 4, 50))
        def reservation = driver.reserveParkingAt(newTimeFrame, reservationLocation, parkingValidator)

        then: "reservation from driver is made"
        driver.hasReservation(reservation);

    }


}
