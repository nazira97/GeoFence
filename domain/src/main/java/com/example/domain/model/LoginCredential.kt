package com.example.domain.model

import io.realm.annotations.PrimaryKey
/*
* Created By Nazira on 21/11/18
*/

/*
 * Model Class for LoginCredentials
 */

open class LoginCredential(@PrimaryKey var email: String? = "", var password: String? = ""){

}