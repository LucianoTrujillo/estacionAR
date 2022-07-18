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
           Location location = Location.from(body["location"]["streetName"] as String, body["location"]["streetNumber"]  as Integer)
           def reservation = reservationsService.createReservation(driverId, startTime, endTime, location)
           respond reservation, formats: ['json']
        }
        catch (Exception e) {
            String errMsg = ""
            if (e instanceof Reservation.InvalidReservationException || e instanceof IllegalArgumentException) {
                errMsg = e.getMessage()
            }
            else {
                errMsg = "Algún dato no tiene formato o correcto o está vacío"
            }
            log.error("error", e)
            def response = '{"error": "' + errMsg + '"}'
            render response, status: 400
        }
    }


    def payReservation(int driverId, int reservationId) {
        try {
            def reservation = reservationsService.payReservation(driverId, reservationId)
            respond reservation, formats: ['json']
        } catch (Exception e) {
            log.error("error", e)
            def response = '{"error": "' + e.getMessage() + '"}'
            render response, status: 400
        }

    }

    def getReservationsOfDriver(int driverId) {
        try {
            def reservations = Driver.get(driverId).reservations
            reservations.sort {
                a, b ->  a.timeFrame.endTime <=> b.timeFrame.endTime
            }
            respond reservations.reverse(), formats: ['json']
        } catch (Exception e) {
            log.error("error", e)
            def response = '{"error": "' + "no se pudo obtener la lista de reservas del conductor" + '"}'
            render response, status: 400
        }
    }



}
