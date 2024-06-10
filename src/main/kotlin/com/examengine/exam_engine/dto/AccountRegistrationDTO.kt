package com.examengine.exam_engine.dto


data class AccountRegistrationDTO(
    private val username: String,
    private val email: String,
    private val password: String
) {
    fun getEmail() : String {
        return this.email
    }
    fun getPassword() : String {
        return this.password
    }
    fun getUsername() : String {
        return this.username
    }
}
