package com.concentrix.data.database

import android.content.Context
import android.util.Log
import com.concentrix.data.loginsharedpreference.SharedPreferences
import com.concentrix.domain.model.EntryExitTime
import com.concentrix.domain.model.TimeSheetData
import com.concentrix.domain.util.AppConstants
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * Created by Parvez on 15/11/18.
 */

/**
 * Singleton class to do realm operations.
 */
object RealmOperations{
    private var realm : Realm? = null

    private fun initialization(ctx:Context): Realm?{
        Realm.init(ctx)
        realm = Realm.getDefaultInstance()

        realm!!.executeTransaction { realm ->
            realm.deleteAll()
        }
        return realm
    }

    fun destroyObject(){
        realm!!.close()
    }


    fun getRealmObject(ctx:Context): Realm? {
        if(realm!= null) {
            return realm
        }
        return initialization(ctx)
    }

    fun insertTime(ctx:Context,entryExitTime: EntryExitTime){
        realm!!.executeTransaction{
            var timestore = realm!!.createObject<EntryExitTime>()
            SharedPreferences.init(ctx)
            timestore.email = SharedPreferences.read(SharedPreferences.EMAIL, "")
            timestore.time = entryExitTime.time
            timestore.type =  entryExitTime.type
            Log.d(AppConstants.TAG, "Stored data->"+timestore.email +  timestore.time + timestore.type)
            realm!!.insert(timestore)
        }
        var timestore = realm!!.where<EntryExitTime>().findFirst()
        if (timestore != null) {
            Log.d(AppConstants.TAG, "Retrieved data->"+timestore.email +  timestore.time + timestore.type)
        }
    }

    fun insertTimeSheetData(timeSheetData: TimeSheetData){
        realm!!.executeTransaction{
            var projectDetail = realm!!.createObject<TimeSheetData>()
            projectDetail.date = timeSheetData.date
            projectDetail.projectCode = timeSheetData.projectCode
            Log.d(AppConstants.TAG, "Stored data->" + projectDetail.date + " " + projectDetail.projectCode)
            realm!!.insert(projectDetail)
        }
        var projectDetail = realm!!.where<TimeSheetData>().equalTo("projectdetail.date", "12/10/2018").findAll()
        if (projectDetail != null) {
            Log.d(AppConstants.TAG, "Retrieved data->"+ projectDetail)
        }
    }
}