package estacionar

import grails.gorm.transactions.Transactional
import location.Location

import timeFrame.LocalDateTimeFrame
import validations.ParkingReservationValidator
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Transactional
class ReservationsService {

    ParkingReservationValidator parkingReservationValidator

    def createReservation(int driverId, String startTime, String endTime, Location location) {

        LocalDateTimeFrame timeFrame =  LocalDateTimeFrame.from(
                LocalDateTime.ofInstant(Instant.parse(startTime), ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.ofInstant(Instant.parse(endTime), ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES))
        Driver driver = Driver.get(driverId)
        Reservation reservation = driver.reserveParkingAt(timeFrame, location, parkingReservationValidator)
        reservation
    }

    def payReservation(int driverId, int reservationId) {
        Driver driver = Driver.get(driverId)
        Reservation reservation = Reservation.get(reservationId)
        if(!driver.hasReservation(reservation)){
            throw new Reservation.InvalidReservationException("El conductor no tiene esa reserva")
        }

        if (reservation.timeFrame.startTime > LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)) {
            throw new Reservation.InvalidReservationException("no puedes pagar una reserva que no ha comenzado")
        }

        long minutes = ChronoUnit.MINUTES.between(reservation.timeFrame.startTime, LocalDateTime.now())
        reservation.price = new BigDecimal(minutes * Reservation.PRICE_PER_MINUTES)
        reservation.state = Reservation.PaymentState.PAID
        reservation.timeFrame.endTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        reservation.save()
        return reservation
    }

}
