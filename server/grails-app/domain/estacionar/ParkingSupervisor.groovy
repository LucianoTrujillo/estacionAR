package estacionar
import java.time.LocalTime

class ParkingSupervisor {

    String licensePlate

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalTime dateTime, List<ParkingReservation> dailyReservations){
        dailyReservations.any {it.isFromDriver(driver) && it.isValidAt(dateTime)}
    }
}
