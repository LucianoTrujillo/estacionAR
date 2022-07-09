package estacionar

import location.Location
import timeFrame.TimeFrame
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

    static embedded = ['timeFrame', 'location']

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
    PaymentState state


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
            String errMsg = String.format("Cannot reserve parking with requested location {} and timeframe {}", details.location, details.timeFrame);
            throw new Exception(errMsg)
        }

        new Reservation(details: details, state: PaymentState.UNPAID)
    }

    boolean isValidAt(LocalTime time){
        this.details.timeFrame.contains(time)
    }

    boolean isValidIn(Location location){
        this.details.location == location
    }
}
