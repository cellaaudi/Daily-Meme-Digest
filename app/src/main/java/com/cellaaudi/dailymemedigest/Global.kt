package com.cellaaudi.dailymemedigest

object Global {
    var username = ""

    // FUNCTION
    public fun login(user_name: String) {
        username = user_name
    }
    public fun logout() {
        username = ""
    }
}