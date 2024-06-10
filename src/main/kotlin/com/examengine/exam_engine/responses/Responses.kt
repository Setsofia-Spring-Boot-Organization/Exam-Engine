package com.examengine.exam_engine.responses

import com.examengine.exam_engine.dao.SuccessResponseDAO
import com.examengine.exam_engine.interfaces.ResponseInterface
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class Responses : ResponseInterface{
    override fun successResponse(successResponseDAO: SuccessResponseDAO): ResponseEntity<Any> {
        return ResponseEntity.status(201).body(successResponseDAO)
    }

}