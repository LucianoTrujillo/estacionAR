package timeFrame

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

import java.time.LocalTime

@Immutable
@ToString
@EqualsAndHashCode
class TimeFrame {

    LocalTime startTime
    LocalTime endTime

    boolean contains(TimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalTime time){
        startTime <= time && endTime >= time
    }

}
