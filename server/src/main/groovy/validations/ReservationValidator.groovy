package validations
import estacionar.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import timeFrame.TimeFrame

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
    TimeFrame availableTimeFrameRightSide
    TimeFrame availableTimeFrameLeftSide


    boolean canParkOnLeftSideInTimeFrame(ReservationDetails details){
        details.getLocation().isLeftSide() && availableTimeFrameLeftSide.contains(details.getTimeFrame())
    }

    boolean canParkOnRightSideInTimeFrame(ReservationDetails details){
        details.getLocation().isRightSide() && availableTimeFrameRightSide.contains(details.getTimeFrame())
    }

    boolean canParkInTimeFrame(ReservationDetails details) {
        canParkOnLeftSideInTimeFrame(details) || canParkOnRightSideInTimeFrame(details)
    }

    boolean prohibitsReservationAt(ReservationDetails details){
        streetsToValidate.contains(details.getLocation().getStreetName()) && !canParkInTimeFrame(details)
    }
}