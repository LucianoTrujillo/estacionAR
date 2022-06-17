package estacionar
import validations.ParkingReservationValidator

class ReservationsController {
	static responseFormats = ['json', 'xml']
	ParkingReservationValidator parkingReservationValidator

    def index() {
        render "hola ${parkingReservationValidator}"
    }
}
