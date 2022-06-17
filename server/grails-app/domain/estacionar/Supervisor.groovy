package estacionar
import java.time.LocalTime

class Supervisor {

    String name

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalTime time, Location parkingLocation){
        driver.getReservations().any {it.isFromDriver(driver) && it.isValidAt(time) && it.isValidIn(parkingLocation)}
    }


    Optional<Infringement> createInfringementIfNoReservationFrom(Driver driver, LocalTime time, Location parkingLocation) {
        if (!driverHasReservation(driver, time, parkingLocation) ) {
            Infringement infringement = new Infringement(driver: driver, time: LocalTime.now(), location: parkingLocation, supervisorInCharge: this);
            return Optional.of(infringement);
        } else {
            return Optional.empty();
        }
    }
}
