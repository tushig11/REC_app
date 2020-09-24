package com.example.rec_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.rec_app.R
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    var slideImages = arrayOf(
        R.drawable.slide3,
        R.drawable.slider4,
        R.drawable.slider5,
        R.drawable.slider6,
        R.drawable.slide1,
        R.drawable.slide2,
    )

    private lateinit var homeViewModel: HomeViewModel
    private var imagePath: String? = "https://cdn2.vectorstock.com/i/1000x1000/20/76/man-avatar-profile-vector-21372076.jpg"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            name_profile.text = it.toString()

            if (it.imagePath != null) {
                imagePath = it.imagePath
            }

            Glide.with(root.context)
                .load(imagePath)
                .override(300, 300)
                .into(root.image_profile);
        })

        root.carouselView.pageCount = slideImages.size;

        val imageListener: ImageListener = SlideListener(slideImages)
        root.carouselView.setImageListener(imageListener);

        root.playBtn.setOnClickListener{
            this.findNavController().navigate(R.id.navigation_sport_activity)
        }

        root.calendarBtn.setOnClickListener{
            this.findNavController().navigate(R.id.navigation_calendar)
        }

        root.learnBtn.setOnClickListener{
            this.findNavController().navigate(R.id.navigation_post)
        }

        root.home_playImage.setOnClickListener{
            this.findNavController().navigate(R.id.navigation_sport_activity)
        }

        root.home_learnImage.setOnClickListener{
            this.findNavController().navigate(R.id.navigation_post)
        }

        return root
    }

    class SlideListener(val list: Array<Int>): ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            imageView?.setImageResource(list[position]);
        }
    }
}