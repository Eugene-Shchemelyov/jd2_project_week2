package com.gmail.eugene.shchemelyov.editor.web.impl;

import com.gmail.eugene.shchemelyov.editor.service.DocumentService;
import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import com.gmail.eugene.shchemelyov.editor.web.DocumentController;
import com.gmail.eugene.shchemelyov.editor.web.exception.NotNullException;
import com.gmail.eugene.shchemelyov.editor.web.exception.ValidateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.gmail.eugene.shchemelyov.editor.web.constant.WebConstant.MAX_DESCRIPTION_LENGTH;

@Controller
public class DocumentControllerImpl implements DocumentController {
    private DocumentService documentService;
    private static final Logger logger = LogManager.getLogger(DocumentControllerImpl.class);

    @Autowired
    public DocumentControllerImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        validate(documentDTO);
        return documentService.save(documentDTO);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        checkNotNullId(id);
        return documentService.getDocumentById(id);
    }

    @Override
    public void deleteDocument(Long id) {
        checkNotNullId(id);
        documentService.deleteDocument(id);
    }

    private void checkNotNullId(Long id) {
        if (id == null) {
            logger.error("Document id is null");
            throw new NotNullException("Not null exception on document id");
        }
    }

    private void validate(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            logger.error("Document is null");
            throw new NotNullException("Not null exception on document");
        }
        if (documentDTO.getId() != null) {
            logger.error("Document id isn't null");
            throw new ValidateException("Validate exception on document id");
        }
        if (documentDTO.getUniqueNumber() == null) {
            logger.error("Document UUID is null");
            throw new ValidateException("Validate exception on document UUID");
        }
        if (documentDTO.getDescription() == null) {
            logger.error("Description is null");
            throw new ValidateException("Validate exception on document description");
        }
        if (documentDTO.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            logger.error(String.format("%s %d", "Document description more than", MAX_DESCRIPTION_LENGTH));
            throw new ValidateException(String.format(
                    "%s %d", "Validate exception on document description more than", MAX_DESCRIPTION_LENGTH));
        }
    }
}
