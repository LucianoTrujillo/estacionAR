package validations


import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import reservationDetails.ReservationDetails
import timeFrame.LocalDateTimeFrame
import timeFrame.LocalTimeFrame

@ToString
@EqualsAndHashCode
class ParkingReservationValidator {

    List<StreetValidation> streetValidations


    boolean prohibitsReservationAt(ReservationDetails details){
        streetValidations.any { it.prohibitsReservationAt(details) }
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


    boolean canParkOnLeftSideInTimeFrame(ReservationDetails details){
        details.getLocation().isLeftSide() && allowsParkingAtHours(availableTimeFrameLeftSide, details.getTimeFrame())
    }

    boolean canParkOnRightSideInTimeFrame(ReservationDetails details){
        details.getLocation().isRightSide() && allowsParkingAtHours(availableTimeFrameRightSide, details.getTimeFrame())
    }

    boolean canParkInTimeFrame(ReservationDetails details) {
        canParkOnLeftSideInTimeFrame(details) || canParkOnRightSideInTimeFrame(details)
    }

    boolean prohibitsReservationAt(ReservationDetails details){
        streetsToValidate.contains(details.getLocation().getStreetName()) && !canParkInTimeFrame(details)
    }
}