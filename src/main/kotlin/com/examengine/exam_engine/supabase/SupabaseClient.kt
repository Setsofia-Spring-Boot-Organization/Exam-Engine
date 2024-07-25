package com.examengine.exam_engine.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

val supabase = createSupabaseClient(
    supabaseUrl = "https://ghpwscjqhxdixgyvnack.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdocHdzY2pxaHhkaXhneXZuYWNrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwOTQ5MzMsImV4cCI6MjAzMzY3MDkzM30.0MUoRxbFpkg_oOYMESgqD0kdj_KdbUEmnxCC-Sb2sH0"
) {
    install(Postgrest)
    install(Realtime)
    install(Storage)
}
@Component
class SupabaseClient {

    suspend fun saveImage(studentId: String, questionId: String, images: List<MultipartFile>): ResponseEntity<ArrayList<String>> {
        val uploadedImageURLs = ArrayList<String>()

        try {
            val bucket = supabase.storage.from("images")
            val processedImages = HashMap<String, ByteArray>()

            for (image in images) {
                val imageByteArray = withContext(Dispatchers.IO) {
                    image.bytes
                }
                val sanitizedFileName = sanitizeFileName(image.originalFilename!!.trim())
                processedImages[sanitizedFileName] = imageByteArray
            }

            for (processedImage in processedImages) {
                val fileName = "${studentId+questionId}/${LocalDateTime.now().toString() + processedImage.key}"
                bucket.upload(fileName, processedImage.value, false)

                val imageUrl = bucket.publicUrl(fileName)
                uploadedImageURLs.add(imageUrl)
            }

            // retrieve all the students screenshots after saving
            println(uploadedImageURLs)

        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity.ok(uploadedImageURLs)
    }

    private fun sanitizeFileName(fileName: String): String {
        return fileName
            .replace(" ", "")
            .replace("(", "")
            .replace(")", "")
    }
}