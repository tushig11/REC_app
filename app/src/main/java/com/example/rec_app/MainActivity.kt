package com.example.rec_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rec_app.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var users: ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val msg = "Want to play some sport? \n\n Need a friend to play? \n\n Then you are in right place"
        welcomeMsg.text = msg
        getUsers()
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

    private fun getUsers() {

        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Home", "${document.id} => ${document.data["fname"]}")
                    val newUser = User(document.id.toString(),
                        document.data["fname"] as String,
                        document.data["lname"] as String,
                        document.data["email"] as String,
                        document.data["imagePath"] as String?,
                        document.data["phone"] as String
                    )
                    users.add(newUser)
                }

                println("TEST DATA IS HERE: ${users.size}")
            }
            .addOnFailureListener { exception ->
                Log.d("Home", exception.toString())
            }
    }
}