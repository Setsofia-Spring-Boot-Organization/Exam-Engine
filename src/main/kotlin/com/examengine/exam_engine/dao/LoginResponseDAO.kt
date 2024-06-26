package com.examengine.exam_engine.dao


class LoginResponseDAO (
    val status: Int,
    val message: String,
    val userId: String?,
    val username: String,
    val roles: String,
    val token: String
)
