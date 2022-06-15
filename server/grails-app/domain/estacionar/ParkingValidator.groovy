package estacionar

class ParkingValidator {

    List<StreetValidator> validators

    static constraints = {
    }

    boolean canMakeReservation(ParkingLocation location, TimeFrame timeFrame){
        !validators.any { it.failsValidation(location, timeFrame) }
    }
}


class StreetValidator {
    List<String> streetsToValidate
    TimeFrame availableTimeFrameRightSide
    TimeFrame availableTimeFrameLeftSide

    boolean canParkOnLeftSide(ParkingLocation location, TimeFrame timeFrame){
        location.isLeftSide() && availableTimeFrameLeftSide.contains(timeFrame)
    }

    boolean canParkOnRightSide(ParkingLocation location, TimeFrame timeFrame){
        location.isRightSide() && availableTimeFrameRightSide.contains(timeFrame)
    }

    boolean canParkAtTime(ParkingLocation location, TimeFrame timeFrame) {
        canParkOnLeftSide(location, timeFrame) || canParkOnRightSide(location, timeFrame)
    }

    boolean failsValidation(ParkingLocation location, TimeFrame timeFrame){
        streetsToValidate.contains(location.streetName) && !canParkAtTime(location, timeFrame)
    }
}