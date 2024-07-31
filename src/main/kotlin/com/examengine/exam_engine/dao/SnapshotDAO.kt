package com.examengine.exam_engine.dao

data class SnapshotDAO(
    val status: Int,
    val message: String,
    val snapshots: List<SnapshotDetails>
)
