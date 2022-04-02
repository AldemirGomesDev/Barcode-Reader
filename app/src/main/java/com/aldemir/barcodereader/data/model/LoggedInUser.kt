package com.aldemir.barcodereader.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepositoryImpl
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)