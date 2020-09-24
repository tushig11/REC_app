package com.example.rec_app.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class SportActivity {
    var id : String? = null
    var userId : String? = null
    var sportType : String? = null
    var date : String? = null
    var startTime: String? = null
    var endTime: String? = null
    var playpals: ArrayList<User>? = null
    var fullname: String? = null

    constructor(userId : String) {
        this.userId = userId
    }

    constructor()
    constructor(
        id: String,
        userId: String?,
        sportType: String?,
        date: String?,
        startTime: String?,
        endTime: String?,
        playpals: ArrayList<User>?,
        fullname: String?
    ) {
        this.id = id
        this.userId = userId
        this.sportType = sportType
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
        this.playpals = playpals
        this.fullname = fullname
    }

    override fun toString(): String {
        return "SportActivity(id=$id, userId=$userId, sportType=$sportType, date=$date, startTime=$startTime, endTime=$endTime, playpals=$playpals)"
    }


}