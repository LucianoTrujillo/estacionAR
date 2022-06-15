package estacionar

class ParkingValidator {

    List<StreetValidator> validators

    static constraints = {
    }

    boolean canMakeReservation(ParkingLocation location, TimeFrame timeFrame){
        !validators.any { !it.isValid(location, timeFrame) }
    }
}


class StreetValidator {
    List<String> restrictingStreets
    TimeFrame availableTimeFrameRightSide
    TimeFrame availableTimeFrameLeftSide

    boolean canParkInSideAtTime(ParkingLocation.LocationSide side, TimeFrame timeFrame) {
        if(side == ParkingLocation.LocationSide.LEFT && !availableTimeFrameLeftSide.contains(timeFrame)) {
            return false
        }
        if(side == ParkingLocation.LocationSide.RIGHT && !availableTimeFrameRightSide.contains(timeFrame)) {
            return false
        }

        true
    }

    boolean isValid(ParkingLocation location, TimeFrame timeFrame){
        !restrictingStreets.contains(location.streetName) || canParkInSideAtTime(location.getSide(), timeFrame)
    }
}