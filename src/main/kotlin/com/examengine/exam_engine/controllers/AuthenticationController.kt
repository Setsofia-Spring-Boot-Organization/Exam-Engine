package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.services.AuthenticationServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("exam-engine/api/v1/auth")
class AuthenticationController(
    private val authenticationServiceImpl: AuthenticationServiceImpl
) {

    // register account
    @PostMapping("/register-account")
    fun registerAccount(@RequestBody accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any> {
        return authenticationServiceImpl.registerAccount(accountRegistrationDTO)
    }

    // login user
    @PostMapping("/login")
    fun loginAccount(@RequestBody accountLoginDTO: AccountLoginDTO): ResponseEntity<Any> {
        return authenticationServiceImpl.loginUser(accountLoginDTO)
    }
}