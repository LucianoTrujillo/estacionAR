package estacionar

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        "/drivers"(resources: "driver") {
            "/reservations"(resources: "reservation") {
                "/pay"(controller:"reservations", action:[GET: "payReservation"])
            }
            "/reservations"(controller:"reservations", action:[POST: "createReservation", GET: "getReservationsOfDriver"])
        }
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
