package com.example.rec_app.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is video post Fragment"
    }
    val text: LiveData<String> = _text
}