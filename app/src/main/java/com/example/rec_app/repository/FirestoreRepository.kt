package com.example.rec_app.repository

import android.util.Log
import com.example.rec_app.classes.SportActivity
import com.example.rec_app.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var db = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    // get saved users from fire store

    fun getLoggedUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun getUsers(): ArrayList<User> {
        val users = ArrayList<User>()
            db.collection("users").get().addOnSuccessListener {
                for(doc in it){
                    val newUser = User(doc.id.toString(),
                        doc.data["fname"] as String,
                        doc.data["lname"] as String,
                        doc.data["email"] as String,
                        doc.data["imagePath"] as String?,
                        doc.data["phone"] as String
                    )
                users.add(newUser)
            }
            }
        return users
    }

    fun getCurrentUser(): DocumentReference{
        return db.collection("users").document(user!!.uid)
    }

    fun updateProfile(fname: String, lname: String, phone: String){
        db.collection("users").document(user!!.uid)
            .update(mapOf(
                "fname" to fname,
                "lname" to lname,
                "phone" to phone
            ))
    }

    fun addUser(id: String, user: User){
        db.collection("users").document(id)
            .set(user)
            .addOnSuccessListener {
                Log.d("HomeActivity","Document added $it")
            }
            .addOnFailureListener(){
                Log.d("HomeActivity","Document added ${it.toString()}")
            }
    }

    fun updateProfileImage(imagePath: String){
        db.collection("users").document(user!!.uid)
            .update(mapOf(
                "imagePath" to imagePath,
            ))
    }

    fun saveSportActivity(activity: SportActivity){
        db.collection("activities").document(activity.id.toString())
            .set(activity)
            .addOnSuccessListener {
                Log.d("Repository","Document added $it")
            }
            .addOnFailureListener(){
                Log.d("Repository","Document added ${it.toString()}")
            }
    }
}