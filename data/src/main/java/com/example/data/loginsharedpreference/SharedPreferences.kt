package com.example.data.loginsharedpreference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/*
* Created By Nazira on 21/11/18
*/

/*
 * It is a Singleton class to store login details in SharedPreference
 */
object SharedPreferences {

    private var mSharedPref: SharedPreferences? = null
    val EMAIL = "EMAIL"
    val PASSWORD = "PASSWORD"

    fun init(context: Context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun read(key: String, defValue: String): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.commit()
    }

    fun read(key: String, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun write(key: String, value: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.commit()
    }

    fun read(key: String, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!).commit()
    }
}