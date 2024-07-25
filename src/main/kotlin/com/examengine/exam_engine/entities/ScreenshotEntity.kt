package com.examengine.exam_engine.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class ScreenshotEntity(
    @Id
    var screenshotId: String? = null,

    var dateCreated: LocalDateTime,

    var questionId: String,
    var studentId: String,

    var screenshot: String
)
