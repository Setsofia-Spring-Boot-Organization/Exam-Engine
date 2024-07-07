package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.UserDetailsDAO
import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.entities.Users
import org.springframework.http.ResponseEntity

interface AuthenticationInterface {
    fun registerAccount(accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any>
    fun loginUser(accountLoginDTO: AccountLoginDTO): ResponseEntity<Any>
    fun getUserDetails(userId: String): ResponseEntity<UserDetailsDAO>
}