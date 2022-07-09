package estacionar
import java.time.LocalTime

class Inspector {

    String name

    static constraints = {

    }

    private boolean driverHasReservation(Driver driver, LocalTime time, Location parkingLocation){
        driver.reservations.any {it.isFromDriver(driver) && it.isValidAt(time) && it.isValidIn(parkingLocation)}
    }


    Optional<Infringement> createInfringementIfNoReservationFrom(Driver driver, LocalTime time, Location parkingLocation) {
        if (driverHasReservation(driver, time, parkingLocation) ) {
            return Optional.empty()
        }

        Infringement infringement = new Infringement(driver: driver, time: LocalTime.now(), location: parkingLocation, supervisorInCharge: this);
        Optional.of(infringement)

    }
}
