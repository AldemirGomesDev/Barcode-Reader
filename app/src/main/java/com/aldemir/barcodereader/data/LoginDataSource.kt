package com.aldemir.barcodereader.data

import com.aldemir.barcodereader.data.model.LoggedInUser

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Resource<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Resource.success(fakeUser)
        } catch (e: Throwable) {
            return Resource.error("Error logging in", null)
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}