package estacionar
import java.time.LocalDateTime

class LocalDateTimeFrame {

    LocalDateTime startTime
    LocalDateTime endTime

    static constraints = {
        startTime nullable: false
        endTime nullable: false
    }

    boolean contains(LocalDateTimeFrame range){
        startTime <= range.startTime && endTime >= range.endTime
    }

    boolean contains(LocalDateTime dateTime){
        startTime <= dateTime && endTime >= dateTime
    }

}
