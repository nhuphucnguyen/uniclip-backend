package com.uniclip.dto

import com.uniclip.model.ClipboardItem
import java.time.LocalDateTime
import java.util.Base64

data class ClipboardItemRequest(
    val type: ClipboardItem.ClipboardType,
    val textContent: String? = null,
    val binaryContent: String? = null // Now accepts base64 encoded string
) {
    fun getBinaryContentAsBytes(): ByteArray? =
        binaryContent?.let { Base64.getDecoder().decode(it) }
}

data class ClipboardItemResponse(
    val id: Long,
    val type: ClipboardItem.ClipboardType,
    val textContent: String?,
    private val binaryContent: ByteArray?,
    val contentHash: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    // Expose binary content as base64 string
    val base64BinaryContent: String?
        get() = binaryContent?.let { Base64.getEncoder().encodeToString(it) }

    // Don't include binaryContent in toString() to avoid large output
    override fun toString(): String {
        return "ClipboardItemResponse(id=$id, type=$type, " +
               "textContent=$textContent, createdAt=$createdAt, updatedAt=$updatedAt, contentHash='$contentHash')"
    }
}
