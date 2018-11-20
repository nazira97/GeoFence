package com.example.domain.repository

/**
 * Created by Parvez on 20/11/18.
 */
import android.content.Context
import com.example.domain.Person
import io.reactivex.Observable

interface CrudRepository {
    abstract fun basicCRUD(ctx: Context): Observable<Person>
    abstract fun findPerson(): Observable<Person>
    abstract fun complexReadWrite(): Observable<String>
    abstract fun destroyRealmObject()
}