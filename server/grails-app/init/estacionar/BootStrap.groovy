package estacionar

class BootStrap {

    def init = { servletContext ->
        // create a driver a save it in db
         /*def driver = new Driver(dni: "42822231", name: "Luciano Trujillo", licensePlate: "AA 123 BB")
         driver.save()
         def driverPili = new Driver(dni: "43081901", name: "Pilar Gaddi", licensePlate: "NN 123 AA")
         driverPili.save()
         def inspector = new Inspector(name: "jorge")
         inspector.save()*/
    }
    def destroy = {
    }
}
