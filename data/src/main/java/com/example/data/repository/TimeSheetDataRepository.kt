package com.example.data.repository

import android.content.Context
import com.example.data.database.RealmOperations
import com.example.domain.model.TimeSheetData
import com.example.domain.repository.TimeSheetRepository
import io.reactivex.Observable

/**
 * Created by Kusuma on 10/12/18
 */
/**
 * This class is used to insert TimeSheet details
 * to realm database
 */
class TimeSheetDataRepository: TimeSheetRepository {
    override fun timeSheet(ctx: Context, timeSheetData: TimeSheetData): Observable<Boolean> {
        RealmOperations.getRealmObject(ctx)
        RealmOperations.insertTimeSheetData(timeSheetData)
        return Observable.just(true)
    }

    override fun destroyRealmObject() {
        RealmOperations.destroyObject()
    }
}