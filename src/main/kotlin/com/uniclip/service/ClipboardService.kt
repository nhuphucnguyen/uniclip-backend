package com.uniclip.service

import com.uniclip.dto.ClipboardItemRequest
import com.uniclip.dto.ClipboardItemResponse
import com.uniclip.model.ClipboardItem
import com.uniclip.repository.ClipboardItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ClipboardService(private val repository: ClipboardItemRepository) {

    private fun calculateContentHash(type: ClipboardItem.ClipboardType, textContent: String?, binaryContent: ByteArray?): String {
        return when (type) {
            ClipboardItem.ClipboardType.TEXT -> "text-${textContent?.hashCode()}"
            ClipboardItem.ClipboardType.IMAGE -> "image-${binaryContent?.contentHashCode()}"
            else -> "unknown-${System.currentTimeMillis()}"
        }
    }

    @Transactional
    fun saveItem(request: ClipboardItemRequest): ClipboardItemResponse {
        val binaryContent = request.getBinaryContentAsBytes()
        val contentHash = calculateContentHash(request.type, request.textContent, binaryContent)
        
        // Check if item with same hash exists
        val existingItem = repository.findByContentHash(contentHash)
        
        val item = if (existingItem != null) {
            // Update the timestamp of existing item
            existingItem.copy(updatedAt = LocalDateTime.now())
        } else {
            // Create new item
            ClipboardItem(
                type = request.type,
                textContent = request.textContent,
                binaryContent = binaryContent,
                contentHash = contentHash
            )
        }
        
        val savedItem = repository.save(item)
        return savedItem.toResponse()
    }

    fun getLatestItem(): ClipboardItemResponse? {
        return repository.findFirstByOrderByUpdatedAtDesc()?.toResponse()
    }

    fun getItems(): List<ClipboardItemResponse> {
        return repository.findAllByOrderByUpdatedAtDesc().map { it.toResponse() }
    }

    private fun ClipboardItem.toResponse() = ClipboardItemResponse(
        id = id,
        type = type,
        textContent = textContent,
        binaryContent = binaryContent,
        contentHash = contentHash,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
