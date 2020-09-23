package com.example.rec_app.model

import java.io.Serializable

class Video: Serializable{
    var url: String = ""
    var type: String = ""
    var title: String = ""

    constructor()

    constructor(url: String, type: String, title: String){
        this.url = url
        this.type = type
        this.title = title
    }

    override fun toString(): String {
        return title
    }
}