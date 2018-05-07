package com.mrkostua.mathalarm.alarms.mathAlarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.ShowLogs
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 4/11/2018.
 */
//TODO write test for this class (maybe it is possible just to mock AlarmManager so it will be local test)
class AlarmManagerHelper constructor(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val calendar = Calendar.getInstance()
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val newAlarmPendingIntent = PendingIntent.getBroadcast(context,
            0,
            Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_CANCEL_CURRENT)

    fun setNewAlarm(alarmObject: AlarmObject) {
        val alarmHour = alarmObject.hours
        val alarmMin = alarmObject.minutes

        calendar.timeInMillis = System.currentTimeMillis()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        val currentMinute = calendar[Calendar.MINUTE]

        refreshAndSetCalendar(alarmHour, alarmMin)
        when {
            alarmHour > currentHour -> {
                ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour  Today")
                ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Today")
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, newAlarmPendingIntent)

            }
            alarmHour < currentHour -> {
                ShowLogs.log(TAG, "h current: $currentHour  alarm hour: $alarmHour Next Day")
                ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Next Day")
                refreshAndSetCalendar(alarmHour, alarmMin, 1)
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, newAlarmPendingIntent)

            }
            alarmHour == currentHour -> {
                if (alarmMin < currentMinute) {
                    ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour Next Day ")
                    ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Next Day")
                    refreshAndSetCalendar(alarmHour, alarmMin, 1)
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, newAlarmPendingIntent)

                } else {
                    ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour  Today")
                    ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Today")
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, newAlarmPendingIntent)

                }

            }
        }
        startWakeLockService(alarmHour, alarmMin)

    }

    fun cancelLastSetAlarm() {
        alarmManager.cancel(getCancelPendingIntent())

    }

    fun snoozeAlarm(snoozeMinutes: Int) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeMinutes * 60 * 1000,
                newAlarmPendingIntent)
        startWakeLockService(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

    }


    private fun getCancelPendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(context,
                0,
                Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun startWakeLockService(alarmHour: Int, alarmMin: Int) {
        context.startService(Intent(context, WakeLockService::class.java)
                .putExtra(ConstantValues.WAKE_LOCK_HOUR_KEY, alarmHour)
                .putExtra(ConstantValues.WAKE_LOCK_MINUTE_KEY, alarmMin))
    }

    //TODO update this method Day_Of_Week is incorrect (in case of last day of the week)
    //after testing snoozeAlarm try to add one day in ms and add to the alarmTime
    private fun refreshAndSetCalendar(hour: Int, minute: Int, day: Int = 0) {
        with(calendar) {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.DAY_OF_WEEK, calendar[Calendar.DAY_OF_WEEK] + day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
    }
}