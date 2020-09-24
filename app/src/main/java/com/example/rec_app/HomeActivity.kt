package com.example.rec_app

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rec_app.ui.sportActivity.SportActivityViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var sportActivityViewModel: SportActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_sport_activity, R.id.navigation_post, R.id.navigation_calendar, R.id.navigation_profile))

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        //ViewModelProvider
        ViewModelProviders.of(this).get(SportActivityViewModel::class.java)
    }

    fun createPlay(view: View) {
        val createPlayIntent = Intent(this, CreatePlayActivity::class.java)
        startActivity(createPlayIntent)
    }

    private fun getCurrentUser(): String? {
        var spf = getSharedPreferences("loggedUser", 0)
        val editor = spf.edit()
        return spf.getString("userID", null)
    }
}