package com.example.rec_app.ui.sportActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rec_app.model.SportActivity
import com.example.rec_app.model.User
import com.example.rec_app.repository.FirestoreRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class SportActivityViewModel : ViewModel() {
    val TAG = "SportActivity_ViewModel"
    var repo = FirestoreRepository()
    var activities : MutableLiveData<List<SportActivity>> = MutableLiveData()

    fun getActvities(): LiveData<List<SportActivity>>{
        repo.getSportActivities().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                activities.value = null
                return@EventListener
            }

            var savedAddressList : MutableList<SportActivity> = mutableListOf()
            for (doc in value!!) {
                var addressItem = doc.toObject(SportActivity::class.java)
                savedAddressList.add(addressItem)
            }
            activities.value = savedAddressList
        })

        return activities
    }
}