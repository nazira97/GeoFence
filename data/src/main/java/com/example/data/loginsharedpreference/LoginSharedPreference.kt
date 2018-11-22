package com.example.data.loginsharedpreference

import android.content.Context
import com.example.domain.model.LoginCredential
import com.example.domain.repository.LoginRepository
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

class LoginSharedPreference : LoginRepository {
    var perfs: SharedPreferences? = null
    override fun storeCredential(context: Context, logincredential: LoginCredential): Observable<LoginCredential> {
        perfs = SharedPreferences(context)
        perfs!!.email = logincredential.email
        perfs!!.password = logincredential.password

        return Observable.just(logincredential)
    }

    override fun checkCredential(): Observable<LoginCredential> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroyRealmObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}