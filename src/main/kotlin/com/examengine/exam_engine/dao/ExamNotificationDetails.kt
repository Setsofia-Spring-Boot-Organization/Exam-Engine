package com.examengine.exam_engine.dao

import java.time.LocalDateTime

data class ExamNotificationDetails(
    val studentNames: List<String>,
    val lecturerName: String,
    val courseName: String,
    val examTitle: String,
    val examDate: String,
    val examDuration: String,
    val examInstruction: String,
    val receivers: List<String>
)
