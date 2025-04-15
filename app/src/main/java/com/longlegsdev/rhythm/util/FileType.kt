package com.longlegsdev.rhythm.util

enum class FileType(val extensions: List<String>) {
    IMAGE(listOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "svg")),
    AUDIO(listOf("mp3", "wav", "m4a", "aac", "ogg", "flac")),
    VIDEO(listOf("mp4", "mov", "avi", "mkv", "webm", "flv")),
    DOCUMENT(listOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt")),
    ARCHIVE(listOf("zip", "rar", "7z", "tar", "gz")),
    UNKNOWN(emptyList());

    companion object {
        fun fromExtension(extension: String): FileType {
            val ext = extension.lowercase()
            return FileType.entries.firstOrNull { fileType ->
                fileType.extensions.contains(ext)
            } ?: UNKNOWN
        }

        fun fromUrl(url: String): FileType {
            val ext = url.substringAfterLast('.', "").lowercase()
            return fromExtension(ext)
        }
    }
}