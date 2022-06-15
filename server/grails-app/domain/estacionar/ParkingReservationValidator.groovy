package estacionar

import java.time.LocalTime

class ParkingReservationValidator {

    List<StreetValidation> streetValidations

    static constraints = {
    }

    boolean reservationCanBeMadeFrom(ParkingLocation location, TimeFrame timeFrame){
        !streetValidations.any { it.canNotMakeReservationFrom(location, timeFrame) }
    }

    boolean reservationCanExistsAt(ParkingLocation location, LocalTime time){
        !streetValidations.any { it.canNotExistReservationAt(location, time) }
    }
}


class StreetValidation {
    List<String> streetsToValidate
    TimeFrame availableTimeFrameRightSide
    TimeFrame availableTimeFrameLeftSide

    boolean canParkOnLeftSideInTimeFrame(ParkingLocation location, TimeFrame timeFrame){
        location.isLeftSide() && availableTimeFrameLeftSide.contains(timeFrame)
    }

    boolean canParkOnRightSideInTimeFrame(ParkingLocation location, TimeFrame timeFrame){
        location.isRightSide() && availableTimeFrameRightSide.contains(timeFrame)
    }

    boolean canBeParkedOnLeftAt(ParkingLocation location, LocalTime time){
        location.isLeftSide() && availableTimeFrameLeftSide.contains(time)
    }

    boolean canParkOnRightSideAt(ParkingLocation location, LocalTime time){
        location.isRightSide() && availableTimeFrameRightSide.contains(time)
    }

    boolean canParkInTimeFrame(ParkingLocation location, TimeFrame timeFrame) {
        canParkOnLeftSideInTimeFrame(location, timeFrame) || canParkOnRightSideInTimeFrame(location, timeFrame)
    }

    boolean canBeParkedAtTime(ParkingLocation location, LocalTime time) {
        canBeParkedOnLeftAt(location, time) || canParkOnRightSideAt(location, time)
    }

    boolean canNotMakeReservationFrom(ParkingLocation location, TimeFrame timeFrame){
        streetsToValidate.contains(location.streetName) && !canParkInTimeFrame(location, timeFrame)
    }

    boolean canNotExistReservationAt(ParkingLocation location, LocalTime time){
        streetsToValidate.contains(location.streetName) && !canBeParkedAtTime(location, time)
    }
}