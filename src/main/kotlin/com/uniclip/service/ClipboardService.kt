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
        val messageDigest = java.security.MessageDigest.getInstance("SHA-256")
        val content = when (type) {
            ClipboardItem.ClipboardType.TEXT -> textContent?.toByteArray() ?: ByteArray(0)
            ClipboardItem.ClipboardType.IMAGE -> binaryContent ?: ByteArray(0)
            else -> ByteArray(0)
        }
        val hashBytes = messageDigest.digest(content)
        return hashBytes.joinToString("") { "%02x".format(it) }
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

    @Transactional
    fun touchItem(contentHash: String): ClipboardItemResponse? {
        return repository.findByContentHash(contentHash)?.let { item ->
            val updatedItem = item.copy(updatedAt = LocalDateTime.now())
            repository.save(updatedItem).toResponse()
        }
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
