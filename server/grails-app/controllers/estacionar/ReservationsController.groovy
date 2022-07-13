package estacionar

import location.Location
import reservationDetails.ReservationDetails
import timeFrame.LocalDateTimeFrame
import validations.ParkingReservationValidator
import java.time.LocalDateTime

class ReservationsController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [index: 'GET', test: 'GET', createReservation: 'POST', getReservationsOfDriver: 'GET']

    ReservationsService reservationsService

    def test() {
        response.setContentType("application/json")
        render '[{"name":"Afghanistan","code":"AF"},{"name":"Aland Islands","code":"AX"},{"name":"Albania","code":"AL"}]'
    }


    def createReservation(int driverId) {
        def body = request.JSON
        String startTime = body["startTime"] as String
        String endTime = body["endTime"] as String
        Location location = new Location(body["location"] as Map)
        def reservation =  reservationsService.createReservation(driverId, startTime, endTime, location)
        reservation.save()
        render reservation.toString()
    }

    def getReservationsOfDriver(int driverId) {
        def reservations = Reservation.findAll();
        render reservations.toString()
    }



}
