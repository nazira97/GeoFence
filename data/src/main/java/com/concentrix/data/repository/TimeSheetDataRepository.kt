package com.concentrix.data.repository

import android.content.Context
import com.concentrix.data.database.RealmOperations
import com.concentrix.domain.model.TimeSheetData
import com.concentrix.domain.repository.TimeSheetRepository
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