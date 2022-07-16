package estacionar

import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.LocalDateTime
import validations.ParkingReservationValidator

class Reservation {


    LocalDateTimeFrame timeFrame
    Location location
    PaymentState state
    BigDecimal price

    static embedded = ['timeFrame', 'location']

    enum PaymentState {
        UNPAID,
        PAID
    }

    static constraints = {
        timeFrame nullable: false
        location nullable: false, validator: {val, obj -> val.streetNumber != null && val.streetNumber > 0 && val.streetName.length() > 0}
        state nullable: false
    }

    static Reservation from(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator validator){
        if(validator.prohibitsReservationAt(timeFrame, location)){
            String errMsg = String.format("No se puede reservar estacionamiento en $location.streetName $location.streetNumber en el horario pedido")
            throw new Exception(errMsg)
        }

        new Reservation(timeFrame: timeFrame, location: location, state: PaymentState.UNPAID, price: 0)
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
