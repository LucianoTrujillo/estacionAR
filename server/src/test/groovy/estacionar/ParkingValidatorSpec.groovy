package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalTime

class ParkingValidatorSpec extends Specification implements DomainUnitTest<ParkingValidator> {

    def setup() {
    }

    def cleanup() {
    }

    void "reservation can be made if parking validator does not know the street"() {
        given: "certain street is not known by parking validator"
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Not Siempre Viva"])
        ParkingValidator parkingValidator = new ParkingValidator(streetValidations: [streetValidation])
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        LocalTime start = LocalTime.of( 0, 0)
        LocalTime end = LocalTime.of(0, 0)
        TimeFrame dateTime = new TimeFrame(startTime: start, endTime: end)

        when: "parking validator is asked if a reservation can be made"
        boolean reservationCanBeMade = parkingValidator.canMakeReservation(location, dateTime)

        then:"reservation can be made at location and time"
        reservationCanBeMade
    }

    void "reservation can not be made if parking validator does not allow parking on requested street and time"() {
        given: "street validator does NOT allow to park on requested street"

        TimeFrame dateTimeFrame = new TimeFrame(
                startTime: LocalTime.of( 0, 0),
                endTime: LocalTime.of( 0, 0))
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: dateTimeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(streetValidations: [streetValidation])
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        TimeFrame dateTime = new TimeFrame(
                startTime: LocalTime.of( 20, 0),
                endTime: LocalTime.of( 22, 0))

        when: "parking validator is asked if a reservation can be made"
        boolean reservationCanBeMade = parkingValidator.canMakeReservation(location, dateTime)

        then:"reservation can not be made at location and time"
        !reservationCanBeMade
    }

    void "reservation can be made if parking validator allows parking on requested street and time"() {
        given: "street validator allows to park on requested street"
        TimeFrame dateTimeFrame = new TimeFrame(
                startTime: LocalTime.of( 0, 0),
                endTime: LocalTime.of( 10, 0))
        StreetValidation streetValidation = new StreetValidation(streetsToValidate: ["Siempre Viva"], availableTimeFrameRightSide: dateTimeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(streetValidations: [streetValidation])
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        TimeFrame dateTime = new TimeFrame(
                startTime: LocalTime.of( 8, 0),
                endTime: LocalTime.of(9, 0))

        when: "parking validator is asked if a reservation can be made"
        boolean reservationCanBeMade = parkingValidator.canMakeReservation(location, dateTime)

        then:"reservation can be made at location and time"
        reservationCanBeMade
    }

}
