package com.example.rec_app.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.rec_app.model.EventObjects
import com.example.rec_app.model.SportActivity
import com.example.rec_app.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var db = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    var eventList = ArrayList<EventObjects>()

    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // get saved users from fire store

    fun getLoggedUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun getUsers(): ArrayList<User> {
        val users = ArrayList<User>()
            db.collection("users").get().addOnSuccessListener {
                for(doc in it){
                    val newUser = User(
                        doc.id.toString(),
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
            .update(
                mapOf(
                    "fname" to fname,
                    "lname" to lname,
                    "phone" to phone
                )
            )
    }

    fun addUser(id: String, user: User){
        db.collection("users").document(id)
            .set(user)
            .addOnSuccessListener {
                Log.d("HomeActivity", "Document added $it")
            }
            .addOnFailureListener(){
                Log.d("HomeActivity", "Document added ${it.toString()}")
            }
    }

    fun updateProfileImage(imagePath: String){
        db.collection("users").document(user!!.uid)
            .update(
                mapOf(
                    "imagePath" to imagePath,
                )
            )
    }

    fun saveSportActivity(activity: SportActivity){
        db.collection("activities").document(activity.id.toString())
            .set(activity)
            .addOnSuccessListener {
                Log.d("Repository", "Document added $it")
            }
            .addOnFailureListener(){
                Log.d("Repository", "Document added ${it.toString()}")
            }
    }

    fun getSportActivities(): CollectionReference {
        return db.collection("activities")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventsList(): ArrayList<EventObjects>{

        db.collection("activities").get().addOnSuccessListener {
            for(doc in it){
                Log.d(TAG, doc["date"].toString())
                Log.d(TAG, doc["startTime"].toString())
                Log.d(TAG, doc["endTime"].toString())
                val date: LocalDate = LocalDate.parse(doc["date"]!!.toString(), dateFormatter)
                val startTime: LocalTime = LocalTime.parse(
                    doc["startTime"]!!.toString(),
                    timeFormatter
                )
                val endTime: LocalTime = LocalTime.parse(doc["endTime"]!!.toString(), timeFormatter)
                val startInstant: Instant = date.atTime(startTime).toInstant(ZoneOffset.UTC)
                val endInstant: Instant = date.atTime(endTime).toInstant(ZoneOffset.UTC)

                var eventObj = EventObjects(
                    doc["sportType"].toString(),
                    Date.from(startInstant),
                    Date.from(endInstant)
                )
                eventList.add(eventObj)
                Log.d(TAG, eventList.size.toString())
            }
        }

        Log.d(TAG, eventList.size.toString())
        return eventList
    }
}