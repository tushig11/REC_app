package com.example.rec_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
    }

    override fun onStart(){
        super.onStart();
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    fun googleLogin(view: View){
        Toast.makeText(this, "Google clicked", Toast.LENGTH_LONG).show()
    }

    fun facebookLogin(view: View){
        Toast.makeText(this, "Facebook clicked", Toast.LENGTH_LONG).show()
    }

    fun login(view: View){

        val email = emailInput.text.toString().trim()
        val pass = passInput.text.toString().trim()

        // Empty input checking
        if(TextUtils.isEmpty(email)){
            emailInput.error = "Email address required"
            return
        }
        if(TextUtils.isEmpty(pass)){
            passInput.error = "Password required"
            return
        }

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Logged in successfully.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
    fun register(view: View){
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)

    }


    private fun getUser(){
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
    }
}