package estacionar

import java.time.LocalTime

class ParkingInfringement {
    Driver driver
    LocalTime timeOfInfringement
    ParkingLocation locationOfInfringement
    ParkingSupervisor supervisorInCharge

    static constraints = {
    }

    boolean isFor(Driver driver){
        driver == this.driver
    }
}
