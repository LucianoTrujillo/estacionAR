package estacionar

class ParkingLocation {

    String streetName
    Integer streetNumber

    static constraints = {
        streetName nullable: false
        streetNumber nullable: false
    }

    enum LocationSide {
        LEFT,
        RIGHT
    }

    LocationSide getSide() {
        if (streetNumber % 2 == 0) {
            LocationSide.LEFT
        } else {
            LocationSide.RIGHT

        }
    }

    boolean isLeftSide(){
        getSide() == LocationSide.LEFT
    }

    boolean isRightSide(){
        getSide() == LocationSide.RIGHT
    }

}
