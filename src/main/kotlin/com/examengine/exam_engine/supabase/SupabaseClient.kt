package com.examengine.exam_engine.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

val supabase = createSupabaseClient(
    supabaseUrl = "https://ghpwscjqhxdixgyvnack.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdocHdzY2pxaHhkaXhneXZuYWNrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwOTQ5MzMsImV4cCI6MjAzMzY3MDkzM30.0MUoRxbFpkg_oOYMESgqD0kdj_KdbUEmnxCC-Sb2sH0"
) {
    install(Postgrest)
    install(Realtime)
}
@Service
class SupabaseClient {

    fun saveImage(imageFile: MultipartFile, imageName: String): ResponseEntity<String> {
        try {
//            val result = supabase.storage.from("images")
//                .upload(
//                    path = "$imageName.png",
//                    data = imageFile.bytes,
//                    upsert = true
//                )
            return ResponseEntity.ok("result")
        } catch (exception: Exception) {
            throw exception
        }
    }
}