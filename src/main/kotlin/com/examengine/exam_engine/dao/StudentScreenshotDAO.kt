package com.examengine.exam_engine.dao

import com.examengine.exam_engine.entities.ScreenshotEntity
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class StudentScreenshotDAO (
    var status: Int?,
    var message: String,
    var screenshots: List<ScreenshotEntity>
    ) {
        class Builder {
            private var status: Int? = null
            private var message: String = ""
            private var screenshots: List<ScreenshotEntity> = ArrayList()

            fun status(status: Int?) = apply { this.status = status }
            fun message(message: String) = apply { this.message = message }
            fun screenshots(screenshots: List<ScreenshotEntity>) = apply { this.screenshots  = screenshots}

            fun build() = StudentScreenshotDAO(
                status,
                message,
                screenshots
            )
        }
    }