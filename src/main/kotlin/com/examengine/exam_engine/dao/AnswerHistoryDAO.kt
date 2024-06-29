package com.examengine.exam_engine.dao

import com.examengine.exam_engine.entities.StudentAnswersEntity

class AnswerHistoryDAO (
    var status: Int?,
    var message: String,
    var answers: List<StudentAnswersEntity>
) {
    class Builder {
        private var status: Int? = null
        private var message: String = ""
        private var answers: List<StudentAnswersEntity> = ArrayList()

        fun status(status: Int) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun answers(answers: List<StudentAnswersEntity>) = apply { this.answers = answers }

        fun build() = AnswerHistoryDAO (
            status = status,
            message = message,
            answers = answers
        )
    }
}