package com.example.rec_app.repository

import com.example.rec_app.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var db = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    // get saved users from fire store
    fun getUsers(): CollectionReference {
        return db.collection("users")
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

    fun updateProfileImage(imagePath: String){
        db.collection("users").document(user!!.uid)
            .update(mapOf(
                "imagePath" to imagePath,
            ))
    }

}