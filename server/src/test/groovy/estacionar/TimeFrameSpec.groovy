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

    void "test contains: given date time contained in time frame of reservation, when asked if contains date time, then returns true"() {
        LocalTime start = LocalTime.of( 0, 0)
        LocalTime end = LocalTime.of(2, 0)
        timeFrame = new TimeFrame(startTime: start, endTime: end)
        expect:"time frame contains dateTime"
        timeFrame.contains(LocalTime.of(1, 0))
    }
}
