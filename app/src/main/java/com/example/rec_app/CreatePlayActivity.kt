package com.example.rec_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.rec_app.model.SportActivity
import com.example.rec_app.model.User
import com.example.rec_app.repository.FirestoreRepository
import kotlinx.android.synthetic.main.activity_create_play.*
import kotlinx.android.synthetic.main.layout_sport_activity.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class CreatePlayActivity : AppCompatActivity(), View.OnClickListener{

    private var aNewActivity : SportActivity? = null
    private var userList = ArrayList<User>()
    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private var friendsList : ArrayList<String> = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_play)

        aNewActivity = SportActivity(getUserId())

        btn_date_choose.setOnClickListener(this)
        btn_starting_time_choose.setOnClickListener(this)
        btn_ending_time_choose.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        val autotextView = findViewById<AutoCompleteTextView>(R.id.playpals_choose)
        // Get the array of friends
        var friendsAsList = FirestoreRepository().getUsers()

        // Create adapter and add in AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, friendsAsList)
        autotextView.setAdapter(adapter)

        //val inviteButton = findViewById<Button>(R.id.btn_invite)
        val listView: ListView = findViewById<ListView>(R.id.playpals_list)

        val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, friendsList)
        listView.adapter = listAdapter

        autotextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent, view, position, id ->

            val u: User = parent.getItemAtPosition(position) as User
            val fullname = u.toString()

            if(aNewActivity?.playpals == null)
                aNewActivity?.playpals = ArrayList<User>()

            aNewActivity?.playpals!!.add(u)

            toast(u.toString())

            friendsList.add(fullname)
            autotextView.text.clear()

            toast(fullname + " is added to invite list")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_date_choose -> {
                onClickOnDateBtn()
            }
            R.id.btn_starting_time_choose -> {
                onClickOnStartTimeBtn()
            }
            R.id.btn_ending_time_choose -> {
                onClickOnEndTimeBtn()
            }
            R.id.btn_submit -> {
                onClickOnSubmitBtn()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickOnDateBtn() {

        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if(isValidDate(year, monthOfYear, dayOfMonth)) {
                    var m: String = (101 + monthOfYear).toString().substring(1)
                    var d: String = (100 + dayOfMonth).toString().substring(1)

                    aNewActivity?.date = "$year-$m-$d"
                } else {
                    toast("Invalid date chosen")
                    aNewActivity?.date = null
                }
                updateDateText()

                aNewActivity?.startTime = null
                updateStartTimeText()

                aNewActivity?.endTime = null;
                updateEndTimeText()
            },
            mYear,
            mMonth,
            mDay
        )
        dpd.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickOnStartTimeBtn() {
        if(aNewActivity?.date == null) {
            toast("Choose date first")
            return
        }
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR)
        val mMinute = c.get(Calendar.MINUTE)
        val tpd = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                if(isValidStartingTime(hour, minute)) {
                    var h: String = (100 + hour).toString().substring(1)
                    var m: String = (100 + minute).toString().substring(1)
                    aNewActivity?.startTime = "$h:$m"
                } else {
                    toast("Invalid start time chosen")
                    aNewActivity?.startTime = null
                }
                updateStartTimeText()

                aNewActivity?.endTime = null;
                updateEndTimeText()
            },
            mHour,
            mMinute,
            false
        )
        tpd.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickOnEndTimeBtn() {
        if(aNewActivity?.startTime == null) {
            toast("Choose start time first")
            return
        }
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR)
        val mMinute = c.get(Calendar.MINUTE)
        val tpd = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                if(isValidEndingTime(hour, minute)) {
                    var h: String = (100 + hour).toString().substring(1)
                    var m: String = (100 + minute).toString().substring(1)
                    aNewActivity?.endTime = "$h:$m"
                } else {
                    toast("Invalid end time chosen")
                    aNewActivity?.endTime = null;
                }
                updateEndTimeText()
            },
            mHour,
            mMinute,
            false
        )
        tpd.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickOnSubmitBtn() {
        toast("Clicked")
        aNewActivity?.sportType = spr_sport_type.selectedItem.toString()
        if(aNewActivity?.sportType == null) {
            toast("Choose sport type!")
            return
        }

        if(aNewActivity?.endTime == null) {
            toast("Choose date and time!")
            return
        }

        aNewActivity!!.playpals?.forEach { pp ->
            println(pp.toString())
            Log.d("", pp.toString())
        }
        toast(aNewActivity.toString())
        aNewActivity!!.id = UUID.randomUUID().toString()
        if(saveActivity()) {

            toast("Saved successfully!")
            //close page
        } else {
            toast("Could not connect to the server. Try again!")
        }
    }

    fun saveActivity() : Boolean {
        //save into database
        FirestoreRepository().saveSportActivity(aNewActivity!!)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isValidDate(year : Int, month : Int, day : Int): Boolean {
        var current = LocalDateTime.now();
        if(year < current.year
            || (year == current.year && month + 1 < current.month.value)
            || (year == current.year && month + 1 == current.month.value && day < current.dayOfMonth))
            return false;
        return true;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isValidStartingTime(hour: Int, minute: Int): Boolean {
        var current = LocalDateTime.now();
        var selectedDate = LocalDate.parse(aNewActivity?.date!!, dateFormatter)

        if(current.year == selectedDate.year
            && current.month.value == selectedDate.month?.value
            && current.dayOfMonth == selectedDate.dayOfMonth) {
            return (current.hour < hour || current.hour == hour && current.minute < minute)
        }
        return true;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isValidEndingTime(hour: Int, minute: Int): Boolean {
        var selectedStartTime = LocalTime.parse(aNewActivity?.startTime!!, timeFormatter)
        return (hour > selectedStartTime.hour
            || hour == selectedStartTime.hour && minute > selectedStartTime.minute)
    }

    fun getUserId() : String {
        return FirestoreRepository().getLoggedUserID()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDateText() {
        if(aNewActivity?.date == null)
            btn_date_choose.text = "Choose date..."
        else
            btn_date_choose.text = aNewActivity?.date!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateStartTimeText() {
        if(aNewActivity?.startTime == null)
            btn_starting_time_choose.text = "Choose start time..."
        else
            btn_starting_time_choose.text = aNewActivity?.startTime!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateEndTimeText() {
        if(aNewActivity?.endTime == null)
            btn_ending_time_choose.text = "Choose end time..."
        else
            btn_ending_time_choose.text = aNewActivity?.endTime!!.format(timeFormatter)
    }

    fun toast(txt : String) {
        Toast.makeText(this, txt, LENGTH_LONG).show()
    }
}