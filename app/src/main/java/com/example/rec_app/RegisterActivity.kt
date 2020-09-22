package com.example.rec_app

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
    }


    fun createAccount(view: View){

        val fname = fnameInput.text.toString().trim()
        val lname = lnameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val pass = passInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()

        // Empty input checking
        if(TextUtils.isEmpty(fname)){
            fnameInput.error = "First name required"
            return
        }
        if(TextUtils.isEmpty(lname)){
            lnameInput.error = "Last name required"
            return
        }
        if(TextUtils.isEmpty(email)){
            emailInput.error = "Email address required"
            return
        }
        if(TextUtils.isEmpty(pass) || pass.length < 6){
            passInput.error = "Password must be include at least 6 character"
            return
        }
        if(TextUtils.isEmpty(phone)){
            phoneInput.error = "Phone number required"
            return
        }
        // input checking end

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Account created successfully",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed. ${task.exception.toString()}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun login(view: View){
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}