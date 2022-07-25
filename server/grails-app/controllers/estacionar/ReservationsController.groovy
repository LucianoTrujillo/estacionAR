package estacionar


class ReservationsController {

    ReservationsService reservationsService

    def createReservation(int driverId, ReservationDetails details) {
       try {
           respond reservationsService.createReservation(driverId, details), formats: ['json']
        }
        catch (Exception e) {
            handleError(e)
        }
    }


    def payReservation(int driverId, int reservationId) {
        try {
            respond reservationsService.payReservation(driverId, reservationId), formats: ['json']
        } catch (Exception e) {
            handleError(e)
        }

    }

    def getReservationsOfDriver(int driverId) {
        try {
            respond reservationsService.getDriverReservations(driverId), formats: ['json']
        } catch (Exception e) {
            handleError(e)
        }
    }

    def getReceipt(int reservationId) {
        try {
            respond Receipt.findByReservationId(reservationId), formats: ['json']
        } catch (Exception e) {
            handleError(e)
        }
    }

    def handleError(Exception e){
        log.error("error", e)
        def response = '{"error": "' + e.getMessage() + '"}'
        render response, status: 500
    }


}
