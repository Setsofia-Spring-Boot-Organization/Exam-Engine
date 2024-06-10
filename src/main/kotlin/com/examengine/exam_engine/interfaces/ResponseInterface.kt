package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.SuccessResponseDAO
import org.springframework.http.ResponseEntity

interface ResponseInterface {
    fun successResponse(successResponseDAO: SuccessResponseDAO) : ResponseEntity<Any>
}