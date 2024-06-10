package com.examengine.exam_engine.responses

import com.examengine.exam_engine.dao.LoginResponseDAO
import com.examengine.exam_engine.dao.SignupResponseDAO
import com.examengine.exam_engine.interfaces.ResponseInterface
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class Responses : ResponseInterface{
    override fun signupResponse(signupResponseDAO: SignupResponseDAO): ResponseEntity<Any> {
        return ResponseEntity.status(201).body(SignupResponseDAO(
            status = signupResponseDAO.getStatus(),
            message = signupResponseDAO.getMessage()
        ))
    }

    override fun loginResponse(loginResponseDAO: LoginResponseDAO): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(LoginResponseDAO(
            status = loginResponseDAO.getStatus(),
            message = loginResponseDAO.getMessage(),
            token = loginResponseDAO.getToken()
        ))
    }
}