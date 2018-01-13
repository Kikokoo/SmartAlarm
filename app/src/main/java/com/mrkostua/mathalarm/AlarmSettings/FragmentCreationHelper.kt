package com.mrkostua.mathalarm.AlarmSettings

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.FragmentOptionSetTime
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */

class FragmentCreationHelper(activity: Activity) {
    private val fragmentManager: FragmentManager = activity.fragmentManager
    private val TAG: String = this.javaClass.simpleName

    fun loadFragment(fragment: Fragment) {
        ShowLogs.log(TAG, "loadFragment + fragment " + fragment.toString())
        fragmentManager.beginTransaction()
                .replace(R.id.flFragmentsContainer, fragment)
                .addToBackStack(null)
                .commit()

    }

    fun getFragmentFormContainer(): Fragment {
        return fragmentManager.findFragmentById(R.id.flFragmentsContainer) ?: FragmentOptionSetTime()

    }
}
