package estacionar

import location.Location

import spock.lang.Specification
import street.Street
import timeFrame.LocalDateTimeFrame
import validations.*

import java.time.Duration
import java.time.LocalDateTime

class ReservationValidatorSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "reservation can be made if street does not have a buslane in the asked street number"() {
        given: "the street asked has no buslane in the street number asked"
        def street = Street.from("Siempre Viva", Street.Type.STREET);

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isSuccess()
    }


    void "reservation can not be made if street does have a buslane in the asked street number but not sign"() {
        given: "the street asked has a buslane in the street number asked"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        street.addBusLane(100,200);


        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can not be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }


    void "reservation can be made if street does have a buslane in the asked street number and has sign"() {
        given: "the street asked has a buslane in the street number asked"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        street.addBusLane(100,200);
        street.addSign(150,199);


        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 160)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isSuccess()

    }


    void "reservation can not be made if street does have a buslane in the asked street number and does not has sign in the asked address"() {
        given: "the street asked has a buslane in the street number asked"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        street.addBusLane(100,200);
        street.addSign(150,199);


        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 120)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }



    void "reservation cannot be made if parking validator does not know the street"() {
        given: "certain street is not known by parking validator"
        def street = Street.from("Siempre Viva", Street.Type.STREET);
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streets: [street])

        when: "parking validator is asked if a reservation can be made"
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 4, 0),
                Duration.ofMinutes(30))
        Location reservationLocation = new Location(streetName: "Not Siempre Viva", streetNumber: 123)
        then:"reservation cannot be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }

    void "reservation cannot be made if reservation timeframe is in between the prohibited hours of an avenue (9 am - 21 pm)"() {
        given: "the time asked is in between the prohibited hours, time being 11 am - 14 pm and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 14, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can not be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }

    void "reservation cannot be made if reservation timeframe intersects the prohibited hours of an avenue (9 am - 21 pm)"() {
        given: "the time asked intersects the prohibited hours, time being 8 am - 14 pm and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 8, 0),
                LocalDateTime.of(2023, 1, 1, 14, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can not be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }

    void "reservation can be made if reservation timeframe does not intersect the prohibited hours of an avenue (7 am - 21 pm)"() {
        given: "the time asked doesnt intersect the prohibited hours, time being 1 am - 8 am and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 1, 0),
                LocalDateTime.of(2023, 1, 1, 6, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isSuccess()

    }

    void "reservation can be made if reservation timeframe goes from the night to the other day not intersecting the prohibited hours of an avenue (9 am - 21 pm)"() {
        given: "the time asked doesnt intersect the prohibited hours, time being 22 pm - 3 am and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 22, 0),
                LocalDateTime.of(2023, 1, 2, 3, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isSuccess()

    }

    void "reservation cannot be made if reservation timeframe goes from the night to the other day intersecting the prohibited hours of an avenue (9 am - 21 pm)"() {
        given: "the time asked does intersect the prohibited hours, time being 22 pm - 10 am and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 22, 0),
                LocalDateTime.of(2023, 1, 2, 10, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }

    void "reservation cannot be made if reservation timeframe is grater than 10 hs"() {
        given: "the time asked doesnt intersect the prohibited hours, but its duration is longer than a day, time being 22 pm - 23 pm of the next day and the street asked is an avenue"
        def street = Street.from("Siempre Viva", Street.Type.AVENUE);
        LocalDateTimeFrame timeFrame = LocalDateTimeFrame.from(
                LocalDateTime.of(2023, 1, 1, 23, 0),
                LocalDateTime.of(2023, 1, 2, 22, 0))

        when: "parking validator is asked if a reservation can be made"
        def parkingValidator = new ParkingReservationValidator(streets: [street])
        Location reservationLocation = new Location(streetName: "Siempre Viva", streetNumber: 123)

        then:"reservation can be made at location and time"
        parkingValidator.validate(timeFrame, reservationLocation).isFailure()

    }








}
