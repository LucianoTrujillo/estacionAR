package estacionar

class BootStrap {

    def init = { servletContext ->
         def inspector = new Inspector(name: "jorge")
         inspector.save()
    }
    def destroy = {
    }
}
