package com.mrkostua.mathalarm.extensions

import android.content.SharedPreferences

/**
 * @author Kostiantyn Prysiazhnyi on 3/5/2018.
 */
//extension function,
//inline keyword (read! use it in small func with extension func to avoid the cost of higher-order functions
// (more about how compiler works and costs of creating and saving this func)
public inline fun SharedPreferences.edit(action: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    action(editor)
    editor.apply()
}

/**
 * to use extension in different package this fun need to be imported like .get or .*
 */
public operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? ->
            edit({ it.putString(key, value) })
        is Int ->
            edit({ it.putInt(key, value) })
        is Boolean ->
            edit({ it.putBoolean(key, value) })
        else ->
            throw UnsupportedOperationException("Not implemented")
    }
}

//todo Think about creating additional fun for get(PreferencesConstants : Enum) so default value will be get inside this method not every time and result can but not null so we eliminate checking for null result
/**
 * to use extension in different package this fun need to be imported like .get or .*
 */
public inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class ->
            getString(key, defaultValue as? String) as T?
        Boolean::class ->
            getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Int::class ->
            getInt(key, defaultValue as? Int ?: -1) as T?
        else ->
            throw UnsupportedOperationException("Not implemented")
    }

}

/**
 * Testing fun without additional checking for null
 */
public inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T, test: String?): T {
    return when (T::class) {
        String::class ->
            getString(key, defaultValue as String) as T
        Boolean::class ->
            getBoolean(key, defaultValue as Boolean) as T
        Int::class ->
            getInt(key, defaultValue as Int) as T
        else ->
            throw UnsupportedOperationException("Not implemented")
    }


}

