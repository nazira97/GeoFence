package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.LoginCredential
import com.example.domain.repository.LoginRepository
import io.reactivex.Observable

/*
* Created By Nazira on 21/11/18
*/

open class LoginUseCase {

    internal var loginRepo: LoginRepository

    constructor(repository: LoginRepository){
        this.loginRepo = repository
    }

    fun storeCredential(context: Context, loginCredential: LoginCredential): Observable<LoginCredential>{
        return loginRepo.storeCredential(context, loginCredential)
    }
}