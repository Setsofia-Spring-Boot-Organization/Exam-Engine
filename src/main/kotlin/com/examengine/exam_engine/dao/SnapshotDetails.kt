package com.examengine.exam_engine.dao

import java.time.LocalDateTime

data class SnapshotDetails(
    val dateCreated: LocalDateTime,
    val snapshot: String
)
