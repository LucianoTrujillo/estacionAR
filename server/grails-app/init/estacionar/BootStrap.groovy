package estacionar

class BootStrap {

    def init = { servletContext ->
        // create a driver a save it in db
        def driver = new Driver(dni: "12345678", name: "Juan", licensePlate: "ABC-123")
        driver.save()
    }
    def destroy = {
    }
}
