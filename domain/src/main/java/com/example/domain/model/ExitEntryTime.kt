package com.example.domain.model

import io.realm.RealmObject

/**
 * Created by Kusuma on 04/12/18.
 */
/**
 * Model class for Geo-fence transitions time
 */

open class EntryExitTime(var email: String? = "", var time: String = "", var type: String = "") : RealmObject() {

}