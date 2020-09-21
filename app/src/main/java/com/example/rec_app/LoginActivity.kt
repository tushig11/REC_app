package com.example.rec_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun googleLogin(view: View){
        Toast.makeText(this, "Google clicked", Toast.LENGTH_LONG).show()
    }

    fun facebookLogin(view: View){
        Toast.makeText(this, "Facebook clicked", Toast.LENGTH_LONG).show()
    }

    fun register(view: View){
        Toast.makeText(this, "Sign up clicked", Toast.LENGTH_LONG).show()
    }

}