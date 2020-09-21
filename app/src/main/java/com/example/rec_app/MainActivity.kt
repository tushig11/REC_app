package com.example.rec_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val msg = "Want to play some sport? \n\n Need a friend to play \n\n There you are in right place"
        welcomeMsg.text = msg
    }

    fun Next(view: View){
        Toast.makeText(this, "Lets get started", Toast.LENGTH_SHORT).show()
        val welcomeIntent = Intent(this, WelcomeActivity::class.java)
        startActivity(welcomeIntent)
    }
}