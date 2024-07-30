package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.OverviewDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import org.springframework.http.ResponseEntity

interface TeacherQuestionsInterface {
    /**
     * @param teacherId the id of the teacher
     * @param questionsDTO the object that receives the questions ans their instructions from the user
     * @return QuestionsDAO with details about the newly created question
     */
    fun createNewQuestions(teacherId: String, questionsDTO: QuestionDetailsDTO): ResponseEntity<QuestionsDAO>

    /**
     * @param teacherId this param takes the teacher id
     * @return AllQuestionsDAO is the returns the total items returned from the request
     */
    fun getAllTeacherQuestions(teacherId: String): ResponseEntity<AllQuestionsDAO>

    /**
     * @param teacherId the teacher id as a parameter, is used to fetch questions set by a particular teacher
     * @return the returned response is an object of the AllQuestionsDAO, but with limited data
     */
    fun getAllTeacherQuestionsWithLimitedResults(teacherId: String): ResponseEntity<AllQuestionsDAO>

    /**
     * @param questionId the question id as a parameter is used to fetch a single question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return the total count of students that received the exams
     */
    fun getAllTeacherQuestionsReceiversCount(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO>

    /**
     * @param questionId the question id as a parameter is used to fetch a single question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return the total count of students that finished an exam
     */
    fun getAllTotalCountOfDoneStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO>

    /**
     * @param questionId the question id as a parameter is used to fetch a single question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return the total count of students that pass an exam
     */
    fun getAllTotalCountOfPassStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO>

    /**
     * @param questionId the question id as a parameter is used to fetch a single question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return the total count of students that failed an exam
     */
    fun getAllTotalCountOfFailedStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO>

    /**
     * This method retrieves the overview of a specific question by its ID and the ID of the teacher who created it.
     *
     * @param questionId the ID of the question to retrieve the overview for
     * @param teacherId the ID of the teacher who created the question
     * @return a ResponseEntity containing the OverviewDAO object with the question's overview details
     */
    fun getQuestionOverview(questionId: String, teacherId: String): ResponseEntity<OverviewDAO>
}