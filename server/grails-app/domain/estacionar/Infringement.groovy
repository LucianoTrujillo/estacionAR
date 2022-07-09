package estacionar

import location.Location

import java.time.LocalTime

class Infringement {
    Driver driver
    LocalTime time
    Location location
    Inspector supervisorInCharge

    static constraints = {
    }

    static embedded = ['location']

    boolean isFor(Driver driver){
        driver == this.driver
    }
}
