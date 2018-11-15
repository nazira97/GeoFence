package com.example.data.database

import android.util.Log
import com.example.domain.Cat
import com.example.domain.Dog
import com.example.domain.Person
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

import io.reactivex.Observable;

open class CrudOperations {

    fun basicCRUD(realm: Realm): Observable<Person>  {
        realm.executeTransaction { realm ->
            // Add a person
            val person = realm.createObject<Person>(0)
            person.name = "Young Person"
            person.age = 14
        }

        // Find the first person (no query conditions) and read a field
        val person = realm.where<Person>().findFirst()!!

        realm.executeTransaction { _ ->
            person.name = "Senior Person"
            person.age = 99
        }
        return Observable.just(person)
    }

    fun findPerson(realm: Realm): Observable<Person> {
        val ageCriteria = 99
        val results = realm.where<Person>().equalTo("age", ageCriteria).findFirst()
        return Observable.just(results)
    }

    fun basicLinkQuery(realm: Realm) {
        val results = realm.where<Person>().equalTo("cats.name", "Tiger").findAll()
        Log.e("-----------","Size of result set: ${results.size}")
    }


    fun complexReadWrite(realm: Realm): Observable<String> {
        var status = "\nPerforming complex Read/Write operation..."
        // Add ten persons in one transaction
        realm.executeTransaction {
            val fido = realm.createObject<Dog>()
            fido.name = "fido"
            for (i in 1..9) {
                val person = realm.createObject<Person>(i.toLong())
                person.name = "Person no. $i"
                person.age = i
                person.dog = fido
                person.tempReference = 42
                for (j in 0..i - 1) {
                    val cat = realm.createObject<Cat>()
                    cat.name = "Cat_$j"
                    person.cats.add(cat)
                }
                Log.e("----------",person.name)
            }
        }
        // Implicit read transactions allow you to access your objects
        status += "\nNumber of persons: ${realm.where<Person>().count()}"
        // Iterate over all objects
        for (person in realm.where<Person>().findAll()) {
            val dogName: String = person?.dog?.name ?: "None"
            status += "\n${person.name}: ${person.age} : $dogName : ${person.cats.size}"
            check(person.tempReference == 0)
        }
        // Sorting
        val sortedPersons = realm.where<Person>().sort(Person::age.name, Sort.DESCENDING).findAll()
        status += "\nSorting ${sortedPersons.last()?.name} == ${realm.where<Person>().findAll().first()?.name}"
        return Observable.just(status)
    }
    fun complexQuery(realm: Realm): String {
        var status = "\n\nPerforming complex Query operation..."
        status += "\nNumber of persons: ${realm.where<Person>().count()}"
        // Find all persons where age between 7 and 9 and name begins with "Person".
        val results = realm.where<Person>()
                .between("age", 7, 9)       // Notice implicit "and" operation
                .beginsWith("name", "Person")
                .findAll()
        status += "\nSize of result set: ${results.size}"
        return status
    }

    fun complexRetrieve(){
        // More complex operations can be executed on another thread, for example using
        // Anko's doAsync extension method.
        // doAsync {
        var info = ""
        // Open the default realm. All threads must use its own reference to the realm.
        // Those can not be transferred across threads.
        // Realm implements the Closable interface, therefore
        // we can make use of Kotlin's built-in extension method 'use' (pun intended).
        Realm.getDefaultInstance().use { realm ->
            info += complexReadWrite(realm)
            info += complexQuery(realm)
        }
        //    uiThread {
       // showStatus(info)
        //   }
        //  }
    }
}