package estacionar

import java.time.Duration
import java.time.LocalDateTime

class DailyBlockReservations {

    ParkingBlock block
    List<ParkingReservation> reservations

    static constraints = {
    }

    ParkingReservation getActiveReservationFrom(Driver driver, LocalDateTime dateTime){
        reservations.find {
            it.isFromDriver(driver) && it.notExpiredAt(dateTime)
        }
    }

    ParkingReservation add(ParkingReservation parkingReservation){
        if(canAddReservationAt(parkingReservation.getReserveTimeFrame())) {
            reservations.add(parkingReservation)
        }
        parkingReservation
    }

    boolean canAddReservationAt(LocalDateTimeFrame timeFrame){
        checkParkingSpaceInTimeFrameUsingInterval(timeFrame, Duration.ofMinutes(30))
    }

    boolean checkParkingSpaceInTimeFrameUsingInterval(LocalDateTimeFrame timeFrame, Duration interval){
        def currentTimeToCheck = timeFrame.getStartTime() + interval
        if(currentTimeToCheck > timeFrame.endTime) {
            currentTimeToCheck = timeFrame.endTime
        }

        if(currentTimeToCheck.isEqual(timeFrame.getEndTime())){
            return enoughSpaceAtTime(currentTimeToCheck)
        }

        LocalDateTimeFrame newTimeFrame = new LocalDateTimeFrame(startTime: currentTimeToCheck, endTime: timeFrame.endTime);
        enoughSpaceAtTime(currentTimeToCheck) && checkParkingSpaceInTimeFrameUsingInterval(newTimeFrame, interval)
    }

    def enoughSpaceAtTime(LocalDateTime dateTime){
        amountOfParkedVehiclesAt(dateTime) < block.getVehicleCapacity()

    }

    def amountOfParkedVehiclesAt(LocalDateTime dateTime){
        reservations.count {it.isValidAt(dateTime)}
    }
}
