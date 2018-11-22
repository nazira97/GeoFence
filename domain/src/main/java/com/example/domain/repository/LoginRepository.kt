package com.example.domain.repository

import android.content.Context
import com.example.domain.model.LoginCredential
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

interface LoginRepository {
    abstract fun storeCredential(context: Context, logincredential: LoginCredential) : Observable<LoginCredential>
    abstract fun checkCredential() : Observable<LoginCredential>
    abstract fun destroyRealmObject()
}