package com.example.domain.model

import io.realm.RealmObject

/**
 * Created by Kusuma on 10/12/18.
 */
/**
 * Model class for TimeSheet details
 */

open class TimeSheetData(var date: String = "", var projectCode: String = "") : RealmObject() {
}