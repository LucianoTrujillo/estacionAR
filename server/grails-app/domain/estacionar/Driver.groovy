package estacionar
import grails.rest.*
import location.Location
import timeFrame.LocalDateTimeFrame
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

    Reservation reserveParkingAt(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator parkingValidator) {
        // chequear si no tengo una reserva en este timeFrame (atritubte reservations). Tirar excepcionsi es el caso
        if (reservations.find { it.timeFrame.intersects(timeFrame)}) {
            throw new Exception("Ya tienes una reserva en este horario")
        }

        Reservation reservation = Reservation.from(timeFrame, location, parkingValidator)
        reservations.add(reservation)
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
