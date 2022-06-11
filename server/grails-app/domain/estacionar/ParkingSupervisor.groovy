package estacionar
import java.time.LocalDateTime

class ParkingSupervisor {

    String licensePlate

    static constraints = {

    }

    boolean driverHasReservation(Driver driver, LocalDateTime dateTime, DailyBlockReservations dailyBlockReservations){
        dailyBlockReservations.getActiveReservationFrom(driver, dateTime) != null
    }
}
