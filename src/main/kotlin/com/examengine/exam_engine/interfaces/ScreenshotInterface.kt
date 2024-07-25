package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.StudentScreenshotDAO
import com.examengine.exam_engine.dto.StudentScreenshotDTO
import org.springframework.http.ResponseEntity

interface ScreenshotInterface {
    fun saveScreenshot(
        questionId: String,
        studentScreenshotDTO: StudentScreenshotDTO
    ): ResponseEntity<StudentScreenshotDAO>
}