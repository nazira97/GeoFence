package com.example.data.database

import android.content.Context
import android.util.Log
import com.example.data.RealmOperations
import com.example.data.loginsharedpreference.SharedPreferences
import com.example.domain.model.EntryExitTime
import com.example.domain.repository.EntryExitRepository
import com.example.domain.util.AppConstants.TAG
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * Created by Kusuma on 04/12/2018
 */
/**
 * This class is used to store the Geo-fence
 * transition details to Realm database
 */

open class TimeStorage: EntryExitRepository {
    private var realm: Realm? = null
    private lateinit var realmOperations: RealmOperations

    override fun storeTime(ctx: Context, entryexitTime: EntryExitTime): Observable<Boolean> {
        realmOperations = object : RealmOperations(ctx){}
        realm = realmOperations.initialization()
        SharedPreferences.init(ctx)

        realm!!.executeTransaction { realm ->
            var timestore = realm!!.createObject<EntryExitTime>()
            timestore.email = SharedPreferences.read(SharedPreferences.EMAIL, "")
            timestore.time = entryexitTime.time
            timestore.type =  entryexitTime.type
            Log.d(TAG, "Stored data->"+ " " +timestore.email + " " +  timestore.time + " " + timestore.type)
        }
        var timestore = realm!!.where<EntryExitTime>().findFirst()
        if (timestore != null) {
            Log.d(TAG, "Retrieved data->"+ " " +timestore.email + " " + timestore.time + " " + timestore.type)
        }
        return Observable.just(true)
    }

    override fun destroyRealmObject() {
        realmOperations.destroyObject()
    }
}