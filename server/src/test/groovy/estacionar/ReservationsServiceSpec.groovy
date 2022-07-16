package estacionar

import grails.testing.services.ServiceUnitTest
import location.Location
import spock.lang.Specification
import street.Street
import timeFrame.LocalDateTimeFrame
import timeFrame.LocalTimeFrame
import validations.ParkingReservationValidator

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationsServiceSpec extends Specification implements ServiceUnitTest<ReservationsService>{

    def setup() {
    }

    def cleanup() {
    }

}
