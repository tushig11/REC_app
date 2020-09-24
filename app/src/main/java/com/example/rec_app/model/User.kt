package com.example.rec_app.model

import java.io.Serializable

class User: Serializable{
    var id: String? = "0"
    var fname: String = "Guest"
    var lname: String ="Guest"
    var email: String = "guest@gmail.com"
    var imagePath: String? = null
    var phone: String = "0"

    constructor()

    constructor(fname: String) {
        this.fname = fname
    }

    constructor(id: String, fname: String, lname: String, email: String, imagePath:String?, phone: String){
        this.id= id
        this.fname = fname
        this.lname = lname
        this.email = email
        this.imagePath = imagePath
        this.phone = phone
    }

    override fun toString(): String {
        return "$fname $lname"
    }
}