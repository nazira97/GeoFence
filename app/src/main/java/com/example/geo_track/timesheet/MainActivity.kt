package com.example.geo_track.timesheet

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.data.loginsharedpreference.LoginSharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import com.example.domain.model.LoginCredential
import com.example.domain.usecase.LoginUseCase
import com.example.domain.util.AppConstants.GEOFENCE_RADIUS_IN_METERS
import com.example.domain.util.AppConstants.TAG
import com.example.domain.util.AppConstants.latitude
import com.example.domain.util.AppConstants.longitude
import com.example.geo_track.timesheet.GeoFencing.GeofenceTransitionsIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Parvez on 15/11/18.
 */
class MainActivity : Activity() {

    companion object {
        private val EMAIL_VALIDATION_MSG = "Enter a valid email address"
        private val PASSWORD_VALIDATION_MSG = "Enter a valid password"
        private var email_valid: Boolean = true
        private var password_valid: Boolean = true
        private val EMAIL_PASSWORD_INVALID = "Email and Password invalid"
    }
    //to access location APIs
    lateinit var geofencingClient: GeofencingClient

    val loginUseCase = object : LoginUseCase(LoginSharedPreference()){}
    val loginCredentials = object : LoginCredential(){}
    val validator = object : Validator(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_btn.setOnClickListener {
              loginCredentials.email = et_email.text.toString()
              loginCredentials.password = et_password.text.toString()
              email_valid = validator.isValidEmail((loginCredentials.email).toString())
              password_valid = validator.isValidPassword((loginCredentials.password).toString())

           if ((email_valid) && (password_valid)) {
               geoFence()
               var storingDetail = loginUseCase.storeCredential(this, loginCredentials)
               storingDetail
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribeOn(AndroidSchedulers.mainThread())
                       .subscribe(
                               { storeUser ->
                                   Log.d("data is stored", storeUser.email + " " + storeUser.password)
                                   val intent = Intent(this@MainActivity, TimeSheetActivity::class.java)
                                   startActivity(intent)
                               },
                               { error ->
                                   Log.e("Error", error.message)
                               }
                       )
           }
           else if(!((email_valid) || (password_valid))){
               setError(EMAIL_PASSWORD_INVALID)
           }
           else if(!(email_valid)){
               setError(EMAIL_VALIDATION_MSG)
           }
           else if(!(password_valid)){
               setError(PASSWORD_VALIDATION_MSG)
           }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

  private fun showStatus(text: String) {
        val textView = TextView(this)
        textView.text = text
    }
    /*
    * This method creates geofence instance to access location APIs.
    */
    fun geoFence(){
        geofencingClient = LocationServices.getGeofencingClient(this)
        val geofence = buildGeofence()
        if (geofence != null && ContextCompat.checkSelfPermission(applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, " Starting GeoFencing")
            geofencingClient
                    .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                    // if geofencing added successfuly call success
                    .addOnSuccessListener {
                        Log.d(TAG, "Success")
                    }
                    // or else error
                    .addOnFailureListener {
                        // failure
                        Log.d(TAG, "Failure")
                    }
        }

    }

    /*
     * This method creates a Geofence.
     * @return Geofence.Builder() This returns built Geo-fence Area
     */
    private fun buildGeofence(): Geofence?{
        if (latitude != null && longitude != null && GEOFENCE_RADIUS_IN_METERS != null) {
            return Geofence.Builder()
                    // uniquely identifies the geofence within app
                    .setRequestId("")
                    // set region
                    .setCircularRegion(
                            latitude,
                            longitude,
                            GEOFENCE_RADIUS_IN_METERS
                    )
                    // transition states
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                    // Expiration duration
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build()
        }
        return null
    }

    val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    /*
     * This method monitor and set related geofence triggered events
     * @param geofence To add geofence to the list of geofence
     * @return GeofencingRequest This returns built Geofence request
     */
    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        Log.d(TAG, "Building")
        return GeofencingRequest.Builder()
                .setInitialTrigger(0)
                .addGeofences(listOf(geofence))
                .build()
    }
    private fun setError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}


