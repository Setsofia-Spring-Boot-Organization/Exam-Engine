package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.ScreenshotEntity
import org.springframework.data.repository.CrudRepository

interface ScreenshotEntityRepository : CrudRepository<ScreenshotEntity, String>