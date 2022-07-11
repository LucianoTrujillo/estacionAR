package estacionar
import grails.rest.*
import validations.ParkingReservationValidator

@Resource(uri='/drivers')
class Driver {

    String name
    String dni
    String licensePlate
    List<Reservation> reservations

    static constraints = {
        name blank: false, nullable: false
        dni blank: false, nullable: false, unique: true
        licensePlate blank: false, nullable: false
    }

    Reservation reserveParkingAt(ReservationDetails details, ParkingReservationValidator parkingValidator) {
        Reservation reservation = Reservation.from(details, parkingValidator)
        reservations.add(reservation);
        reservation
    }

    def hasReservation(Reservation reservation) {
        reservations.contains(reservation)
    }

    boolean equals(Object driver){
        if (this === driver)
            return true

        if (driver == null)
            return false

        if (this.class != driver.class) {
            return false
        }

        Driver testDriver = (Driver)driver;
        dni == testDriver.dni
    }
}
