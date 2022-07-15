package estacionar

import grails.gorm.transactions.Transactional
import location.Location

import timeFrame.LocalDateTimeFrame
import validations.ParkingReservationValidator
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Transactional
class ReservationsService {

    ParkingReservationValidator parkingReservationValidator

    def createReservation(int driverId, String startTime, String endTime, Location location) {

        LocalDateTimeFrame timeFrame =  LocalDateTimeFrame.from(
                LocalDateTime.ofInstant(Instant.parse(startTime), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.parse(endTime), ZoneId.systemDefault()))
        Driver driver = Driver.get(driverId)
        Reservation reservation = driver.reserveParkingAt(timeFrame, location, parkingReservationValidator)
        reservation
    }

    def payReservation(int driverId, int reservationId) {
        Driver driver = Driver.get(driverId)
        Reservation reservation = Reservation.get(reservationId)
        if(!driver.hasReservation(reservation)){
            throw new Exception("El conductor no tiene esa reserva")
        }
        reservation.state = Reservation.PaymentState.PAID
        reservation.save()
        return reservation
    }

}
