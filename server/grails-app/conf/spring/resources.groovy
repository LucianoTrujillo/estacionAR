
// Place your Spring DSL code here

import timeFrame.TimeFrame
import validations.StreetValidation
import validations.ParkingReservationValidator

import java.time.LocalTime

beans = {
    parkingReservationValidator(ParkingReservationValidator) {
        streetValidations = [new StreetValidation(
                streetsToValidate: ["libertador"],
                availableTimeFrameRightSide: new TimeFrame(
                        startTime: LocalTime.of(5, 0),
                        endTime: LocalTime.of(21, 0)),
                availableTimeFrameLeftSide: new TimeFrame(
                        startTime: LocalTime.of(5, 0),
                        endTime: LocalTime.of(21, 0)),
        )]
    }

}
