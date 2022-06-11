package estacionar
import java.time.LocalDateTime

class ParkingReservation {

    LocalDateTimeFrame reserveTimeFrame
    ParkingBlock parkingBlock
    Driver driver

    static constraints = {
    }

    static ParkingReservation from(Driver driver, ParkingBlock parkingBlock, LocalDateTimeFrame reserveTimeFrame, DailyBlockReservations dailyBlockReservations){
        def reservation = new ParkingReservation(reserveTimeFrame: reserveTimeFrame, parkingBlock: parkingBlock, driver: driver)
        dailyBlockReservations.add(reservation)
        reservation
    }

    boolean isFromDriver(Driver driver){
        this.driver == driver
    }

    boolean notExpiredAt(LocalDateTime dateTime){
        reserveTimeFrame.contains(dateTime)
    }

    boolean isValidAt(LocalDateTime timeFrame) {
        reserveTimeFrame.contains(timeFrame);
    }


}
