package com.example.rec_app.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rec_app.R
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_post, container, false)

        postsViewModel.videoList.observe(viewLifecycleOwner, {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(root.context)
            recyclerView.adapter = VideoAdapter(root.context, it)
        })

        return root
    }
}