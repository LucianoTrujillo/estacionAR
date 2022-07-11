package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import timeFrame.LocalDateTimeFrame

import java.time.LocalDateTime

class LocalDateTimeFrameSpec extends Specification {

    void "timeframe contains particular time"() {
        given: "a timeframe from 04:00AM to 05:00AM"
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 4, 0)
        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 5, 0)
        LocalDateTimeFrame timeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)

        when: "asked if timeframe contains time 4:20AM"
        boolean timeFrameContainsTime = timeFrame.contains(LocalDateTime.of(2000, 1, 1, 4, 30))

        then: "returns true"
        timeFrameContainsTime
    }

    void "timeframe does not contain particular time"() {
        given: "a reservation from 03:00AM to 04:00AM"
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 3, 0)
        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 4, 0)
        LocalDateTimeFrame timeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)

        when: "asked if reservation contains time 4:20AM"
        boolean timeFrameContainsTime = timeFrame.contains(LocalDateTime.of(2000, 1, 1, 4, 20))

        then: "returns false"
        !timeFrameContainsTime
    }

    void "timeframe contains particular time frame"() {
        given: "a reservation from 04:00AM to 05:00AM"
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 4, 0)
        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 5, 0)
        LocalDateTimeFrame timeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)

        when: "asked if reservation contains time from 4:20AM to 04:50AM"
        LocalDateTime testStart = LocalDateTime.of(2000, 1, 1, 4, 20)
        LocalDateTime testEnd = LocalDateTime.of(2000, 1, 1, 4, 50)
        LocalDateTimeFrame testTimeFrame = new LocalDateTimeFrame(startTime: testStart, endTime: testEnd)
        boolean timeFrameContainsTime = timeFrame.contains(testTimeFrame)

        then: "returns true"
        timeFrameContainsTime
    }

    void "timeframe does not contain particular time frame"() {
        given: "a reservation from 04:00AM to 04:10AM"
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 4, 0)
        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 4, 10)
        LocalDateTimeFrame timeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)
        when: "asked if reservation contains time from 4:20AM to 04:50AM"
        LocalDateTime testStart = LocalDateTime.of(2000, 1, 1, 4, 20)
        LocalDateTime testEnd = LocalDateTime.of(2000, 1, 1, 4, 50)
        LocalDateTimeFrame testTimeFrame = new LocalDateTimeFrame(startTime: testStart, endTime: testEnd)
        boolean timeFrameContainsTime = timeFrame.contains(testTimeFrame)

        then: "returns false"
        !timeFrameContainsTime
    }
}
