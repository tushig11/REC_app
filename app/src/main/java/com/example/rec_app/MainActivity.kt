package com.example.rec_app


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.rec_app.repository.FirestoreRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val msg = "Want to play some sport? \n\n Need a friend to play? \n\n Then you are in right place"
        welcomeMsg.text = msg

        FirestoreRepository().getEventsList()
        mum_logo.setOnClickListener{
            webView.visibility = View.VISIBLE
            webView.loadUrl("https://www.miu.edu/")
            webView.settings.javaScriptEnabled = true
            webView.settings.builtInZoomControls = true
            nextBtn.visibility = View.GONE
            backBtn.visibility = View.VISIBLE
        }

        backBtn.setOnClickListener{
            webView.visibility = View.GONE
            nextBtn.visibility = View.VISIBLE
            backBtn.visibility = View.GONE
        }
    }

    fun next(view: View){
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}