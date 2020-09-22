package com.example.rec_app.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rec_app.R
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_post, container, false)

        postsViewModel.text.observe(viewLifecycleOwner, Observer {  text_post.text = it  })

        return root
    }
}