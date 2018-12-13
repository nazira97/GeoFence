package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.TimeSheetData
import com.example.domain.repository.TimeSheetRepository
import io.reactivex.Observable

/**
 * Created by Kusuma 10/12/18
 */

open class TimeSheetUseCase {
    internal var timeSheetRepo: TimeSheetRepository

    constructor(repository: TimeSheetRepository){
        this.timeSheetRepo = repository
    }

    fun storeTimeSheetData(ctx: Context, timeSheetData: TimeSheetData): Observable<Boolean>{
        return timeSheetRepo.timeSheet(ctx,timeSheetData)
    }

    fun destroyRealmObject() {
        return timeSheetRepo.destroyRealmObject()
    }

}