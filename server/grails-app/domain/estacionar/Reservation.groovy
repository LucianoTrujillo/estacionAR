package estacionar

import java.time.Duration
import java.time.LocalTime
import validations.ParkingReservationValidator

enum ReservationState {
    Unpaid,
    Paid
}

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
    ReservationState state

    static constraints = {
        details nullable: false
        driver nullable: false
        state nullable: false
    }

    static Reservation from(Driver driver, ParkingReservationValidator validator, ReservationDetails details = ReservationDetails.from(LocalTime.of(0, 0), Duration.ofMinutes(0), new Location(streetName: "", streetNumber: 0))){
        if(validator.prohibitsReservationAt(details))
            throw new Exception("Cannot reserve parking with requested location and timeframe")

        new Reservation(details: details, driver: driver, state: ReservationState.Unpaid)
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
