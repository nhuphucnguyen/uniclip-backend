package com.uniclip.controller

import com.uniclip.dto.ClipboardItemRequest
import com.uniclip.dto.ClipboardItemResponse
import com.uniclip.service.ClipboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clipboard")
class ClipboardController(private val service: ClipboardService) {

    @PostMapping
    fun saveItem(@RequestBody request: ClipboardItemRequest): ResponseEntity<ClipboardItemResponse> {
        return ResponseEntity.ok(service.saveItem(request))
    }

    @GetMapping("/latest/{deviceId}")
    fun getLatestItem(@PathVariable deviceId: String): ResponseEntity<ClipboardItemResponse> {
        return service.getLatestItem(deviceId)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/device/{deviceId}")
    fun getItemsByDevice(@PathVariable deviceId: String): ResponseEntity<List<ClipboardItemResponse>> {
        return ResponseEntity.ok(service.getItemsByDevice(deviceId))
    }
}
