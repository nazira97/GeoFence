package com.example.domain.repository

/**
 * Created by Parvez on 20/11/18.
 */
import android.content.Context
import com.example.domain.Person
import io.reactivex.Observable

interface CrudRepository {
    abstract fun basicCRUD(email: String, password: String): Observable<Person>
    abstract fun findPerson(): Observable<Person>
    abstract fun complexReadWrite(): Observable<String>
    abstract fun destroyRealmObject()
}