package com.example.domain

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by Parvez on 15/11/18.
 */

open class Person(@PrimaryKey var id: Long = 0, var name: String = "", var age: Int = 0, var dog: Dog? = null,
                  var cats: RealmList<Cat> = RealmList(), @Ignore var tempReference: Int = 0) : RealmObject() {
    // The Kotlin compiler generates standard getters and setters.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.
}