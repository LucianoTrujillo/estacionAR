package timeFrame

import grails.databinding.BindingFormat
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

import java.time.LocalDateTime
import java.time.LocalTime

@ToString
@EqualsAndHashCode
class LocalDateTimeFrame {
    LocalDateTime startTime
    LocalDateTime endTime

    static LocalDateTimeFrame from(LocalDateTime startTime, LocalDateTime endTime) {
        return new LocalDateTimeFrame(startTime: startTime, endTime: endTime)
    }

    boolean contains(LocalDateTimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalDateTime time){
        startTime <= time && endTime >= time
    }
}

@Immutable
@ToString
@EqualsAndHashCode
class LocalTimeFrame {

    LocalTime startTime
    LocalTime endTime

    boolean contains(LocalTimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalTime time){
        startTime <= time && endTime >= time
    }
}
