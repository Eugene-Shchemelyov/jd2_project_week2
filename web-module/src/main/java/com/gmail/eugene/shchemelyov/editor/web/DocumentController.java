package com.gmail.eugene.shchemelyov.editor.web;

import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;

public interface DocumentController {
    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void deleteDocument(Long id);
}
