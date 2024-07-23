package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.QuestionStatus
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@RequiredArgsConstructor
class QuestionUtil(
) {
    fun iteratedQuestions(question: QuestionsEntity): QuestionsDAO {
        return QuestionsDAO.Builder()
            .questionId(question.questionId!!)
            .questionStatus(question.questionStatus)
            .dateCreated(question.dateCreated)
            .questionTitle(question.questionTitle)
            .questionInstructions(question.questionInstruction)
            .questionStartTime(question.questionStartTime)
            .questionEndTime(question.questionEndTime)
            .questions(question.question)
            .passMark(question.passMark)
            .receivers(question.receivers)
            .build()
    }

    fun createQuestion(teacherId: String, questionsDTO: QuestionDetailsDTO): QuestionsEntity {
        if (
            questionsDTO.questionTitle.isEmpty() ||
            questionsDTO.questionInstruction.isEmpty() ||
            questionsDTO.questionStartTime == null ||
            questionsDTO.questionEndTime == null ||
            questionsDTO.question.isEmpty()
        ) throw MyExceptions(Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY)

        val newQuestion = QuestionsEntity(
                creator = teacherId,
                dateCreated = LocalDate.now(),
                questionStatus = QuestionStatus.ACTIVE,
                questionTitle = questionsDTO.questionTitle,
                questionInstruction = questionsDTO.questionInstruction,
                questionStartTime = questionsDTO.questionStartTime!!,
                questionEndTime = questionsDTO.questionEndTime!!,
                question = questionsDTO.question,
                passMark = questionsDTO.passMark,
                receivers = questionsDTO.questionReceivers,
                receiversDone = null
        )
        return newQuestion
    }

    fun successQuestionRequestResponse(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questionsDAO
        )
    }

    fun getAllTeacherQuestionsWithLimitedResults(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questionsDAO.map { question ->
                QuestionsDAO
                    .Builder()
                    .questionId(question.questionId)
                    .questionTitle(question.questionTitle)
                    .questionStartTime(question.questionStartTime!!)
                    .questionEndTime(question.questionEndTime!!)
                    .passMark(question.passMark!!)
                    .questionStatus(question.questionStatus!!)
                    .build()
            }
        )
    }

    fun newCreatedQuestionResponse(createdQuestion: QuestionsEntity): QuestionsDAO {
        return QuestionsDAO.Builder()
            .status(200)
            .message("Questions created successfully!")
            .questionId(createdQuestion.questionId!!)
            .dateCreated(createdQuestion.dateCreated)
            .questionTitle(createdQuestion.questionTitle)
            .questionStatus(createdQuestion.questionStatus)
            .questionInstructions(createdQuestion.questionInstruction)
            .questionStartTime(createdQuestion.questionStartTime)
            .questionEndTime(createdQuestion.questionEndTime)
            .questions(createdQuestion.question)
            .receivers(createdQuestion.receivers)
            .passMark(createdQuestion.passMark)
            .build()
    }
}