package com.examengine.exam_engine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExamEngineApplication

fun main(args: Array<String>) {
	runApplication<ExamEngineApplication>(*args)
}