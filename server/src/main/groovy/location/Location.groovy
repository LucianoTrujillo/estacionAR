package location

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Location {

    String streetName
    Integer streetNumber

    static Location from(String streetName, Integer streetNumber) {
        return new Location(streetName: streetName, streetNumber: streetNumber)
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
