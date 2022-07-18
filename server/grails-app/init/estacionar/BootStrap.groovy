package estacionar

class BootStrap {

    def init = { servletContext ->
        // create a driver a save it in db
         def driver = new Driver(dni: "42822231", name: "Luciano Trujillo", licensePlate: "AA 123 BB")
         driver.save()
    }
    def destroy = {
    }
}
