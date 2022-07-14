package validations


import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import location.Location
import timeFrame.LocalDateTimeFrame
import timeFrame.LocalTimeFrame

@ToString
@EqualsAndHashCode
class ParkingReservationValidator {

    List<StreetValidation> streetValidations


    boolean prohibitsReservationAt(LocalDateTimeFrame timeFrame, Location location) {
        streetValidations.any { it.prohibitsReservationAt(timeFrame, location) }
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