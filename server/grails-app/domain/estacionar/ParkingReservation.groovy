package estacionar
import java.time.LocalTime

class ParkingReservation {

    TimeFrame reserveTimeFrame
    ParkingLocation parkingLocation
    Driver driver

    static constraints = {
    }

    static def from(Driver driver, ParkingLocation location, TimeFrame reserveTimeFrame, ParkingValidator validator){
        if (validator.canMakeReservation(location, reserveTimeFrame)) {
            new ParkingReservation(reserveTimeFrame: reserveTimeFrame, parkingLocation: location, driver: driver)
        }
    }

    boolean isFromDriver(Driver driver){
        this.driver == driver
    }

    boolean notExpiredAt(LocalTime dateTime){
        reserveTimeFrame.contains(dateTime)
    }
}
