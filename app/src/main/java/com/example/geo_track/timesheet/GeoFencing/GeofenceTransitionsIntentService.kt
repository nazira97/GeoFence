package com.example.geo_track.timesheet.GeoFencing

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.data.repository.EntryExitDataRepository
import com.example.domain.model.EntryExitTime
import com.example.domain.usecase.EntryExitUseCase
import com.example.domain.util.AppConstants.TAG
import com.example.geo_track.timesheet.R
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

/*
 * Created by Kusuma on 22-11-18
 */

/*
 * This is the Service to perform Geofence Transition in background
 */
class GeofenceTransitionsIntentService : IntentService("GeoTransition") {
    private val entryExitCase = object : EntryExitUseCase(EntryExitDataRepository()){}
    val entryExit = object : EntryExitTime(){}

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, " Service Started running")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.errorCode)
            Log.d(TAG, "error->"+errorMessage)
            return
        }
        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered.
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            entryExit.time = (Calendar.getInstance().getTime()).toString()

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                entryExit.type = "Entry"
                Log.d(TAG, "entry" + " " +entryExit.type + " " +  entryExit.time)

            }
            else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                entryExit.type = "Exit"
                Log.d(TAG, "exit" + " " + entryExit.type + " " + entryExit.time)
            }

            var addingEntryExitTime = entryExitCase.storeTime(this, entryExit)
            addingEntryExitTime
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {storeTime ->
                                if(storeTime){
                                    Log.d(TAG, "Location Entered")
                                    Toast.makeText(this," Entered Location " + " " + entryExit.time + " " + entryExit.type, Toast.LENGTH_LONG).show()
                                }
                            },
                            { error ->
                                Log.e("Error", error.message)
                            }
                    )

        } else {
            // Log the error.
            Log.d(TAG, getString(R.string.geofence_transition_invalid_type)+ geofenceTransition)
        }
    }
}