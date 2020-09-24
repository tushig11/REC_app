package com.example.rec_app.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rec_app.model.User
import com.example.rec_app.model.Video
import com.google.firebase.firestore.DocumentSnapshot

class PostsViewModel : ViewModel() {

    var videoList: MutableLiveData<ArrayList<Video>> = MutableLiveData()

    init {
        videoList.value = arrayListOf(
            Video("https://www.youtube.com/embed/9sBL1WsREyE", "tennis", "Beginner Tennis Lesson | How To Hit Forehands & Backhands"),
            Video("https://www.youtube.com/embed/UECvWpSBfU8", "basketball", "Michael Jordan's Basketball Lesson"),
            Video("https://www.youtube.com/embed/2lFq7T6pmu8", "basketball", "10 BEST Basketball Drills For BEGINNERS!!"),
            Video("https://www.youtube.com/embed/CXgfNBnetzQ", "tennis", "How To Serve In Tennis In 7 Steps"),
            Video("https://www.youtube.com/embed/oor0faEKwBo", "tennis", "How To Hit A Clean Tennis Shot"),
            Video("https://www.youtube.com/embed/92gCzJBNLcI", "badminton", "12 Things to Become a Better Badminton Player"),
            Video("https://www.youtube.com/embed/4E3Uqc7HflQ", "badminton", "7 Tactical Advice for Single Players")
        )
    }
}