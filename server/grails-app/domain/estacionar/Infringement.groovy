package estacionar

import location.Location

import java.time.LocalDateTime

class Infringement {
    Driver driver
    LocalDateTime time
    Location location
    Inspector supervisorInCharge

    static constraints = {
    }

    static embedded = ['location']

    boolean isFor(Driver driver){
        driver == this.driver
    }
}
