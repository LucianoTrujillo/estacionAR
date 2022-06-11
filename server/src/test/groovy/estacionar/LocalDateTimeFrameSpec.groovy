package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDateTime

class LocalDateTimeFrameSpec extends Specification implements DomainUnitTest<LocalDateTimeFrame> {

    LocalDateTimeFrame localDateTimeFrame

    def setup() {

    }

    def cleanup() {
    }

    void "test contains: given date time contained in time frame of reservation, when asked if contains date time, then returns true"() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0)
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 2, 0)
        localDateTimeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)
        expect:"time frame contains dateTime"
        localDateTimeFrame.contains(LocalDateTime.of(2022, 1, 1, 1, 0))
    }
}
