package estacionar
import validations.ParkingReservationValidator

class Driver {

    String name
    String dni
    String address
    String email
    String licensePlate
    List<Reservation> reservations

    static constraints = {
        name blank: false, nullable: false
        dni blank: false, nullable: false
        address blank: false, nullable: false
        email blank: false, nullable: false
        licensePlate blank: false, nullable: false
    }

    def reserveParkingAt(ReservationDetails details, ParkingReservationValidator parkingValidator) {
        Reservation reservation = Reservation.from(this, parkingValidator, details)
        reservations.add(reservation)
    }

    boolean equals(Driver driver){
        dni == driver.getDni()
    }
}
