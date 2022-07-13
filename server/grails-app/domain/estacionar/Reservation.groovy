package estacionar

import location.Location
import reservationDetails.ReservationDetails
import timeFrame.LocalDateTimeFrame

import java.time.LocalDateTime
import validations.ParkingReservationValidator

class Reservation {

    ReservationDetails details
    PaymentState state

    static embedded = ['details', 'state']

    enum PaymentState {
        UNPAID,
        PAID
    }

    static constraints = {
        details nullable: false
        state nullable: false
    }

    static Reservation from(ReservationDetails details, ParkingReservationValidator validator){
        if(validator.prohibitsReservationAt(details)){
            String errMsg = String.format("Cannot reserve parking with requested location {} and timeframe {}", details.location, details.timeFrame)
            throw new Exception(errMsg)
        }

        new Reservation(details: details, state: PaymentState.UNPAID)
    }

    boolean isValidAt(LocalDateTime time){
        this.details.timeFrame.contains(time)
    }

    boolean isValidIn(Location location){
        this.details.location == location
    }

    String toString(){
        return String.format("Reservation(details: %s, state: %s)", this.details, this.state)
    }
}
