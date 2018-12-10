package com.example.domain.repository

import android.content.Context
import com.example.domain.model.TimeSheetData
import io.reactivex.Observable

/**
 * Created by Kusuma on 10/12/18.
 */

interface TimeSheetRepository {

    /**
     * method to store TimeSheet details
     *@param ctx to get Realm object
     *@param timeSheetData to access variables within TimeSheetData model class
     *@return Boolean returns true when data is stored to database successfully
     */

    abstract fun timeSheet(ctx: Context, timeSheetData: TimeSheetData): Observable<Boolean>

    /**
     * Used to destroy RealmObject
     */
    abstract fun destroyRealmObject()
}