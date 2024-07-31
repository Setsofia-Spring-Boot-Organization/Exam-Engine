package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.ExamNotificationDetails
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.time.LocalDateTime

@Service
class EmailService @Autowired constructor(
    private val javaMailSender: JavaMailSender,
    @Value("\${MAIL_USERNAME}") private val mailSender: String,
    private val templateEngine: TemplateEngine
) {

    private fun sendEmail(variables: Map<String, Any>, emailReceivers: List<String>) {
        for (receiver in emailReceivers) {
            val context = Context().apply {
                setVariables(variables + mapOf("studentName" to receiver, "dashboardUrl" to "https://exam-engine-ttu.vercel.app/student/dashboard")) // Add dashboard URL to context
            }

            val text: String = templateEngine.process("exam-notification", context)

            val message: MimeMessage = javaMailSender.createMimeMessage()
            MimeMessageHelper(message, true, "UTF-8").apply {
                setPriority(1)
                setSubject("New Exam Assigned")
                setFrom(mailSender)
                setTo(receiver)
                setText(text, true)
            }

            try {
                javaMailSender.send(message)
                println("Email sent successfully to $receiver!")
            } catch (e: MessagingException) {
                e.printStackTrace()
                println("Error sending email to $receiver: ${e.message}")
            }
        }
    }



    fun sendExamNotification(examDetails: ExamNotificationDetails) {
        val inviteTemplateVariables: Map<String, Any> = mapOf(
            "lecturerName" to examDetails.lecturerName,
            "courseName" to examDetails.courseName,
            "examTitle" to examDetails.examTitle,
            "examDate" to examDetails.examDate,
            "examDuration" to examDetails.examDuration,
            "examInstructions" to examDetails.examInstruction,
            "year" to LocalDateTime.now().year
        )

        try {
            sendEmail(
                inviteTemplateVariables,
                examDetails.receivers // List of email addresses
            )
        } catch (messagingException: MessagingException) {
            println(messagingException.message)
        }
    }

}
