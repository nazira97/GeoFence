package com.concentrix.domain.repository

import android.content.Context
import com.concentrix.domain.model.LoginCredential
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

interface LoginRepository {
    /**
     * method to store login credentials
     *@param context to create Shared Preference instance
     *@param loginCreds to access variables within LoginCredential model class
     *@return LoginCredential this returns stored login details
     */
    abstract fun storeCredential(context: Context, loginCreds: LoginCredential) : Observable<LoginCredential>
}