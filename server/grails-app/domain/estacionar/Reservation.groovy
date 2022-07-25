package estacionar

import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.Duration
import java.time.LocalDateTime
import validations.ParkingReservationValidator

import java.time.temporal.ChronoUnit

class Reservation {


    LocalDateTimeFrame timeFrame
    Location location
    PaymentState state
    BigDecimal price
    String licensePlateOfDriver

    static final BigDecimal PRICE_PER_MINUTES = new BigDecimal("2")


    static embedded = ['timeFrame', 'location']

    enum PaymentState {
        UNPAID,
        PAID
    }

     static class InvalidReservationException extends Exception {
         InvalidReservationException(String errorMessage) {
            super(errorMessage);
        }
    }

    static constraints = {
        timeFrame nullable: false
        location nullable: false, validator: {val, obj -> validLocation(val)}
        state nullable: false
    }

    static boolean validLocation(Location location) {
        return location.streetNumber != null && location.streetNumber > 0 && location.streetName.length() > 0
    }

    static Reservation from(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator validator, String licensePlate){

        def validation = validator.validate(timeFrame, location)
        if(validation.isFailure()){
            throw new InvalidReservationException(validation.message)
        }

        new Reservation(
                timeFrame: timeFrame,
                location: location,
                state: PaymentState.UNPAID,
                price: timeFrame.duration().toMinutes() * PRICE_PER_MINUTES,
                licensePlateOfDriver: licensePlate)
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
