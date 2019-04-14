package com.gmail.eugene.shchemelyov.editor.web;

import com.gmail.eugene.shchemelyov.editor.service.DocumentService;
import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import com.gmail.eugene.shchemelyov.editor.web.exception.NotNullException;
import com.gmail.eugene.shchemelyov.editor.web.exception.ValidateException;
import com.gmail.eugene.shchemelyov.editor.web.impl.DocumentControllerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {
    private DocumentController documentController;
    @Mock
    private DocumentService documentService;

    @Before
    public void init() {
        documentController = new DocumentControllerImpl(documentService);
    }

    @Test
    public void shouldSaveDocumentDTO() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(null);
        UUID uniqueNumber = UUID.randomUUID();
        documentDTO.setUniqueNumber(uniqueNumber);
        String description = "description";
        documentDTO.setDescription(description);

        DocumentDTO savedDocumentDTO = new DocumentDTO();
        savedDocumentDTO.setId(1L);
        savedDocumentDTO.setUniqueNumber(uniqueNumber);
        savedDocumentDTO.setDescription(description);

        when(documentService.save(documentDTO)).thenReturn(savedDocumentDTO);
        savedDocumentDTO = documentController.add(documentDTO);
        Assert.assertEquals(1L, savedDocumentDTO.getId().longValue());
        Assert.assertEquals(uniqueNumber, savedDocumentDTO.getUniqueNumber());
        Assert.assertEquals(description, savedDocumentDTO.getDescription());
    }

    @Test(expected = ValidateException.class)
    public void shouldDocumentDTOIdIsNull() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(1L);
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription("description");
        documentController.add(documentDTO);
    }

    @Test(expected = ValidateException.class)
    public void shouldDocumentDTOWithNotNullUUID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(null);
        documentDTO.setUniqueNumber(null);
        documentDTO.setDescription("description");
        documentController.add(documentDTO);
    }

    @Test(expected = ValidateException.class)
    public void shouldDocumentDTOWithNotNullDescription() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(null);
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription(null);
        documentController.add(documentDTO);
    }

    @Test(expected = ValidateException.class)
    public void shouldDocumentDTOWithDescriptionLessThanMaxDescriptionLength() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(null);
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrMoreThan100Symbols");
        documentController.add(documentDTO);
    }

    @Test(expected = NotNullException.class)
    public void shouldDocumentDTONotNull() {
        documentController.add(null);
    }

    @Test(expected = NotNullException.class)
    public void shouldIdNotNullOnGetDocument() {
        documentController.getDocumentById(null);
    }

    @Test(expected = NotNullException.class)
    public void shouldIdNotNullOnDeleteDocument() {
        documentController.deleteDocument(null);
    }
}
