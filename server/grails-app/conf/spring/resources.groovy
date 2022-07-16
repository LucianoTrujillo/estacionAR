
// Place your Spring DSL code here

import street.Street
import validations.ParkingReservationValidator
import java.time.LocalTime
import timeFrame.LocalTimeFrame


beans = {
    parkingReservationValidator(ParkingReservationValidator) {
        def street = Street.from("libertador", Street.Type.STREET)
        street.addBusLane(100, 200)
        street.addSign(150, 300)
        streets = [
                  street
        ]


    }

}
