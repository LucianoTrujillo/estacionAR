package estacionar

class ParkingReservationValidator {

    List<StreetValidation> streetValidations

    static constraints = {
    }

    boolean prohibitsReservationAt(ParkingLocation location, TimeFrame timeFrame){
        streetValidations.any { it.prohibitsReservationAt(location, timeFrame) }
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

    boolean canParkInTimeFrame(ParkingLocation location, TimeFrame timeFrame) {
        canParkOnLeftSideInTimeFrame(location, timeFrame) || canParkOnRightSideInTimeFrame(location, timeFrame)
    }

    boolean prohibitsReservationAt(ParkingLocation location, TimeFrame timeFrame){
        streetsToValidate.contains(location.streetName) && !canParkInTimeFrame(location, timeFrame)
    }
}