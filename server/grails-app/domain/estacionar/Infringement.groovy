package estacionar

import java.time.LocalTime

class Infringement {
    Driver driver
    LocalTime time
    Location location
    Inspector supervisorInCharge

    static constraints = {
    }

    boolean isFor(Driver driver){
        driver == this.driver
    }
}
