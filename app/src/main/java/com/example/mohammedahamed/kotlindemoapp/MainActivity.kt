package com.example.mohammedahamed.kotlindemoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.data.database.CrudOperations
import com.example.data.loginsharedpreference.LoginSharedPreference
import com.example.domain.usecase.CrudUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import com.example.domain.model.LoginCredential
import com.example.domain.usecase.LoginUseCase
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Parvez on 15/11/18.
 */
class MainActivity : Activity() {

    companion object {
        const val TAG: String = "InternalGeoTrack"
    }

    val crudUseCase = object : CrudUseCase(CrudOperations()){}
    val loginUseCase = object : LoginUseCase(LoginSharedPreference()){}
    val logincredential = object : LoginCredential(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logincredential.email = et_email.text.toString()
        logincredential.password = et_password.text.toString()

        var storingDetail = loginUseCase.storeCredential(this, logincredential)
        storingDetail
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         { storeUser ->
                             Log.d("retrieved data is",storeUser.email)
                             showStatus(" Name : "+storeUser.email + " \n Age : " + storeUser.password)
                         },
                         { error ->
                             Log.e("Error", error.message)
                         }
                 )

        var addingPerson = crudUseCase.basicCRUD(this)
        addingPerson
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { firstUser ->
                            Log.d("retrieved data is",firstUser.name)
                            showStatus(" Name : "+firstUser.name + " \n Age : " + firstUser.age)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )

        var searchPerson = crudUseCase.findPerson()
        searchPerson
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            Log.d("Retrieved data is",user.name)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )

        var AddingTenPersons = crudUseCase.complexReadWrite()
        AddingTenPersons
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {  usersDetail->
                            Log.d("retrieved data is",usersDetail)
                            showStatus(usersDetail)
                        },
                        { error ->
                            Log.e("Error ", error.message)
                        }
                )
    }
    override fun onDestroy() {
        super.onDestroy()
        crudUseCase.destroyRealmObject()
    }

  private fun showStatus(text: String) {
        val textView = TextView(this)
        textView.text = text
    }
}


