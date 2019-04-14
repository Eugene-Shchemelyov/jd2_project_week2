package com.gmail.eugene.shchemelyov.editor.service.impl;

import com.gmail.eugene.shchemelyov.editor.repository.DocumentRepository;
import com.gmail.eugene.shchemelyov.editor.repository.model.Document;
import com.gmail.eugene.shchemelyov.editor.service.DocumentService;
import com.gmail.eugene.shchemelyov.editor.service.converter.DocumentConverter;
import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
    private DocumentConverter documentConverter;
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentConverter documentConverter,
                               DocumentRepository documentRepository) {
        this.documentConverter = documentConverter;
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentDTO save(DocumentDTO documentDTO) {
        Document document = documentConverter.toDocument(documentDTO);
        Document savedDocument = documentRepository.save(document);
        return documentConverter.fromDocument(savedDocument);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.getDocumentById(id);
        if (document.isDeleted() == null || !document.isDeleted()) {
            return documentConverter.fromDocument(document);
        }
        return new DocumentDTO();
    }

    @Override
    public void deleteDocument(Long id) {
        Document document = new Document();
        document.setId(id);
        document.setDeleted(true);
        documentRepository.deleteDocument(document);
    }
}
