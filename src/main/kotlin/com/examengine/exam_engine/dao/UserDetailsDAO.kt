package com.examengine.exam_engine.dao

import com.examengine.exam_engine.entities.Users

data class UserDetailsDAO(
    val status: Int,
    val message: String,
    val details: Users
)
