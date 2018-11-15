package com.example.data

import android.content.Context
import io.realm.Realm

open class RealmOperations{
    var ctx:Context?=null
    var realm : Realm? = null

    constructor(context: Context){
        this.ctx = context
    }

    fun initialization(): Realm?{
        Realm.init(ctx)
        realm = Realm.getDefaultInstance()

        realm!!.executeTransaction { realm ->
            realm.deleteAll()
        }
        return realm
    }

    fun destroyObject(){
        realm!!.close()
    }

    fun getRealmObject(): Realm? {
        return realm
    }
}