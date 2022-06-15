package estacionar
import java.time.LocalTime

class ParkingReservation {

    TimeFrame reserveTimeFrame
    ParkingLocation parkingLocation
    Driver driver
    ParkingReceipt receipt


    static constraints = {
    }

    static ParkingReservation from(Driver driver, ParkingLocation location, TimeFrame reserveTimeFrame, ParkingValidator validator){
        if (validator.canMakeReservation(location, reserveTimeFrame)) {
            ParkingReservation newParkingReservation = new ParkingReservation(reserveTimeFrame: reserveTimeFrame, parkingLocation: location, driver: driver)
            return newParkingReservation;
        } else {
            throw new Exception("Cannot reserve parking with requested location and timeframe")
        }
    }

    boolean isFromDriver(Driver driver){
        this.driver == driver // Tiene ID null, averiguar por que
    }

    boolean isValidAt(LocalTime dateTime){
        reserveTimeFrame.contains(dateTime)
    }

    boolean isValidIn(ParkingLocation location){
        this.parkingLocation == location
    }
}
