package estacionar
import java.time.LocalTime

class ParkingSupervisor {

    String name

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalTime time, ParkingLocation parkingLocation, List<ParkingReservation> parkingReservationsOfTheDay){
        parkingReservationsOfTheDay.any {it.isFromDriver(driver) && it.isValidAt(time) && it.isValidIn(parkingLocation)}
    }


    Optional<ParkingInfringement> validateDriverHasReservation(Driver driver, LocalTime time, ParkingLocation parkingLocation, List<ParkingReservation> parkingReservationsOfTheDay) {
        if (!driverHasReservation(driver, time, parkingLocation, parkingReservationsOfTheDay) ) {
            ParkingInfringement infringement = new ParkingInfringement(driver: driver, timeOfInfringement: LocalTime.now(), locationOfInfringement: parkingLocation, supervisorInCharge: this);
            return Optional.of(infringement);
        } else {
            return Optional.empty();
        }
    }
}
