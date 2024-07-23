package com.examengine.exam_engine.dao

import com.examengine.exam_engine.dto.QuestionsDTO
import com.examengine.exam_engine.enums.QuestionStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class QuestionsDAO(
    var questionId: String,
    var status: Int?,
    var message: String,
    var dateCreated: LocalDate?,
    var questionTitle: String,
    var questionInstructions: String,
    var questionStatus: QuestionStatus?,
    var questionStartTime: LocalDateTime?,
    var questionEndTime: LocalDateTime?,
    var questions: List<QuestionsDTO>,
    var studentQuestions: List<StudentQuestionsDAO>?,
    var totalCounts: Int?,
    var totalScore: Int?,
    var passMark: Int?,
    var receivers: List<String>
) {
    // Nested Builder class
    class Builder {
        private var questionId: String = ""
        private var status: Int? = null
        private var message: String = ""
        private var dateCreated: LocalDate? = null
        private var questionTitle: String = ""
        private var questionInstructions: String = ""
        private var questionStatus: QuestionStatus? = null
        private var questionStartTime: LocalDateTime? = null
        private var questionEndTime: LocalDateTime? = null
        private var questions: List<QuestionsDTO> = listOf()
        private var studentQuestions: List<StudentQuestionsDAO>? = null
        private var totalScore: Int? = null
        private var totalCounts: Int? = null
        private var passMark: Int? = null
        private var receivers: List<String> = listOf()

        fun questionId(questionId: String) = apply { this.questionId = questionId }
        fun status(status: Int) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun dateCreated(dateCreated: LocalDate) = apply { this.dateCreated = dateCreated }
        fun questionTitle(questionTitle: String) = apply { this.questionTitle = questionTitle }
        fun questionInstructions(questionInstructions: String) = apply { this.questionInstructions = questionInstructions }
        fun questionStatus(questionStatus: QuestionStatus) = apply { this.questionStatus = questionStatus }
        fun questionStartTime(questionStartTime: LocalDateTime) = apply { this.questionStartTime = questionStartTime }
        fun questionEndTime(questionEndTime: LocalDateTime) = apply { this.questionEndTime = questionEndTime }
        fun questions(questions: List<QuestionsDTO>) = apply { this.questions = questions }
        fun studentQuestions(studentQuestions: List<StudentQuestionsDAO>) = apply { this.studentQuestions = studentQuestions }
        fun totalScore(totalScore: Int) = apply { this.totalScore = totalScore }
        fun totalCounts(totalCounts: Int) = apply { this.totalCounts = totalCounts }
        fun passMark(passMark: Int) = apply { this.passMark = passMark }
        fun receivers(receivers: List<String>) = apply { this.receivers = receivers }

        fun build() = QuestionsDAO(
            questionId,
            status,
            message,
            dateCreated,
            questionTitle,
            questionInstructions,
            questionStatus,
            questionStartTime,
            questionEndTime,
            questions,
            studentQuestions,
            totalCounts,
            totalScore,
            passMark,
            receivers
        )
    }
}
