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
        location nullable: false, validator: {val, obj -> val.streetNumber != null && val.streetNumber > 0 && val.streetName.length() > 0}
        state nullable: false
    }

    static Reservation from(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator validator, String licensePlate){
        if(timeFrame.startTime < LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)){
            throw new InvalidReservationException("no puedes reservar un estacionamiento en tiempo pasado")
        }

        if(validator.prohibitsReservationAt(timeFrame, location)){
            String errMsg = String.format("No se puede reservar estacionamiento en $location.streetName $location.streetNumber en el horario pedido")
            throw new InvalidReservationException(errMsg)
        }

        if(timeFrame.duration() < Duration.ofMinutes(30)){
            throw new InvalidReservationException("La duración de la reserva debe ser mayor a 30 minutos")
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
