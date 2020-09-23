package com.example.rec_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rec_app.model.User
import com.example.rec_app.repository.FirestoreRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1111
    private val TAG = "Login Activity"
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        forgotPassword.setOnClickListener{
            resetPassword(it)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    fun googleLogin(view: View){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val userId = auth.currentUser?.uid
                    if (userId != null){
                        val repo = FirestoreRepository()
                        repo.getCurrentUser().get()
                            .addOnSuccessListener {
                                if( !it.exists()) repo.addUser(userId, User(userId, account.givenName.toString(), account.familyName.toString(), account.email.toString(), account.photoUrl.toString(), ""))
                            }
                        saveLoggedUser(userId)
                        startActivity(Intent(this, HomeActivity::class.java))
                       Toast.makeText(this, "signInWithCredential:success", Toast.LENGTH_LONG)
                    }

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }

                // ...
            }
    }

    fun facebookLogin(view: View){
        Toast.makeText(this, "Facebook login coming soon", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(
                        baseContext, "Logged in successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    auth.currentUser?.uid?.let {
                        saveLoggedUser(it)
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }
    fun register(view: View){
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)

    }

    private fun saveLoggedUser(id: String){
        val spf = getSharedPreferences("loggedUser", 0)
        val editor = spf.edit()
        editor.putString("userID", id)
        editor.apply()
    }

    private fun resetPassword(view: View){
        val email = emailInput.text.toString().trim()
        // Empty input checking
        if(TextUtils.isEmpty(email)){
            emailInput.error = "Email address required"
            return
        }
        // Empty input checking
        if(TextUtils.isEmpty(email)){
            emailInput.error = "Email address required"
            return
        }

        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}