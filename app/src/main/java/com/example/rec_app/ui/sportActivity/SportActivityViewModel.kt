package com.example.rec_app.ui.sportActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SportActivityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is event Fragment"
    }
    val text: LiveData<String> = _text

}