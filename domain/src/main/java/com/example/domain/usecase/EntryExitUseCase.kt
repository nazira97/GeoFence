package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.EntryExitTime
import com.example.domain.repository.EntryExitRepository
import io.reactivex.Observable

/**
 * Created by Kusuma 04/12/18
 */

open class EntryExitUseCase {
    internal var entryRepo: EntryExitRepository

    constructor(repository: EntryExitRepository){
        this.entryRepo = repository
    }

    fun storeTime(ctx: Context, entryExitTime: EntryExitTime):Observable<Boolean> {
        return entryRepo.storeTime(ctx,entryExitTime)
    }

    fun destroyRealmObject() {
        return entryRepo.destroyRealmObject()
    }
}