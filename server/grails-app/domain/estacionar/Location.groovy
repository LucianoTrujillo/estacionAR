package estacionar

class Location {

    String streetName
    Integer streetNumber

    static constraints = {
        streetName nullable: false
        streetNumber nullable: false
    }

    enum Side {
        LEFT,
        RIGHT
    }

    Side getSide() {
        if (streetNumber % 2 == 0) {
            Side.LEFT
        } else {
            Side.RIGHT
        }
    }

    boolean isLeftSide(){
        getSide() == Side.LEFT
    }

    boolean isRightSide(){
        getSide() == Side.RIGHT
    }

    boolean equals(Object location){
        if (this === location)
            return true

        if (location == null)
            return false

        if (this.class != location.class) {
            return false
        }

        Location testLocation = (Location)location;
        streetName == testLocation.streetName && streetNumber == testLocation.streetNumber
    }

}
