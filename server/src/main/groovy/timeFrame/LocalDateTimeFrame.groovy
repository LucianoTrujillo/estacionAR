package timeFrame

import grails.databinding.BindingFormat
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ToString
@EqualsAndHashCode
class LocalDateTimeFrame {
    LocalDateTime startTime
    LocalDateTime endTime

    static LocalDateTimeFrame from(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime.isAfter(endTime)) {
            printf("startTime %s is after endTime %s", startTime, endTime)
            throw new IllegalArgumentException("el tiempo de comienzo debe ser menor que el tiempo de fin")
        }
        return new LocalDateTimeFrame(startTime: startTime, endTime: endTime)
    }

    static LocalDateTimeFrame from(LocalDateTime startTime, Duration duration) {
        return from(startTime, startTime + duration)
    }

    boolean contains(LocalDateTimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalDateTime time){
         time >= startTime && time <= endTime
    }

    Duration duration() {
        return Duration.between(startTime, endTime)
    }

    boolean intersects(LocalDateTimeFrame frame){
        contains(frame.startTime) ||
        contains(frame.endTime) ||
                frame.contains(this)
    }
}