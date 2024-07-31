package com.examengine.exam_engine.dao

import com.examengine.exam_engine.entities.StudentAnswersEntity

data class AnswerHistoryDAO (
    var status: Int?,
    var message: String,
    var answers: List<StudentAnswersEntity>
)