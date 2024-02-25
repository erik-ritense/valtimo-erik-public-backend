package com.ritense.valtimo.erik

import com.fasterxml.jackson.databind.node.ObjectNode
import com.ritense.document.domain.Document
import com.ritense.document.domain.impl.JsonDocumentContent
import com.ritense.document.domain.impl.JsonSchemaDocumentId
import com.ritense.document.domain.impl.request.ModifyDocumentRequest
import com.ritense.document.exception.DocumentNotFoundException
import com.ritense.document.service.DocumentService
import com.ritense.formflow.expression.FormFlowBean
import org.camunda.bpm.engine.TaskService
import org.springframework.stereotype.Service
import java.util.*

@FormFlowBean
@Service
class GoWithTheFlowService(
    protected val taskService: TaskService,
    protected val documentService: DocumentService

) {
    fun go() {
        println("FLOW")
    }

    fun storeSubmission(submissionData: ObjectNode, additionalProperties: Map<String, Any>) {

        modifyDocument(
            additionalProperties["documentId"].toString(),
            submissionData.toPrettyString()
        )
    }

    fun finishTask(additionalProperties: Map<String, Any>) {
        additionalProperties.takeIf { it.containsKey("taskInstanceId") }
            ?.apply { taskService.complete(this.get("taskInstanceId")?.toString()) }
    }

    fun modifyDocument(businessKey: String?, json: String?) {
        documentService.findBy(JsonSchemaDocumentId.existingId(UUID.fromString(businessKey)))
            .ifPresentOrElse({ document: Document ->
                val jsonNode = JsonDocumentContent(json).asJson()
                val modifyDocumentRequest = ModifyDocumentRequest(
                    document.id().toString(), jsonNode, document.version().toString()
                )
                val modifyDocumentResult = documentService.modifyDocument(modifyDocumentRequest)
                if (modifyDocumentResult.errors().size > 0) {
                    throw RuntimeException("Document not updated!")
                }
            }) { throw DocumentNotFoundException("Document not found!") }
    }
}