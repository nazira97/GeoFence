package com.example.geo_track.timesheet

import android.util.Patterns
import java.util.regex.Pattern

/*
* Created By Nazira on 28/11/18
*/

/**
 * This Validator class is used to validate Login details
 */
open class Validator {

    /**
     * Checks if the email is valid.
     * @param data - can be EditText or String
     * @return - true if the email is valid.
     */
    fun isValidEmail(data: Any): Boolean {
        val str = data.toString()
        val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()

        return valid
    }

    /**
     * Checks if the password is valid as per the following password policy.
     * Password should be minimum minimum 8 characters long.
     * Password should contain at least one number.
     * Password should contain at least one capital letter.
     * Password should contain at least one small letter.
     * Password should contain at least one special character.
     * Allowed special characters: "!@#$"
     *
     * @param data - can be EditText or String
     * @return - true if the password is valid as per the password policy.
     */
    fun isValidPassword(data: Any): Boolean {
        val str = data.toString()
        var valid = true

        // Password policy check
        // Password should be minimum minimum 8 characters long
        if (str.length < 8) {
            valid = false
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one special character
        // Allowed special characters : "!@#$"
        exp = ".*[!@#$].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        return valid
    }
}
