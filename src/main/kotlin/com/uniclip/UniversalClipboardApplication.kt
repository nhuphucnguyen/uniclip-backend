
package com.uniclip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UniversalClipboardApplication

fun main(args: Array<String>) {
    runApplication<UniversalClipboardApplication>(*args)
}