
// Place your Spring DSL code here

import validations.StreetValidation
import validations.ParkingReservationValidator
import java.time.LocalTime
import timeFrame.LocalTimeFrame


beans = {
    parkingReservationValidator(ParkingReservationValidator) {
        streetValidations = [new StreetValidation(
                streetsToValidate: ["libertador"],
                availableTimeFrameRightSide: new LocalTimeFrame(
                        startTime: LocalTime.of(5, 0),
                        endTime: LocalTime.of(21, 0)),
                availableTimeFrameLeftSide: new LocalTimeFrame(
                        startTime: LocalTime.of(5, 0),
                        endTime: LocalTime.of(21, 0)),
        )]
    }

}
