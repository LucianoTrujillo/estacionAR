package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalTime

class TimeFrameSpec extends Specification implements DomainUnitTest<TimeFrame> {

    TimeFrame timeFrame

    def setup() {

    }

    def cleanup() {
    }

    void "timeframe contains particular time"() {
        given: "a timeframe from 04:00AM to 05:00AM"
        LocalTime start = LocalTime.of( 4, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)

        when: "asked if timeframe contains time 4:20AM"
        boolean timeFrameContainsTime = timeFrame.contains(LocalTime.of(4, 20))

        then: "returns true"
        timeFrameContainsTime
    }

    void "timeframe does not contain particular time"() {
        given: "a reservation from 03:00AM to 04:00AM"
        LocalTime start = LocalTime.of( 3, 0)
        LocalTime end = LocalTime.of(4, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)

        when: "asked if reservation contains time 4:20AM"
        boolean timeFrameContainsTime = timeFrame.contains(LocalTime.of(4, 20))

        then: "returns false"
        !timeFrameContainsTime
    }

    void "timeframe contains particular time frame"() {
        given: "a reservation from 04:00AM to 05:00AM"
        LocalTime start = LocalTime.of( 4, 0)
        LocalTime end = LocalTime.of(5, 0)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)

        when: "asked if reservation contains time from 4:20AM to 04:50AM"
        LocalTime testStart = LocalTime.of( 4, 0)
        LocalTime testEnd = LocalTime.of(4, 50)
        TimeFrame testTimeFrame = new TimeFrame(startTime: testStart, endTime: testEnd)
        boolean timeFrameContainsTime = timeFrame.contains(testTimeFrame)

        then: "returns true"
        timeFrameContainsTime
    }

    void "timeframe does not contain particular time frame"() {
        given: "a reservation from 04:00AM to 04:10AM"
        LocalTime start = LocalTime.of( 4, 0)
        LocalTime end = LocalTime.of(4, 10)
        TimeFrame timeFrame = new TimeFrame(startTime: start, endTime: end)

        when: "asked if reservation contains time from 4:20AM to 04:50AM"
        LocalTime testStart = LocalTime.of( 4, 0)
        LocalTime testEnd = LocalTime.of(5, 0)
        TimeFrame testTimeFrame = new TimeFrame(startTime: testStart, endTime: testEnd)
        boolean timeFrameContainsTime = timeFrame.contains(testTimeFrame)

        then: "returns true"
        !timeFrameContainsTime
    }
}
