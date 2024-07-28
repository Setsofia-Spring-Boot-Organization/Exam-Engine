package com.examengine.exam_engine.entities

import com.examengine.exam_engine.dto.QuestionsDTO
import com.examengine.exam_engine.enums.QuestionStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class QuestionsEntity (
    @Id
    var questionId: String? = null,
    var creator: String,
    var dateCreated: LocalDateTime,

    var questionTitle: String,
    var questionInstruction: String,
    var questionStartTime: LocalDateTime,
    var questionEndTime: LocalDateTime,
    var questionStatus: QuestionStatus,

    var question: List<QuestionsDTO>,
    var passMark: Int,

    var receivers: List<String>,
)