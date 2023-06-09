package com.example.geo_track.timesheet

import android.app.Application

import io.realm.Realm

/**
 * Created by Parvez on 15/11/18.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
    }
}