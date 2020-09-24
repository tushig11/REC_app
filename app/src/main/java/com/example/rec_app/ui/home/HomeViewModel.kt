package com.example.rec_app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rec_app.model.User
import com.example.rec_app.repository.FirestoreRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener

class HomeViewModel : ViewModel() {

    val TAG = "Profile_ViewModel"
    var repo = FirestoreRepository()
    var currentUser : MutableLiveData<User> = MutableLiveData()

    fun getCurrentUser(): LiveData<User>{
        repo.getCurrentUser().addSnapshotListener(EventListener<DocumentSnapshot> { value, err ->
            if (err != null) {
                Log.w(TAG, "Listen failed.", err)
                currentUser.value = User()
                return@EventListener
            }

            if (value != null) {
                currentUser.value = value.toObject(User::class.java)
            } else currentUser.value = null

        })
        return currentUser
    }
}