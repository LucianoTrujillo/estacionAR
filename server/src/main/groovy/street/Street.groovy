package street

import com.sun.istack.NotNull
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import location.Location
import timeFrame.LocalDateTimeFrame

import java.time.Duration
import java.time.LocalDateTime


@ToString
@EqualsAndHashCode
class Street {

    enum Type {
        STREET,
        AVENUE
    }

    String name
    Type type
    // array of array of ints
    ArrayList<ArrayList<Integer>> bikeLanes
    ArrayList<ArrayList<Integer>> busLanes
    ArrayList<ArrayList<Integer>> signs

    static Street from(String name, Type type){
        new Street(
                type: type,
                name: name,
                bikeLanes: [],
                busLanes: [],
                signs: []
        )
    }

    void addBusLane(int begin, int end) {
        busLanes.add(new ArrayList<Integer>([begin, end]))
    }

    void addBikeLane(int begin, int end) {
        bikeLanes.add(new ArrayList<Integer>([begin, end]))
    }

    void addSign(int begin, int end) {
        signs.add(new ArrayList<Integer>([begin, end]))
    }

    boolean hasBikeLaneInNumber(int streetNumber){
        bikeLanes.any { streetNumber >= it[0] && streetNumber <= it[1] }
    }

    boolean hasBusLaneInNumber(int streetNumber){
        busLanes.any { streetNumber >= it[0] && streetNumber <= it[1] }
    }

    boolean hasSignInNumber(int streetNumber){
        signs.any { streetNumber >= it[0] && streetNumber <= it[1] }
    }








}
