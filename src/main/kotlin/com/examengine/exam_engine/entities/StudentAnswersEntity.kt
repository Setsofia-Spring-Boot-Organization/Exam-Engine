package com.examengine.exam_engine.entities

import com.examengine.exam_engine.dao.AnswersDAO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class StudentAnswersEntity(
    @Id
    var answerId: String? = null,
    var questionId: String,
    var studentId: String,

    var answers: List<AnswersDAO>
)
