package com.gmail.eugene.shchemelyov.editor.service.converter;

import com.gmail.eugene.shchemelyov.editor.repository.model.Document;
import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;

public interface DocumentConverter {
    Document toDocument(DocumentDTO documentDTO);

    DocumentDTO fromDocument(Document document);
}
