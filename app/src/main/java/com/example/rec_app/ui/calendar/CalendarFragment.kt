package com.example.rec_app.ui.calendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.rec_app.R
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_calendar, container, false)

        viewModel.text.observe(viewLifecycleOwner, Observer {  text_calendar.text = it  })

        return root
    }
}