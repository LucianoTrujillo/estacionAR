package estacionar
import java.time.LocalTime

class ParkingReservation {

    TimeFrame reserveTimeFrame
    ParkingLocation parkingLocation
    Driver driver
    Receipt receipt


    static constraints = {
    }

    static def Optional<Infrigement> from(Driver driver, ParkingLocation location, TimeFrame reserveTimeFrame, ParkingValidator validator){
        if (validator.canMakeReservation(location, reserveTimeFrame)) {
            Receipt receipt = new Receipt(reserveTimeFrame: reserveTimeFrame, parkingLocation: location, driver: driver);
            ParkingReservation newParkingReservation = new ParkingReservation(reserveTimeFrame: reserveTimeFrame, parkingLocation: location, driver: driver, receipt: receipt)
            return Optional.from(newParkingReservation);
        } else {
            return Optional.empty();
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
