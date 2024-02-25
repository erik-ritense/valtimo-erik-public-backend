package com.ritense.valtimo.investigation1

import com.ritense.formflow.expression.FormFlowBean
import mu.KotlinLogging
import org.springframework.stereotype.Service

@FormFlowBean
@Service
class SampleService {

    fun logSomething() {
        logger.info( "Let's logs something")
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}