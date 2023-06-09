package com.example.data



class NetworkApi {

    public fun validateUser(username: String?): Boolean {
        // imagine an actual network call here
        // for demo purpose return false in "real" life
        return !(username == null || username.length == 0)
    }
}
