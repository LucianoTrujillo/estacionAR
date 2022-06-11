package estacionar

class ParkingBlock {

    LocalDateTimeFrame availableParkingTimeFrame
    Integer vehicleCapacity
    String[] boundingStreets

    static constraints = {
        availableParkingTimeFrame nullable: false
        vehicleCapacity nullable: false
        boundingStreets nullable: false
    }

}
