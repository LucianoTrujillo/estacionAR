package estacionar
import validations.ParkingReservationValidator

class ReservationsController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [index: 'GET', test: 'GET']

	ParkingReservationValidator parkingReservationValidator

    // return a json that contains a list of numbers as json
    def test() {
        response.setContentType("application/json")
        render '[{"name":"Afghanistan","code":"AF"},{"name":"Aland Islands","code":"AX"},{"name":"Albania","code":"AL"}]'
    }

    def index() {
        render "hola ${parkingReservationValidator}"
    }


}
