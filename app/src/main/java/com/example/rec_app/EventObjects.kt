import java.util.*

class EventObjects {
    var id = 0
        private set
    var message: String
        private set
    var date: Date
        private set
    var end: Date
        private set

    constructor(message: String, date: Date, end: Date) {
        this.message = message
        this.date = date
        this.end = end
    }

    constructor(id: Int, message: String, date: Date, end: Date) {
        this.date = date
        this.message = message
        this.id = id
        this.end = end
    }

}