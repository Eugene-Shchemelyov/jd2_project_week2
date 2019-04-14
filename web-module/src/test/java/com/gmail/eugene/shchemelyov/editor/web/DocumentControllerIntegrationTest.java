package com.gmail.eugene.shchemelyov.editor.web;

import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import com.gmail.eugene.shchemelyov.editor.web.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {AppConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class DocumentControllerIntegrationTest {
    private static final String DESCRIPTION = "description";

    @Autowired
    private DocumentController documentController;

    @Test
    public void shouldSaveDocumentDTO() {
        DocumentDTO documentDTO = new DocumentDTO();
        UUID uniqueNumber = UUID.randomUUID();
        documentDTO.setUniqueNumber(uniqueNumber);
        documentDTO.setDescription(DESCRIPTION);

        documentDTO = documentController.add(documentDTO);
        Assert.assertNotNull(documentDTO.getId());
        Assert.assertEquals(uniqueNumber, documentDTO.getUniqueNumber());
        Assert.assertEquals(DESCRIPTION, documentDTO.getDescription());
    }

    @Test
    public void shouldGetDocumentDTO() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription(DESCRIPTION);
        documentDTO = documentController.add(documentDTO);

        DocumentDTO foundDocumentDTO = documentController.getDocumentById(documentDTO.getId());
        Assert.assertNotNull(foundDocumentDTO.getId());
        Assert.assertNotNull(foundDocumentDTO.getUniqueNumber());
        Assert.assertNotNull(foundDocumentDTO.getDescription());
    }

    @Test
    public void shouldDeleteDocumentDTO() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription(DESCRIPTION);
        documentDTO = documentController.add(documentDTO);

        DocumentDTO foundDocumentDTO = documentController.getDocumentById(documentDTO.getId());
        documentController.deleteDocument(foundDocumentDTO.getId());
        Assert.assertNull(documentController.getDocumentById(foundDocumentDTO.getId()).getId());
        Assert.assertNull(documentController.getDocumentById(foundDocumentDTO.getId()).getUniqueNumber());
        Assert.assertNull(documentController.getDocumentById(foundDocumentDTO.getId()).getDescription());
    }
}
