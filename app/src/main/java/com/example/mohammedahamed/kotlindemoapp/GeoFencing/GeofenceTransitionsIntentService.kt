package com.example.mohammedahamed.kotlindemoapp.GeoFencing

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mohammedahamed.kotlindemoapp.MainActivity.Companion.TAG
import com.example.mohammedahamed.kotlindemoapp.R
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
/*
 * Created by Kusuma on 22-11-18
 */

/*
 * This is the Service to perform Geofence Transition in background
 */
class GeofenceTransitionsIntentService : IntentService("GeoTransition") {

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
            Log.d(TAG, "ID 00000000000000000000000000" + triggeringGeofences[0].requestId);

            Toast.makeText(this, "Entered", Toast.LENGTH_SHORT).show()

        } else {
            // Log the error.
            Log.d(TAG, getString(R.string.geofence_transition_invalid_type)+ geofenceTransition)
        }
    }
}