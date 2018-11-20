package com.example.data.database

import android.content.Context
import android.util.Log
import com.example.data.RealmOperations
import com.example.domain.Cat
import com.example.domain.Dog
import com.example.domain.Person
import com.example.domain.repository.CrudRepository
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where

import io.reactivex.Observable;

/**
 * Created by Parvez on 15/11/18.
 */
open class CrudOperations : CrudRepository {

    private var realm: Realm? = null
    private lateinit var realmOperations: RealmOperations

    override fun basicCRUD(ctx: Context): Observable<Person> {
        realmOperations = object : RealmOperations(ctx){}
        realm = realmOperations.initialization()

        realm!!.executeTransaction { realm ->
            // Add a person
            val person = realm.createObject<Person>(0)
            person.name = "Young Person"
            person.age = 14
        }

        // Find the first person (no query conditions) and read a field
        val person = realm!!.where<Person>().findFirst()!!

        realm!!.executeTransaction { _ ->
            person.name = "Senior Person"
            person.age = 99
        }
        return Observable.just(person)
    }

    override fun findPerson(): Observable<Person> {
        val ageCriteria = 99
        val results = realm!!.where<Person>().equalTo("age", ageCriteria).findFirst()
        return Observable.just(results)
    }

    override fun complexReadWrite(): Observable<String> {
        var status = "\nPerforming complex Read/Write operation..."
        // Add ten persons in one transaction
        realm!!.executeTransaction {
            val fido = realm!!.createObject<Dog>()
            fido.name = "fido"
            for (i in 1..9) {
                val person = realm!!.createObject<Person>(i.toLong())
                person.name = "Person no. $i"
                person.age = i
                person.dog = fido
                person.tempReference = 42
                for (j in 0..i - 1) {
                    val cat = realm!!.createObject<Cat>()
                    cat.name = "Cat_$j"
                    person.cats.add(cat)
                }
                Log.e("----------",person.name)
            }
        }
        // Implicit read transactions allow you to access your objects
        status += "\nNumber of persons: ${realm!!.where<Person>().count()}"
        // Iterate over all objects
        for (person in realm!!.where<Person>().findAll()) {
            val dogName: String = person?.dog?.name ?: "None"
            status += "\n${person.name}: ${person.age} : $dogName : ${person.cats.size}"
            check(person.tempReference == 0)
        }
        // Sorting
        val sortedPersons = realm!!.where<Person>().sort(Person::age.name, Sort.DESCENDING).findAll()
        status += "\nSorting ${sortedPersons.last()?.name} == ${realm!!.where<Person>().findAll().first()?.name}"
        return Observable.just(status)
    }

    override fun destroyRealmObject() {
        realmOperations.destroyObject()
    }
}