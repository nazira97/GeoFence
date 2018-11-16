package com.example.mohammedahamed.kotlindemoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.data.RealmOperations
import com.example.data.database.CrudOperations
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Parvez on 15/11/18.
 */
class MainActivity : Activity() {
    companion object {
        const val TAG: String = "InternalGeoTrack"
    }

    private lateinit var rootLayout: LinearLayout
    val crudOperations = object : CrudOperations(){}
    val realmOperations = object : RealmOperations(this){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayout = findViewById(R.id.container)
        rootLayout.removeAllViews()

        var addingPerson = crudOperations.basicCRUD(realmOperations.initialization()!!)
        addingPerson
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { firstuser ->
                            Log.i("retrieved data is",firstuser.name)
                            showStatus(" Name : "+firstuser.name + " \n Age : " + firstuser.age)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )

        var searchPerson = crudOperations.findPerson(realmOperations.getRealmObject()!!)
        searchPerson
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            Log.i("Retrieved data is",user.name)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )

        var AddingTenPersons = crudOperations.complexReadWrite(realmOperations.getRealmObject()!!)
        AddingTenPersons
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {  usersDetail->
                            Log.i("retrieved data is",usersDetail)
                            showStatus(usersDetail)
                        },
                        { error ->
                            Log.e("Error ", error.message)
                        }
                )
    }
    override fun onDestroy() {
        super.onDestroy()
        realmOperations.destroyObject()
    }

    private fun showStatus(text: String) {
        val textView = TextView(this)
        textView.text = text
        rootLayout.addView(textView)
    }
}


