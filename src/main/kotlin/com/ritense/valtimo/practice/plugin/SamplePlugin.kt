package com.ritense.valtimo.practice.plugin

import com.ritense.plugin.annotation.Plugin
import com.ritense.plugin.annotation.PluginAction
import com.ritense.plugin.annotation.PluginProperty
import com.ritense.plugin.domain.ActivityType
import org.camunda.bpm.engine.delegate.DelegateExecution

@Plugin(
    key = "sample",
    title = "Sample Plugin",
    description = "Sample plugin description"
)
class SamplePlugin(
    val sampleClient: SampleClient
) {

    @PluginProperty(key = "username", secret = false)
    private lateinit var username: String

    @PluginAction(
        key = "post-tweet",
        title = "Post tweet",
        description = "Post a tweet on twitter.",
        activityTypes = [ActivityType.SERVICE_TASK_START]
    )
    fun samplePrinter(execution: DelegateExecution) {
            sampleClient.hello()
    }

}