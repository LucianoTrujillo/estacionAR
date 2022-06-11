package estacionar

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDateTime

class ParkingSupervisorSpec extends Specification implements DomainUnitTest<ParkingSupervisor> {

    ParkingSupervisor supervisor
    Driver driver
    DailyBlockReservations dailyBlockReservations

    def setup() {
        supervisor = new ParkingSupervisor(license: "AAA 000")
        driver = new Driver(
                name: "Pocho",
                dni: "42822222",
                address: "siempre viva 1234",
                email: "pochito@gmail.com",
                licensePlate: "BBB 111"
        )
    }

    def cleanup() {
    }

    void "test driverHasReservation: given driver has reserved parking, when sueprvisor asks if has reservation, then return true "() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0)
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 2, 0)

        LocalDateTimeFrame localDateTimeFrame = new LocalDateTimeFrame(startTime: start, endTime: end)
        ParkingBlock block = new ParkingBlock(
                availableParkingTimeFrame: localDateTimeFrame,
                vehicleCapacity: 1,
                boundingStreets: ["a", "b", "c", "d"]
        )
        dailyBlockReservations = new DailyBlockReservations(block: block, reservations: [])
        ParkingReservation.from(driver, block, localDateTimeFrame, dailyBlockReservations)

        expect:"driver has reservation"
            supervisor.driverHasReservation(driver, LocalDateTime.of(2022, 1, 1, 1, 0), dailyBlockReservations)
    }
}
