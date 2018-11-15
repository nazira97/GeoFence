package com.example.domain


import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

open class Dog : RealmObject() {
    var name: String? = null

    @LinkingObjects("dog")
    val owners: RealmResults<Person>? = null
}