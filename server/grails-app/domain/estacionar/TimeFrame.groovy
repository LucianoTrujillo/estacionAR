package estacionar
import java.time.LocalTime

class TimeFrame {

    LocalTime startTime
    LocalTime endTime

    static constraints = {
        startTime nullable: false
        endTime nullable: false
    }

    boolean contains(TimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalTime time){
        startTime <= time && endTime >= time
    }

}
