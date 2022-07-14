package reservationDetails

import com.sun.istack.NotNull
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.Duration
import java.time.LocalDateTime


@ToString
@EqualsAndHashCode
class ReservationDetails {
    LocalDateTimeFrame timeFrame
    Location location


    static ReservationDetails from(LocalDateTime startTime, Duration duration, Location location){
        new ReservationDetails(
                timeFrame: new LocalDateTimeFrame(
                        startTime: startTime,
                        endTime: startTime + duration
                ),
                location: location
        )
    }

    static ReservationDetails from(LocalDateTimeFrame timeFrame, Location location){
        new ReservationDetails(
                timeFrame: timeFrame,
                location: location
        )
    }
}