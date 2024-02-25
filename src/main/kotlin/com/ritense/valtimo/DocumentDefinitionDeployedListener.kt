package com.ritense.valtimo

import com.ritense.document.domain.event.DocumentDefinitionDeployedEvent
import com.ritense.document.service.DocumentDefinitionService
import com.ritense.processdocument.domain.impl.request.ProcessDocumentDefinitionRequest
import com.ritense.processdocument.service.ProcessDocumentAssociationService
import com.ritense.valtimo.contract.authentication.AuthoritiesConstants.ADMIN
import com.ritense.valtimo.contract.authentication.AuthoritiesConstants.DEVELOPER
import com.ritense.valtimo.contract.authentication.AuthoritiesConstants.USER
import com.ritense.valtimo.domain.contexts.Context
import com.ritense.valtimo.domain.contexts.ContextProcess
import com.ritense.valtimo.service.ContextService
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Pageable

class DocumentDefinitionDeployedListener(
    private val processDocumentAssociationService: ProcessDocumentAssociationService,
    private val contextService: ContextService,
    private val documentDefinitionService: DocumentDefinitionService
) {

    @EventListener(DocumentDefinitionDeployedEvent::class)
    fun handle(documentDefinitionDeployedEvent: DocumentDefinitionDeployedEvent) {
        if (documentDefinitionDeployedEvent.documentDefinition().id().name() == DOCUMENT_DEFINITION_ID) {
            // Adds link
            processDocumentAssociationService.createProcessDocumentDefinition(
                ProcessDocumentDefinitionRequest(
                    PROCESS_DEFINITION_ID,
                    documentDefinitionDeployedEvent.documentDefinition().id().name(),
                    false
                )
            )
            // Ensure access
            documentDefinitionService.putDocumentDefinitionRoles(DOCUMENT_DEFINITION_ID, setOf(ADMIN, USER, DEVELOPER))

            // Manage context access
            contextService.findAll(Pageable.unpaged()).forEach { context: Context ->
                val process = ContextProcess(PROCESS_DEFINITION_ID, true)
                context.addProcess(process)
                contextService.save(context)
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
        const val PROCESS_DEFINITION_ID = "fill-me"
        const val DOCUMENT_DEFINITION_ID = "fill-me"
    }

}