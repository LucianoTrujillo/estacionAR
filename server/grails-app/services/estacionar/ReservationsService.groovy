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

import estacionar.Receipt


class ReservationDetails {
    String startTime
    String endTime
    Location location
}

@Transactional
class ReservationsService {

    ParkingReservationValidator parkingReservationValidator

    Reservation createReservation(int driverId, ReservationDetails details) {

        LocalDateTimeFrame timeFrame =  LocalDateTimeFrame.from(
                LocalDateTime.ofInstant(Instant.parse(details.startTime), ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.ofInstant(Instant.parse(details.endTime), ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES))
        Driver driver = Driver.get(driverId)
        driver.reserveParkingAt(timeFrame, details.location, parkingReservationValidator)
    }

    Receipt payReservation(int driverId, int reservationId) {
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
        Receipt receipt = new Receipt(driverId: driverId, reservationId: reservationId)
        reservation.save()
        receipt.save()
        return receipt
    }

    List<Reservation> getDriverReservations(int driverId) {
        def reservations = Driver.get(driverId).reservations
        reservations.sort {
            a, b ->  a.timeFrame.endTime <=> b.timeFrame.endTime
        }
        return reservations.reverse()
    }

}
