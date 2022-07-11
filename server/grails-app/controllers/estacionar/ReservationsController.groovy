package estacionar
import validations.ParkingReservationValidator

class ReservationsController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [index: 'GET', test: 'GET', createReservation: 'POST']

	ParkingReservationValidator parkingReservationValidator

    def test() {
        response.setContentType("application/json")
        render '[{"name":"Afghanistan","code":"AF"},{"name":"Aland Islands","code":"AX"},{"name":"Albania","code":"AL"}]'
    }

    def createReservation() {
        response.setContentType("application/json")
        def json = request.JSON
        def driverId = json["driverId"]
        Driver driver = Driver.get(driverId as Serializable)
        render '['+driver.name+']'
    }

    def index() {
        render "hola ${parkingReservationValidator}"
    }


}
