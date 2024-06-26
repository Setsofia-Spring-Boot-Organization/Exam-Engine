package com.examengine.exam_engine.dao

import com.examengine.exam_engine.dto.QuestionReceiverDTO
import com.examengine.exam_engine.dto.QuestionsDTO
import com.examengine.exam_engine.enums.QuestionStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class QuestionsDAO(
    var status: Int?,
    var message: String,
    var dateCreated: LocalDate?,
    var questionTitle: String,
    var questionInstructions: String,
    var questionStatus: QuestionStatus,
    var questionStartTime: LocalDateTime,
    var questionEndTime: LocalDateTime,
    var questions: List<QuestionsDTO>,
    var receiver: List<QuestionReceiverDTO>
) {
    // Nested Builder class
    class Builder {
        private var status: Int? = null
        private var message: String = ""
        private var dateCreated: LocalDate? = null
        private var questionTitle: String = ""
        private var questionInstructions: String = ""
        private lateinit var questionStatus: QuestionStatus
        private lateinit var questionStartTime: LocalDateTime
        private lateinit var questionEndTime: LocalDateTime
        private var questions: List<QuestionsDTO> = listOf()
        private var receiver: List<QuestionReceiverDTO> = listOf()

        fun status(status: Int) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun dateCreated(dateCreated: LocalDate) = apply { this.dateCreated = dateCreated }
        fun questionTitle(questionTitle: String) = apply { this.questionTitle = questionTitle }
        fun questionInstructions(questionInstructions: String) = apply { this.questionInstructions = questionInstructions }
        fun questionStatus(questionStatus: QuestionStatus) = apply { this.questionStatus = questionStatus }
        fun questionStartTime(questionStartTime: LocalDateTime) = apply { this.questionStartTime = questionStartTime }
        fun questionEndTime(questionEndTime: LocalDateTime) = apply { this.questionEndTime = questionEndTime }
        fun questions(questions: List<QuestionsDTO>) = apply { this.questions = questions }
        fun receiver(receiver: List<QuestionReceiverDTO>) = apply { this.receiver = receiver }

        fun build() = QuestionsDAO(
            status,
            message,
            dateCreated,
            questionTitle,
            questionInstructions,
            questionStatus,
            questionStartTime,
            questionEndTime,
            questions,
            receiver
        )
    }
}

