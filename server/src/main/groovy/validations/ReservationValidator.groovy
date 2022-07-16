package validations


import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import location.Location
import street.Street
import timeFrame.LocalDateTimeFrame
import timeFrame.LocalTimeFrame

import java.time.LocalTime
import java.time.temporal.ChronoUnit;

@ToString
@EqualsAndHashCode
class ParkingReservationValidator {

    // guarda una lista de calles: {tipo (calle o avenida), nombre, alturas con metrovía o ciclovía, altura con carteles}
    // encontrar la calle en donde se quiere estacionar y verificar si en esa altura se puede
    List<Street> streets


    boolean validTime (LocalTime time) {
        def prohibitedStartTime = LocalTime.of(9, 0)
        def prohibitedEndTime = LocalTime.of(21, 0)
        time <= prohibitedStartTime || time >= prohibitedEndTime
    }


    boolean validTimeFrame(LocalDateTimeFrame timeFrame){
        if (ChronoUnit.HOURS.between(timeFrame.startTime, timeFrame.endTime) > 12){
            return false
        }
        validTime(timeFrame.startTime.toLocalTime()) && validTime(timeFrame.endTime.toLocalTime())
    }

    boolean prohibitsReservationAt(LocalDateTimeFrame timeFrame, Location location) {
        Street street = streets.find { it.name == location.streetName }
        if (street == null) {
            return true
        }


        if (!street.hasSignInNumber(location.streetNumber) && (street.hasBikeLaneInNumber(location.streetNumber) || street.hasBusLaneInNumber(location.streetNumber))) {
            return true
        }
        else {
            if (street.type == Street.Type.AVENUE) {
                return !validTimeFrame(timeFrame)
            }
            return false
        }
    }
}

@Immutable
@ToString
@EqualsAndHashCode
class StreetValidation {
    List<String> streetsToValidate
    LocalTimeFrame availableTimeFrameRightSide
    LocalTimeFrame availableTimeFrameLeftSide

    boolean allowsParkingAtHours(LocalTimeFrame availableTimeFrame, LocalDateTimeFrame requestedTimeFrame){
        availableTimeFrame.startTime <= requestedTimeFrame.startTime.toLocalTime() &&
        availableTimeFrame.endTime >= requestedTimeFrame.endTime.toLocalTime()
    }


    boolean canParkOnLeftSideInTimeFrame(LocalDateTimeFrame timeFrame, Location location){
        location.isLeftSide() && allowsParkingAtHours(availableTimeFrameLeftSide, timeFrame)
    }

    boolean canParkOnRightSideInTimeFrame(LocalDateTimeFrame timeFrame, Location location){
        location.isRightSide() && allowsParkingAtHours(availableTimeFrameRightSide, timeFrame)
    }

    boolean canParkInTimeFrame(LocalDateTimeFrame timeFrame, Location location) {
        canParkOnLeftSideInTimeFrame(timeFrame, location) || canParkOnRightSideInTimeFrame(timeFrame, location)
    }

    boolean prohibitsReservationAt(LocalDateTimeFrame timeFrame, Location location){
        streetsToValidate.contains(location.getStreetName()) && !canParkInTimeFrame(timeFrame, location)
    }
}