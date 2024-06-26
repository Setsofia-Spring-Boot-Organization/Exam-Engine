package com.examengine.exam_engine.dao

class AnsweredQuestionsDAO(
    var status: Int?,
    var message: String,
    var questionId: String,
    var answers: List<AnswersDAO>
) {
    class Builder {
        private var status: Int? = null
        private var message: String = ""
        private var questionId: String = ""
        private var answers: List<AnswersDAO> = ArrayList()

        fun status(status: Int?) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun questionId(questionId: String) = apply { this.questionId = questionId }
        fun answers(answers: List<AnswersDAO>) = apply { this.answers  = answers}

        fun build() = AnsweredQuestionsDAO(
            status,
            message,
            questionId,
            answers
        )
    }
}