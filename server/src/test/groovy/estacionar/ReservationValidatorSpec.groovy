package estacionar

import location.Location
import reservationDetails.ReservationDetails
import spock.lang.Specification
import timeFrame.LocalTimeFrame
import validations.*

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationValidatorSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "reservation can be made if parking validator does not know the street"() {
        given: "certain street is not known by parking validator"
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Not Siempre Viva"])
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        when: "parking validator is asked if a reservation can be made"
        ReservationDetails details = ReservationDetails.from(
                LocalDateTime.of(2000, 1, 1, 4, 0),
                Duration.ofMinutes(0),
                new Location(streetName: "Siempre Viva", streetNumber: 123))
        boolean reservationCanBeMade = !parkingValidator.prohibitsReservationAt(details)

        then:"reservation can be made at location and time"
        reservationCanBeMade
    }

    void "reservation can not be made if parking validator does not allow parking on requested street and time"() {
        given: "street validator does NOT allow to park on requested street"
        LocalTimeFrame timeFrame = new LocalTimeFrame(
                startTime: LocalTime.of( 0, 0),
                endTime: LocalTime.of( 0, 0))
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        when: "parking validator is asked if a reservation can be made"
        ReservationDetails details = ReservationDetails.from(
                LocalDateTime.of(2000, 1, 1, 4, 0),
                Duration.ofMinutes(120),
                new Location(streetName: "Siempre Viva", streetNumber: 123))
        boolean reservationCanBeMade = !parkingValidator.prohibitsReservationAt(details)

        then:"reservation can not be made at location and time"
        !reservationCanBeMade
    }

    void "reservation can be made if parking validator allows parking on requested street and time"() {
        given: "street validator allows to park on requested street"
        LocalTimeFrame timeFrame = new LocalTimeFrame(
                startTime: LocalTime.of( 0, 0),
                endTime: LocalTime.of( 23, 0))
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: timeFrame)
        ParkingReservationValidator parkingValidator = new ParkingReservationValidator(streetValidations: [streetValidation])

        when: "parking validator is asked if a reservation can be made"
        ReservationDetails details = ReservationDetails.from(
                LocalDateTime.of(2000, 1, 1, 20, 0),
                Duration.ofMinutes(30),
                new Location(streetName: "Siempre Viva", streetNumber: 123))
        boolean reservationCanBeMade = !parkingValidator.prohibitsReservationAt(details)

        then:"reservation can be made at location and time"
        reservationCanBeMade
    }

}
