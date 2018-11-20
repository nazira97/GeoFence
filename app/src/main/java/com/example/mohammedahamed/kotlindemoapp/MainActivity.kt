package com.example.mohammedahamed.kotlindemoapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.data.RealmOperations
import com.example.data.database.CrudOperations
import com.example.domain.usecase.CrudUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Parvez on 15/11/18.
 */
class MainActivity : Activity() {
    private var myPreferences = "myPrefs"
    private var EMAIL = "email"
    private var PASSWORD = "password"

    companion object {
        const val TAG: String = "InternalGeoTrack"
    }

    private lateinit var rootLayout: LinearLayout
    val crudUseCase = object : CrudUseCase(CrudOperations()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        login.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString(EMAIL, email.text.toString())
            editor.putString(PASSWORD, password.text.toString())
            editor.commit()
        }

        var addingPerson = crudUseCase.basicCRUD(EMAIL, PASSWORD)
        addingPerson
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { firstuser ->
                            Log.i("retrieved data is",firstuser.email)
                            //showStatus(" Name : "+firstuser.name + " \n Age : " + firstuser.age)
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
                            Log.i("Retrieved data is",user.email)
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
                            Log.i("retrieved data is",usersDetail)
                            //showStatus(usersDetail)
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

//    private fun showStatus(text: String) {
//        val textView = TextView(this)
//        textView.text = text
//        rootLayout.addView(textView)
//    }
}


