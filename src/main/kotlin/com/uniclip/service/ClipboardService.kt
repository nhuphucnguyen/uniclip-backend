package com.uniclip.service

import com.uniclip.dto.ClipboardItemRequest
import com.uniclip.dto.ClipboardItemResponse
import com.uniclip.model.ClipboardItem
import com.uniclip.repository.ClipboardItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClipboardService(private val repository: ClipboardItemRepository) {

    @Transactional
    fun saveItem(request: ClipboardItemRequest): ClipboardItemResponse {
        val item = ClipboardItem(
            deviceId = request.deviceId,
            type = request.type,
            textContent = request.textContent,
            binaryContent = request.binaryContent
        )
        val savedItem = repository.save(item)
        return savedItem.toResponse()
    }

    fun getLatestItem(deviceId: String): ClipboardItemResponse? {
        return repository.findFirstByDeviceIdOrderByCreatedAtDesc(deviceId)?.toResponse()
    }

    fun getItemsByDevice(deviceId: String): List<ClipboardItemResponse> {
        return repository.findAllByDeviceIdOrderByCreatedAtDesc(deviceId).map { it.toResponse() }
    }

    private fun ClipboardItem.toResponse() = ClipboardItemResponse(
        id = id,
        deviceId = deviceId,
        type = type,
        textContent = textContent,
        binaryContent = binaryContent,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
