package com.examengine.exam_engine.dao

import com.examengine.exam_engine.entities.SnapshotEntity
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class StudentScreenshotDAO (
    var status: Int?,
    var message: String,
    var snapshots: List<SnapshotEntity>
    ) {
        class Builder {
            private var status: Int? = null
            private var message: String = ""
            private var snapshots: List<SnapshotEntity> = ArrayList()

            fun status(status: Int?) = apply { this.status = status }
            fun message(message: String) = apply { this.message = message }
            fun snapshots(snapshots: List<SnapshotEntity>) = apply { this.snapshots  = snapshots}

            fun build() = StudentScreenshotDAO(
                status,
                message,
                snapshots
            )
        }
    }