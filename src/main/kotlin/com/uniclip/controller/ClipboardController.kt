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

    @GetMapping("/latest")
    fun getLatestItem(): ResponseEntity<ClipboardItemResponse> {
        return service.getLatestItem()?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getItems(): ResponseEntity<List<ClipboardItemResponse>> {
        return ResponseEntity.ok(service.getItems())
    }
}
