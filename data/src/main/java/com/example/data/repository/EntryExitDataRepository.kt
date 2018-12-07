package com.example.data.repository

import android.content.Context
import com.example.data.database.RealmOperations
import com.example.domain.model.EntryExitTime
import com.example.domain.repository.EntryExitRepository
import io.reactivex.Observable

/**
 * Created by kusuma on 05/12/18.
 */
/**
 * This class is used to insert Geo-fence transition timings
 * to realm database
 */
class EntryExitDataRepository : EntryExitRepository {
    override fun storeTime(ctx: Context, entryExitTime: EntryExitTime): Observable<Boolean> {
        RealmOperations.getRealmObject(ctx)
        RealmOperations.insertTime(ctx,entryExitTime)
        return Observable.just(true)
    }

    override fun destroyRealmObject() {
        RealmOperations.destroyObject()
    }
}