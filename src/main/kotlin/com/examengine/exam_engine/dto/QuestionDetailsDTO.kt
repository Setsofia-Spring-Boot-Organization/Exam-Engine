package com.examengine.exam_engine.dto

import java.time.LocalDateTime

data class QuestionDetailsDTO(
    var questionTitle: String,
    var questionInstruction: String,

    var questionStartTime: LocalDateTime?,
    var questionEndTime: LocalDateTime?,

    var question: List<QuestionsDTO>,
    var passMark: Int,

    var questionReceivers: List<String>
)
