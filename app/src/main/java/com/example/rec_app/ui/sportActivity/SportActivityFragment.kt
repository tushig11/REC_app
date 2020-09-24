package com.example.rec_app.ui.sportActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rec_app.CreatePlayActivity
import com.example.rec_app.R
import com.example.rec_app.model.SportActivity
import kotlinx.android.synthetic.main.fragment_sport_activity.view.*


class SportActivityFragment : Fragment() {
    private val RESULT_ACTIVITY_CODE = 999
    private lateinit var sportActivityViewModel: SportActivityViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sportActivityViewModel = ViewModelProviders.of(this).get(SportActivityViewModel::class.java)
        val binding = inflater.inflate(R.layout.fragment_sport_activity, container, false)

        sportActivityViewModel.getActvities().observe(viewLifecycleOwner, {
            binding.activitiesView.layoutManager = LinearLayoutManager(binding.context)
            val adapter = SportActivityAdapter(binding.context, it as ArrayList<SportActivity>)
            binding.activitiesView.adapter = adapter
        })

        binding.createActivityBtn.setOnClickListener{
            startActivityForResult(Intent(binding.context, CreatePlayActivity::class.java), RESULT_ACTIVITY_CODE)
        }

        return binding
    }

}