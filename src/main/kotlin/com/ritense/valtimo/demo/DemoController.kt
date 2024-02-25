package com.ritense.valtimo.demo

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


@RestController
class DemoController {

    @GetMapping("/api/parallel")
    suspend fun parallel(): String = withContext(Dispatchers.IO){

        val client = webClient()

        val hi1: Deferred<String> = async {
            println("hi1")
            client
                .get()
                .uri("/api/endpoint1")
                .retrieve()
                .awaitBody<String>()
        }
        val hi2: Deferred<String> = async {
            println("hi2")
            client
                .get()
                .uri("/api/endpoint2")
                .retrieve()
                .awaitBody<String>()
        }

        hi1.await() +  hi2.await()
    }

    private fun webClient(): WebClient {

        return WebClient.builder()
            .baseUrl("http://localhost:8081")
            .clone()
            .build()
    }

}
