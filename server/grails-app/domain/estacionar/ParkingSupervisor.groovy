package estacionar
import java.time.LocalTime
import java.util.Optional

class ParkingSupervisor {

    String name

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalTime dateTime, List<ParkingReservation> dailyReservations, ParkingLocation parkingLocation){
        dailyReservations.any {it.isFromDriver(driver) && it.isValidAt(dateTime) && it.isIn(parkingLocation)}
    }


    public static Optional<Infrigement> validateParkingReservation(Driver driver, LocalTime dateTime, List<ParkingReservation> dailyReservations, ParkingLocation parkingLocation) {
        if (!driverHasReservation(driver, dateTime, dailyReservations, parkingLocation)) {
            Infrigement infrigement = new Infrigement(driver, dateTime, parkingLocation, this);
            return Optional.of(infrigement);
        } else {
            return Optional.empty();
        }
    }



}
