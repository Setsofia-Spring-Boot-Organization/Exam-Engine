package com.examengine.exam_engine.utilities

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DateUtil {
    fun getCurrentDate() : LocalDateTime {
        return LocalDateTime.now()
    }
}