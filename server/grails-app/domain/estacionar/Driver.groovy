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
        licensePlate blank: false, nullable: false,  validator: {val, obj -> validLicensePlate(val) }
    }

    static boolean validLicensePlate(String licensePlate) {
        return licensePlate ==~ /[A-Z]{2}\s\d{3}\s[A-Z]{2}/ || licensePlate ==~ /[A-Z]{3}\s\d{3}/
    }

    Reservation reserveParkingAt(LocalDateTimeFrame timeFrame, Location location, ParkingReservationValidator parkingValidator) {
        if (reservations.find { it.timeFrame.intersects(timeFrame) && it.state == Reservation.PaymentState.UNPAID }) {
            throw new Reservation.InvalidReservationException("Ya tienes una reserva activa en este horario")
        }

        Reservation reservation = Reservation.from(timeFrame, location, parkingValidator, licensePlate)
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
