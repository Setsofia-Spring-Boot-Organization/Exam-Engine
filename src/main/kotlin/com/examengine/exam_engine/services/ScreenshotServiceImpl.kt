package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.StudentScreenshotDAO
import com.examengine.exam_engine.dto.StudentScreenshotDTO
import com.examengine.exam_engine.entities.SnapshotEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.ScreenshotInterface
import com.examengine.exam_engine.repositories.SnapshotEntityRepository
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RequiredArgsConstructor
class ScreenshotServiceImpl(
    private val snapshotEntityRepository: SnapshotEntityRepository
) : ScreenshotInterface {
    override fun saveScreenshot(
        questionId: String,
        studentScreenshotDTO: StudentScreenshotDTO
    ): ResponseEntity<StudentScreenshotDAO> {

        val screenshotEntity = SnapshotEntity(
            dateCreated = LocalDateTime.now(),
            questionId = questionId,
            studentId = studentScreenshotDTO.studentId,
            screenshot = studentScreenshotDTO.screenshot
        )

        try {
            val screenshot = snapshotEntityRepository.save(screenshotEntity)
            return ResponseEntity.status(201).body(
                StudentScreenshotDAO
                    .Builder()
                    .status(201)
                    .message("success")
                    .snapshots(listOf(screenshot))
                    .build()
            )
        } catch (exception: MyExceptions) {
            throw MyExceptions(Reasons.ERROR_SAVING_DATA)
        }
    }
}