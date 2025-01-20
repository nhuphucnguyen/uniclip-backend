package com.uniclip.dto

import com.uniclip.model.ClipboardItem
import java.time.LocalDateTime

data class ClipboardItemRequest(
    val deviceId: String,
    val type: ClipboardItem.ClipboardType,
    val textContent: String? = null,
    val binaryContent: ByteArray? = null
)

data class ClipboardItemResponse(
    val id: Long,
    val deviceId: String,
    val type: ClipboardItem.ClipboardType,
    val textContent: String?,
    val binaryContent: ByteArray?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
