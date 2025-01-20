package com.uniclip.repository

import com.uniclip.model.ClipboardItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClipboardItemRepository : JpaRepository<ClipboardItem, Long> {
    fun findFirstByDeviceIdOrderByCreatedAtDesc(deviceId: String): ClipboardItem?
    fun findAllByDeviceIdOrderByCreatedAtDesc(deviceId: String): List<ClipboardItem>
}
