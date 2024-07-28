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
import java.time.LocalDateTime

@Component
@RequiredArgsConstructor
class QuestionUtil {
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
                dateCreated = LocalDateTime.now(),
                questionStatus = QuestionStatus.ACTIVE,
                questionTitle = questionsDTO.questionTitle,
                questionInstruction = questionsDTO.questionInstruction,
                questionStartTime = questionsDTO.questionStartTime!!,
                questionEndTime = questionsDTO.questionEndTime!!,
                question = questionsDTO.question,
                passMark = questionsDTO.passMark,
                receivers = questionsDTO.questionReceivers
        )

        if (newQuestion.questionEndTime < LocalDateTime.now()) throw MyExceptions(Reasons.INVALID_QUESTION_END_TIME)
        return newQuestion
    }

    fun successQuestionRequestResponse(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        val questions = ArrayList<QuestionsDAO>()
        for (question in questionsDAO) {
            val questionStatus =  if (question.questionEndTime!! < LocalDateTime.now()) {
                QuestionStatus.DONE
            } else {
                QuestionStatus.ACTIVE
            }

            questions.add(
                QuestionsDAO
                    .Builder()
                    .questionId(question.questionId)
                    .dateCreated(question.dateCreated!!)
                    .questionTitle(question.questionTitle)
                    .questionStatus(questionStatus)
                    .questionStartTime(question.questionStartTime!!)
                    .questionEndTime(question.questionEndTime!!)
                    .questions(question.questions)
                    .receivers(question.receivers)
                    .passMark(question.passMark!!)
                    .build()
            )
        }

        println(questions)

        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questions
        )
    }

    fun getAllTeacherQuestionsWithLimitedResults(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        val questions = ArrayList<QuestionsDAO>()
        for (question in questionsDAO) {
            val questionStatus =  if (question.questionEndTime!! < LocalDateTime.now()) {
                QuestionStatus.DONE
            } else {
                QuestionStatus.ACTIVE
            }

            questions.add(
                QuestionsDAO
                    .Builder()
                    .questionId(question.questionId)
                    .questionTitle(question.questionTitle)
                    .questionStartTime(question.questionStartTime!!)
                    .questionEndTime(question.questionEndTime!!)
                    .passMark(question.passMark!!)
                    .questionStatus(questionStatus)
                    .build()
            )
        }

        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questions
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