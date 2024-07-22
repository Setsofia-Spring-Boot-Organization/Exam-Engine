package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.UserDetailsDAO
import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import org.springframework.http.ResponseEntity

interface AuthenticationInterface {
    /**
     * @param accountRegistrationDTO takes in the registration details of the user:
     *     username: String,
     *     email: String,
     *     gender: String,
     *     password: String
     * @return responseEntity<Any>
     */
    fun registerAccount(accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any>
    fun loginUser(accountLoginDTO: AccountLoginDTO): ResponseEntity<Any>
    fun getUserDetails(userId: String): ResponseEntity<UserDetailsDAO>
}