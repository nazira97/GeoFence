package com.example.domain


import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

/**
 * Created by Parvez on 15/11/18.
 */
open class Dog : RealmObject() {
    var name: String? = null

    @LinkingObjects("dog")
    val owners: RealmResults<Person>? = null
}