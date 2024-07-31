package com.examengine.exam_engine.utilities

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class DateUtil {
    fun getCurrentDate() : LocalDateTime {
        return LocalDateTime.now()
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm a")
        return dateTime.format(formatter)
    }
}