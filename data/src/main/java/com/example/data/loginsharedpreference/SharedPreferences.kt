package com.example.data.loginsharedpreference

import android.content.Context
import android.content.SharedPreferences

/*
* Created By Nazira on 21/11/18
*/

//class to store login details in shared preference
open class SharedPreferences(context: Context) {
    val MyPREFERENCES = "MyPrefs"
    val EMAIL: String = "email"
    val PASSWORD: String = "password"
    val perfs: SharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)

    var email: String?
      get() = perfs.getString(EMAIL, "")
      set(value) = perfs.edit().putString(EMAIL, value).apply()


    var password: String?
      get() = perfs.getString(PASSWORD, "")
      set(value) = perfs.edit().putString(PASSWORD,value).apply()
}