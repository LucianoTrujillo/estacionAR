package estacionar
import grails.rest.*
@Resource(uri = '/receipts')
class Receipt {

    int driverId
    int reservationId


    static constraints = {
    }
}
