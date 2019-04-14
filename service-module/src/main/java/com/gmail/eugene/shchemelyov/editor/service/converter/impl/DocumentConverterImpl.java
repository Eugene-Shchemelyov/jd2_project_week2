package com.gmail.eugene.shchemelyov.editor.service.converter.impl;

import com.gmail.eugene.shchemelyov.editor.repository.model.Document;
import com.gmail.eugene.shchemelyov.editor.service.converter.DocumentConverter;
import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import org.springframework.stereotype.Service;

import static com.gmail.eugene.shchemelyov.editor.service.constant.ServiceConstant.DELETED;

@Service
public class DocumentConverterImpl implements DocumentConverter {
    @Override
    public Document toDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setId(documentDTO.getId());
        document.setUniqueNumber(documentDTO.getUniqueNumber());
        document.setDescription(documentDTO.getDescription());
        document.setDeleted(DELETED);
        return document;
    }

    @Override
    public DocumentDTO fromDocument(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }
}
