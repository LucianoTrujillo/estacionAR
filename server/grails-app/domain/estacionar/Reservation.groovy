package estacionar

import java.time.Duration
import java.time.LocalTime
import validations.ParkingReservationValidator


class ReservationDetails {
    TimeFrame timeFrame
    Location location

    static constraints = {
        timeFrame nullable: false
        location nullable: false
    }

    static ReservationDetails from(LocalTime startTime, Duration duration, Location location){
        new ReservationDetails(
                timeFrame: new TimeFrame(
                        startTime: startTime,
                        endTime: startTime + duration
                ),
                location: location
        )
    }
}

class Reservation {

    ReservationDetails details
    Driver driver
    PaymentState state


    enum PaymentState {
        UNPAID,
        PAID
    }

    static constraints = {
        details nullable: false
        driver nullable: false, insert: false, update: false
        state nullable: false
    }

    static Reservation from(Driver driver, ReservationDetails details, ParkingReservationValidator validator){
        if(validator.prohibitsReservationAt(details))
            // agregar detalles del conductor y la reserva
            throw new Exception("Cannot reserve parking with requested location and timeframe")

        new Reservation(details: details, driver: driver, state: PaymentState.UNPAID)
    }

    boolean isFromDriver(Driver driver){
        this.driver == driver
    }

    boolean isValidAt(LocalTime time){
        this.details.timeFrame.contains(time)
    }

    boolean isValidIn(Location location){
        this.details.location == location
    }
}
