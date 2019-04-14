package com.gmail.eugene.shchemelyov.editor.repository;

import com.gmail.eugene.shchemelyov.editor.repository.model.Document;

public interface DocumentRepository {
    Document save(Document document);

    Document getDocumentById(Long id);

    void deleteDocument(Document document);
}
