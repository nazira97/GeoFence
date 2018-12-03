package com.example.data.loginsharedpreference

import android.content.Context
import com.example.domain.model.LoginCredential
import com.example.domain.repository.LoginRepository
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

/*
 * This class is used call SharedPreference instance
 * and pass the login details to SharedPreference
 */
class LoginSharedPreference : LoginRepository {
    override fun storeCredential(context: Context, loginCreds: LoginCredential): Observable<LoginCredential> {
        SharedPreferences.init(context)
        SharedPreferences.write(SharedPreferences.EMAIL, loginCreds.email)
        SharedPreferences.write(SharedPreferences.PASSWORD, loginCreds.password)

        return Observable.just(loginCreds)
    }
}