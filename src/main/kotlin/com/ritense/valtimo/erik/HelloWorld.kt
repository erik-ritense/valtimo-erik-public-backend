package com.ritense.valtimo.erik

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class HelloWorld {
    fun helloWorld(input: String, message: String) {

        logger.warn { "$input , $message" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
