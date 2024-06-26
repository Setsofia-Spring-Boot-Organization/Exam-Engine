package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.StudentAnswersEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentAnswersRepository : CrudRepository<StudentAnswersEntity, String>
