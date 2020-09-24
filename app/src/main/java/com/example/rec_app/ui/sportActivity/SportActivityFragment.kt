package com.example.rec_app.ui.sportActivity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rec_app.R
import com.example.rec_app.SportActivityAdapter
import com.example.rec_app.model.SportActivity
import com.example.rec_app.model.User
import kotlinx.android.synthetic.main.fragment_sport_activity.view.*
import java.time.LocalDate
import java.time.LocalTime


class SportActivityFragment : Fragment() {

    private lateinit var sportActivityViewModel: SportActivityViewModel
    lateinit var sportActivities: ArrayList<SportActivity>
    lateinit var createActivity : Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sportActivityViewModel = ViewModelProviders.of(this).get(SportActivityViewModel::class.java)
        val binding = inflater.inflate(R.layout.fragment_sport_activity, container, false)
      //  eventViewModel.text.observe(viewLifecycleOwner, Observer {text_event.text = it })
        readActivities()
        binding.activitiesView.layoutManager = LinearLayoutManager(binding.context)
        val adapter = SportActivityAdapter(binding.context, sportActivities)

        binding.activitiesView.adapter = adapter

        return binding
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun readActivities() : List<SportActivity> {
        var playpals1 = arrayListOf(User("Sam"), User("Khaliun"), User("Deborah"))
        var playpals2 = arrayListOf(User("Tom"), User("Jerry"))

        var activity1 = SportActivity(
            1, "Maharishi",
            "Basketball", LocalDate.of(2020, 9, 22),
            LocalTime.of(9, 30), LocalTime.of(11, 30), playpals1
        )

        var activity2 = SportActivity(
            2, "Mahesh",
           "Badmington", LocalDate.of(2020, 9, 22),
            LocalTime.of(9, 30), LocalTime.of(11, 30), playpals2
        )

        var activity3 = SportActivity(
            3, "Yogi",
           "Basketball", LocalDate.of(2020, 9, 22),
            LocalTime.of(9, 30), LocalTime.of(11, 30), null
        )

        var activity4 = SportActivity(
            4, "Somesh",
            "Basketball", LocalDate.of(2020, 9, 22),
            LocalTime.of(9, 30), LocalTime.of(11, 30), null
        )

        sportActivities = arrayListOf(activity1, activity2, activity3, activity4)
        return sportActivities
    }
}