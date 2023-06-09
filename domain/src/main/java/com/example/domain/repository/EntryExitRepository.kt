package com.example.domain.repository

import android.content.Context
import com.example.domain.model.EntryExitTime
import io.reactivex.Observable

/**
* Created by Kusuma on 04/12/18.
*/

interface EntryExitRepository {
    /**
     * method to store Geo-fence Transition time
     *@param ctx to create Shared Preference instance
     *@param entryexitTime to access variables within EntryExitTime model class
     *@return Boolean returns true when data is stored to database successfully
     */
    abstract fun storeTime(ctx: Context,entryExitTime: EntryExitTime): Observable<Boolean>

    /**
     * Used to destroy RealmObject
     */
    abstract fun destroyRealmObject()
}
