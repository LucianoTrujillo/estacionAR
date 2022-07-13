package estacionar

import grails.gorm.transactions.Transactional
import location.Location
import reservationDetails.ReservationDetails
import timeFrame.LocalDateTimeFrame
import validations.ParkingReservationValidator

import java.time.LocalDateTime

@Transactional
class ReservationsService {

    ParkingReservationValidator parkingReservationValidator

    def createReservation(int driverId, String startTime, String endTime, Location location) {
        ReservationDetails reservationDetails = ReservationDetails.from(
                LocalDateTimeFrame.from(
                        LocalDateTime.parse(startTime),
                        LocalDateTime.parse(endTime)),
                location
        )

        Driver driver = Driver.get(driverId)
        Reservation reservation = driver.reserveParkingAt(reservationDetails, parkingReservationValidator)
        if(!reservation.save(true)) {
            throw new Exception("Cannot save reservation")
        }
        reservation

    }

}
