package com.mrkostua.mathalarm

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*

/**
 * @author Kostiantyn on 21.11.2017.
 */
//todo read more about kotlin !!!!! the way it work and compile
class MainMathAlarmButton : AppCompatActivity() {
    private lateinit var rlBackgroundLayout: RelativeLayout
    private lateinit var rlButtonLayout: RelativeLayout
    private var lastAlarmData: LastAlarmData = object : LastAlarmData(this) {}


    private lateinit var tvAlarmTime: TextView


    /**
    lateinit - this lets you have non-nullable properties in your Activity that you
    don't initialize when the constructor is called, but only later,
    in the onCreate method. You do however, in this case, take
    responsibility for initializing the variables before using
    them the first time, otherwise you'll get an exception at runtime.
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout to the full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main_alarm)

        initializeViews()
    }


    private fun initializeViews() {
        rlBackgroundLayout = findViewById(R.id.rlBackgroundLayout) as RelativeLayout
        rlButtonLayout = findViewById(R.id.rlButtonLayout) as RelativeLayout

        tvAlarmTime = findViewById(R.id.tvAlarmTime) as TextView
    }

    private fun intializeAlarm() {
        if (lastAlarmData.alarmHours != 0) {
            tvAlarmTime.text = Integer.toString(lastAlarmData.alarmHours) + " : " + Integer.toString(lastAlarmData.alarmMinutes)

        }
        //todo method for checking time and setting night day theme


    }


    private fun showDayOfTheWeek() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            0 -> {

            }
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }
            6 -> {

            }



        }

    }

    private fun underlineDayOfWeek(tvDayOfWeek : TextView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvDayOfWeek.setTextAppearance(R.style.ChosenDayOfTheWeek_TextTheme)
        } else {
            tvDayOfWeek.setTextAppearance(this,R.style.ChosenDayOfTheWeek_TextTheme)
        }
        
        val ssContent : SpannableString = object : SpannableString("Content"){}

        ssContent.setSpan(object : UnderlineSpan(){}, 0, ssContent.length, 0 )

        tvDayOfWeek.text = ssContent


    }
}
