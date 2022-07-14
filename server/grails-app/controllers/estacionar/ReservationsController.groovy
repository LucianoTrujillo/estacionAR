package estacionar

import location.Location

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
        render reservation.toString()
    }

    def getReservationsOfDriver(int driverId) {
        render Driver.findById(driverId).reservations.toString()
    }



}
