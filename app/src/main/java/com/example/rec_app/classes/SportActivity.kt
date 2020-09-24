package com.example.rec_app.classes

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class SportActivity ( var id : Int,
                           var userId : String,
                           var sportType : String?,
                           var date : LocalDate?,
                           var startTime: LocalTime?,
                           var endTime: LocalTime?,
                           var playpals: ArrayList<User>?) : Serializable {


    constructor(userId : String) : this(
        -1, userId, null, null, null, null, ArrayList<User>()
    )
}