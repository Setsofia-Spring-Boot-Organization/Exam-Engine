package com.examengine.exam_engine.dao

class AnsweredQuestionsDAO(
    var status: Int?,
    var message: String,
    var questionId: String,
    var answers: List<AnswersDAO>,
    var totalScore: Int?,
    var remarks: String
) {
    class Builder {
        private var status: Int? = null
        private var message: String = ""
        private var questionId: String = ""
        private var answers: List<AnswersDAO> = ArrayList()
        private var totalScore: Int? = null
        private var remarks: String = ""

        fun status(status: Int?) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun questionId(questionId: String) = apply { this.questionId = questionId }
        fun totalScore(totalScore: Int) = apply { this.totalScore = totalScore }
        fun answers(answers: List<AnswersDAO>) = apply { this.answers  = answers}
        fun remarks(remarks: String) = apply { this.remarks = remarks }

        fun build() = AnsweredQuestionsDAO(
            status,
            message,
            questionId,
            answers,
            totalScore,
            remarks
        )
    }
}