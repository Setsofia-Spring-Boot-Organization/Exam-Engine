package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.SnapshotEntity
import org.springframework.data.repository.CrudRepository

interface SnapshotEntityRepository : CrudRepository<SnapshotEntity, String> {
    fun findSnapshotEntitiesByQuestionIdAndStudentId(questionId: String, studentId: String): List<SnapshotEntity>
}