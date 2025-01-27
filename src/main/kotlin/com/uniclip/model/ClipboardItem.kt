package com.uniclip.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.time.LocalDateTime

@Entity
@Table(name = "clipboard_items")
data class ClipboardItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val type: ClipboardType,

    @Column(columnDefinition = "TEXT")
    val textContent: String? = null,

    @JdbcTypeCode(Types.VARBINARY)
    val binaryContent: ByteArray? = null,

    @Column(nullable = false)
    val contentHash: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    enum class ClipboardType {
        TEXT, IMAGE, FILE
    }
}
