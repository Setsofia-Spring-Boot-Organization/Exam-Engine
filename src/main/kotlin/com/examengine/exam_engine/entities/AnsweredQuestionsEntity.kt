package com.examengine.exam_engine.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class AnsweredQuestionsEntity (

    @Id
    var answerId: String? = null,

    var questionId: String,
    var userId: String,

    var remark: String
)