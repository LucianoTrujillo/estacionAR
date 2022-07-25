package validations


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import location.Location
import street.Street
import timeFrame.LocalDateTimeFrame

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit;

class Validation {
    Status status
    String message

    enum Status {
        SUCCESS,
        ERROR
    }

    boolean isFailure() {
        return status == Status.ERROR
    }

    boolean isSuccess() {
        return status == Status.SUCCESS
    }
}

@ToString
@EqualsAndHashCode
class ParkingReservationValidator {

    List<Street> streets

    boolean validAvenueTimeFrame(LocalTime time) {
        def prohibitedStartTime = LocalTime.of(7, 0)
        def prohibitedEndTime = LocalTime.of(21, 0)
        time <= prohibitedStartTime || time >= prohibitedEndTime
    }


    boolean validAvenueTimeFrame(LocalDateTimeFrame timeFrame){
        if (ChronoUnit.HOURS.between(timeFrame.startTime, timeFrame.endTime) > 10){
            return false
        }
        validAvenueTimeFrame(timeFrame.startTime.toLocalTime()) && validAvenueTimeFrame(timeFrame.endTime.toLocalTime())
    }


    Validation validate(LocalDateTimeFrame timeFrame, Location location) {
        if(timeFrame.startTime < LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)){
            return failedValidation("no puedes reservar un estacionamiento cuyo inicio está en el pasado")
        }

        if(timeFrame.duration() < Duration.ofMinutes(10)){
            return failedValidation("la duración de la reserva debe ser mayor a 10 minutos")
        }

        if(location.streetName == null || location.streetNumber == null || location.streetNumber < 1 || location.streetName.length() == 0) {
            return failedValidation("la ubicación tiene que tener una calle y una altura mayor que 0")
        }

        Street street = streets.find { it.name == location.streetName }
        if (street == null) {
            return failedValidation("La calle ingresada no se encuentra en el sistema")
        }


        if (!street.hasSignInNumber(location.streetNumber) && (street.hasBikeLaneInNumber(location.streetNumber) || street.hasBusLaneInNumber(location.streetNumber))) {
            return failedValidation("En la ubicación " + location.streetName + " " + location.streetNumber + " se encuentra una ciclovía o metrobus y no hay cartel que permita estacionar")
        }

        if (street.type == Street.Type.AVENUE && !validAvenueTimeFrame(timeFrame)) {
                return failedValidation("En la avenida " + location.streetName + " no se puede estacionar de 7 AM a 21 PM")
        }

        succeedsValidation()
    }

    static Validation failedValidation(String errMsg) {
        new Validation(status: Validation.Status.ERROR, message: errMsg)
    }

    static Validation succeedsValidation() {
        new Validation(status: Validation.Status.SUCCESS)
    }
}