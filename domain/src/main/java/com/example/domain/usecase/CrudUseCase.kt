package com.example.domain.usecase
/**
 * Created by Parvez on 20/11/18.
 */
import android.content.Context
import com.example.domain.Person
import com.example.domain.repository.CrudRepository
import io.reactivex.Observable

open class CrudUseCase{

    internal lateinit var crudRepo: CrudRepository

    constructor(repository: CrudRepository){
        this.crudRepo = repository
    }

    fun basicCRUD(ctx: Context): Observable<Person> {
        return crudRepo.basicCRUD(ctx)
    }

    fun findPerson(): Observable<Person> {
        return crudRepo.findPerson()
    }

    fun complexReadWrite(): Observable<String> {
        return crudRepo.complexReadWrite()
    }

    fun destroyRealmObject() {
        return crudRepo.destroyRealmObject()
    }
}