package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.LoginResponseDAO
import com.examengine.exam_engine.dao.SignupResponseDAO
import org.springframework.http.ResponseEntity

interface ResponseInterface {
    fun signupResponse(signupResponseDAO: SignupResponseDAO) : ResponseEntity<Any>
    fun loginResponse(loginResponseDAO: LoginResponseDAO) : ResponseEntity<Any>
}