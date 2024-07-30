package com.examengine.exam_engine.dao

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class OverviewDAO (
    var status: Int?,
    var message: String,
    var totalStudents: Int?,
    var completedStudents: Int?,
    var passStudents: Int?,
    var failedStudents: Int?,
    var totalReceived: Int?,
    var absentStudents: Int?
    ) {
        class Builder {
            private var status: Int? = null
            private var message: String = ""
            private var totalStudents: Int? = null
            private var completedStudents: Int? = null
            private var failedStudents: Int? = null
            private var passStudents: Int? = null
            private var totalReceived: Int? = null
            private var absentStudents: Int? = null

            fun status(status: Int?) = apply { this.status = status }
            fun message(message: String) = apply { this.message = message }
            fun totalStudents(totalStudents: Int) = apply { this.totalStudents = totalStudents }
            fun completedStudents(completedStudents: Int) = apply { this.completedStudents = completedStudents }
            fun passStudents(passStudents: Int) = apply { this.passStudents  = passStudents}
            fun failedStudents(failedStudents: Int) = apply { this.failedStudents = failedStudents }
            fun totalReceived(totalReceived: Int) = apply { this.totalReceived  = totalReceived}
            fun absentStudents(absentStudents: Int) = apply { this.absentStudents  = absentStudents}

            fun build() = OverviewDAO(
                status,
                message,
                totalStudents,
                completedStudents,
                passStudents,
                failedStudents,
                totalReceived,
                absentStudents
            )
        }
    }