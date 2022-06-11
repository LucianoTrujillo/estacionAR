package estacionar

class Driver {

    String name
    String dni
    String address
    String email
    String licensePlate

    static constraints = {
        name blank: false, nullable: false
        dni blank: false, nullable: false
        address blank: false, nullable: false
        email blank: false, nullable: false
        licensePlate blank: false, nullable: false
    }

}
