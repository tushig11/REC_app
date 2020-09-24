package com.example.rec_app

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rec_app.model.SportActivity
import java.time.format.DateTimeFormatter

class SportActivityAdapter(var context: Context, var sportActivities: ArrayList<SportActivity>) : RecyclerView.Adapter<SportActivityAdapter.MyViewHolder>(){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text = sportActivities!![position].userId.toString()
        holder.pals.text = sportActivities!![position].playpals?.map { p -> p.toString() }?.joinToString(", ")

        var dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        var endFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        holder.date.text = sportActivities!![position].date?.format(dateFormatter)
        holder.time.text = sportActivities!![position].startTime?.format(endFormatter) +
                " - " +
                sportActivities!![position].endTime?.format(endFormatter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportActivityAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_sport_activity, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return sportActivities.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var username: TextView = itemView.findViewById<TextView>(R.id.userName)
        var date : TextView = itemView.findViewById<TextView>(R.id.date)
        var time : TextView = itemView.findViewById<TextView>(R.id.timeInfo)
        var pals: TextView = itemView.findViewById<TextView>(R.id.playpals)
    }

}