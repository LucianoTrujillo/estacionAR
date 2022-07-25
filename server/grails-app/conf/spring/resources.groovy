
// Place your Spring DSL code here

import validations.ParkingReservationValidator
import street.Street

beans = {
    parkingReservationValidator(ParkingReservationValidator) {
        def bucarelli = Street.from("bucarelli", Street.Type.STREET)
        def nuevaYork = Street.from("nueva york", Street.Type.STREET)
        nuevaYork.addBikeLane(2000, 3100)
        nuevaYork.addSign(2500, 2600)
        def cabildo = Street.from("cabildo", Street.Type.AVENUE)
        cabildo.addBusLane(0, 5000)
        cabildo.addSign(1000, 1050)

        streets = [
                bucarelli,
                nuevaYork,
                cabildo
        ]
    }

}
