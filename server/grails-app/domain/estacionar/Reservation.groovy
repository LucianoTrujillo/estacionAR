package estacionar

import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.LocalDateTime
import validations.ParkingReservationValidator

class Reservation {


    LocalDateTimeFrame timeFrame
    Location location
    PaymentState state

    static embedded = ['timeFrame', 'location']

    enum PaymentState {
        UNPAID,
        PAID
    }

    static constraints = {
        timeFrame nullable: false
        location nullable: false
        state nullable: false
    }

    static Reservation from(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator validator){
        if(validator.prohibitsReservationAt(timeFrame, location)){
            String errMsg = String.format("Cannot reserve parking with requested location {} and timeframe {}", location, timeFrame)
            throw new Exception(errMsg)
        }

        new Reservation(timeFrame: timeFrame, location: location, state: PaymentState.UNPAID)
    }

    boolean isValidAt(LocalDateTime time){
        this.timeFrame.contains(time)
    }

    boolean isValidIn(Location location){
        this.location == location
    }

    String toString(){
        return String.format("Reservation(time frame: %s, location: %s, state: %s)", timeFrame.toString(), location.toString(), state)
    }
}
