package com.examengine.exam_engine.dto

class AccountLoginDTO (
    private val email: String,
    private val password: String
) {
    fun getEmail() : String {
        return this.email
    }

    fun getPassword() : String {
        return this.password
    }
}