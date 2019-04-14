package com.gmail.eugene.shchemelyov.editor.service;

import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;

public interface DocumentService {
    DocumentDTO save(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void deleteDocument(Long id);
}
