package com.example.rec_app.ui.post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.rec_app.R
import com.example.rec_app.model.Video
import kotlinx.android.synthetic.main.video_view.view.*

class VideoAdapter(var context: Context, var videoList: ArrayList<Video>) : RecyclerView.Adapter<VideoAdapter.MyViewHolder>(){
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.video.loadData(createFrameVideo(videoList[position].url, videoList[position].title), "text/html", "utf-8")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.video_view, parent, false)
        return MyViewHolder(v)
    }
    override fun getItemCount(): Int {
        return videoList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var video: WebView = itemView.videoView
        init {
            video.settings.javaScriptEnabled = true
            video.webChromeClient = WebChromeClient()
        }
    }

    private fun createFrameVideo(uri: String, title: String) = "<html><body>$title<br><iframe width=100% height=100% src=\"$uri\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
}