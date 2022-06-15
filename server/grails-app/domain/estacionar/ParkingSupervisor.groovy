package estacionar
import java.time.LocalTime
import java.util.Optional

class ParkingSupervisor {

    String name

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalTime dateTime, List<ParkingReservation> dailyReservations, ParkingLocation parkingLocation){
        dailyReservations.any {it.isFromDriver(driver) && it.isValidAt(dateTime) && it.isValidIn(parkingLocation)}
    }


    Optional<ParkingInfringement> validateParkingReservation(Driver driver, LocalTime dateTime, List<ParkingReservation> dailyReservations, ParkingLocation parkingLocation) {
        if (!driverHasReservation(driver, dateTime, dailyReservations, parkingLocation)) {
            ParkingInfringement infringement = new ParkingInfringement(driver: driver, timeOfInfringement: LocalTime.now(), locationOfInfringement: parkingLocation, supervisorInCharge: this);
            return Optional.of(infringement);
        } else {
            return Optional.empty();
        }
    }
}
