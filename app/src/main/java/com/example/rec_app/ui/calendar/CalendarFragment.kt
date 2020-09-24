package com.example.rec_app.ui.calendar

import EventObjects
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.rec_app.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private val cal = Calendar.getInstance()
    private var dailyEvent = listOf<EventObjects>()
    private var eventIndex = 0
    private var initialChildCnt = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_calendar, container, false)

//        viewModel.text.observe(viewLifecycleOwner, Observer {  text_calendar.text = it  })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventIndex = mLayout.childCount
        initialChildCnt = eventIndex

        dailyEvent = listOf<EventObjects>(
            EventObjects("THIS IS 21ST",convertStringToDate("21-09-2020 13:00"),convertStringToDate("21-09-2020 15:00")),
            EventObjects("THIS IS 22ND",convertStringToDate("22-09-2020 21:00"),convertStringToDate("22-09-2020 22:00")),
            EventObjects("THIS IS 23RD",convertStringToDate("23-09-2020 07:00"),convertStringToDate("23-09-2020 08:00"))
        )

        display_current_date.text = displayDateInString(cal.time)
        displayDailyEvents()

        previous_day.setOnClickListener {
            previousCalendarDate()
        }

        next_day.setOnClickListener {
            nextCalendarDate()
        }
    }

    private fun convertStringToDate(dateInString: String): Date {
        val format: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(dateInString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date!!
    }

    private fun previousCalendarDate() {
        if(mLayout.childCount >= initialChildCnt) {
            mLayout.removeViewAt(eventIndex - 1)
        }
        cal.add(Calendar.DAY_OF_MONTH, -1)
        display_current_date.text = displayDateInString(cal.time)
        displayDailyEvents()
    }

    private fun nextCalendarDate() {
        if(mLayout.childCount >= initialChildCnt) {
            mLayout.removeViewAt(eventIndex - 1)
        }
        cal.add(Calendar.DAY_OF_MONTH, 1)
        display_current_date.text = displayDateInString(cal.time)
        displayDailyEvents()
    }

    private fun displayDateInString(mDate: Date): String {
        val formatter = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
        return formatter.format(mDate)
    }

    private fun displayDailyEvents() {
        val startOfDay = Calendar.getInstance();
        startOfDay.time = cal.time;
        startOfDay.set(Calendar.HOUR_OF_DAY, 0)
        startOfDay.set(Calendar.MINUTE, 0)
        startOfDay.set(Calendar.SECOND, 0)
        startOfDay.set(Calendar.MILLISECOND, 0)

        val endOfDay = Calendar.getInstance()
        endOfDay.time = cal.time
        endOfDay.set(Calendar.HOUR_OF_DAY, 23)
        endOfDay.set(Calendar.MINUTE, 59)
        endOfDay.set(Calendar.SECOND, 59)
        endOfDay.set(Calendar.MILLISECOND, 999)

        for (eObj in dailyEvent) {
            val startDate = eObj.date
            val endDate = eObj.end
//            val s = LocalDate.parse(eObj.date)
            val eventMessage = eObj.message
            if(startDate.after(startOfDay.time) && endDate.before(endOfDay.time)) {
                val eventBlockHeight = getEventTimeFrame(startDate, endDate)
                Log.d(TAG, "Height $eventBlockHeight")
                displayEventSection(startDate, eventBlockHeight, eventMessage)
            }
        }
    }

    private fun getEventTimeFrame(start: Date, end: Date): Int {
        val SECOND = 1000
        val MINUTE = 60 * SECOND
        val HOUR = 60 * MINUTE

        var timeDifference = end.time - start.time
        val hours =  timeDifference / HOUR;
        timeDifference %= HOUR;

        val minutes = timeDifference / MINUTE

        return (hours * 60 + minutes).toInt()
    }

    private fun displayEventSection(eventDate: Date, height: Int, message: String) {
        val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val displayValue = timeFormatter.format(eventDate)
        val hourMinutes = displayValue.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val hours = Integer.parseInt(hourMinutes[0])
        val minutes = Integer.parseInt(hourMinutes[1])
        Log.d(TAG, "Hour value $hours")
        Log.d(TAG, "Minutes value $minutes")
        val topViewMargin = hours * 60 + minutes
        Log.d(TAG, "Margin top $topViewMargin")
        createEventView(topViewMargin, height, message)
    }

    private fun createEventView(topMargin: Int, height: Int, message: String) {
        val mEventView = TextView(activity)
        val lParam =
            RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        lParam.topMargin = topMargin * 4
        lParam.leftMargin = 24
        mEventView.layoutParams = lParam
        mEventView.setPadding(24, 0, 24, 0)
        mEventView.height = height * 4
        mEventView.gravity = 0x11
        mEventView.setTextColor(Color.parseColor("#ffffff"))
        mEventView.text = message
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"))
        mLayout.addView(mEventView, eventIndex - 1)
    }

    companion object {
        private val TAG = CalendarFragment::class.java.simpleName
    }


}