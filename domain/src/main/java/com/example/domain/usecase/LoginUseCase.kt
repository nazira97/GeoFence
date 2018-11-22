package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.LoginCredential
import com.example.domain.repository.LoginRepository
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

open class LoginUseCase {

    internal lateinit var loginRepo: LoginRepository

    constructor(repository: LoginRepository){
        this.loginRepo = repository
    }

    fun storeCredential(context: Context, logincredential: LoginCredential): Observable<LoginCredential>{
        return loginRepo.storeCredential(context, logincredential)
    }

    fun checkCredential(): Observable<LoginCredential>{
        return loginRepo.checkCredential()
    }

    fun destroyRealmObject(){
        return loginRepo.destroyRealmObject()
    }
}