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
       try {
           String startTime = body["startTime"] as String
           String endTime = body["endTime"] as String
           Location location = new Location(body["location"] as Map)
           def reservation = reservationsService.createReservation(driverId, startTime, endTime, location)
           respond reservation, formats: ['json']
        }
        catch (Exception e) {
            log.error("error", e)
            def response = '{"error": "' + "no se pudo crear la reserva, revise los datos" + '"}'
            render response, status: 400
        }
    }


    def payReservation(int driverId, int reservationId) {
        def reservation = reservationsService.payReservation(driverId, reservationId)
        respond reservation, formats: ['json']
    }

    def getReservationsOfDriver(int driverId) {
        def reservations = Driver.get(driverId).reservations
        reservations.sort {
            a, b ->  a.timeFrame.endTime <=> b.timeFrame.endTime
        }
        respond reservations.reverse(), formats: ['json']
    }



}
