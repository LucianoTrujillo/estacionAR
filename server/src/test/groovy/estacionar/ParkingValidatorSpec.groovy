package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalTime

class ParkingValidatorSpec extends Specification implements DomainUnitTest<ParkingValidator> {

    def setup() {
    }

    def cleanup() {
    }

    void "test canMakeReservation: given street validator does not validate requested street, when parking validator is asked if a reservation can be made, then return true"() {

        StreetValidator validator = new StreetValidator(restrictingStreets: ["Not Siempre Viva"])
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        LocalTime start = LocalTime.of( 0, 0)
        LocalTime end = LocalTime.of(0, 0)
        TimeFrame dateTime = new TimeFrame(startTime: start, endTime: end)
        expect:"fix me"
            parkingValidator.canMakeReservation(location, dateTime)
    }

    void "test canMakeReservation: given street validator does NOT allow to park in requested street, parking validator is asked if a reservation can be made, then return false"() {
        LocalTime start = LocalTime.of( 0, 0)
        LocalTime end = LocalTime.of( 0, 0)
        TimeFrame dateTimeFrame = new TimeFrame(startTime: start, endTime: end)
        StreetValidator validator = new StreetValidator(restrictingStreets: ["Siempre Viva"], availableTimeFrameRightSide: dateTimeFrame)
        ParkingValidator parkingValidator = new ParkingValidator(validators: [validator])
        ParkingLocation location = new ParkingLocation(streetName: "Siempre Viva", streetNumber: 123)
        start = LocalTime.of( 20, 0)
        end = LocalTime.of(22, 0)
        TimeFrame dateTime = new TimeFrame(startTime: start, endTime: end)
        expect:"fix me"
        !parkingValidator.canMakeReservation(location, dateTime)
    }

}
