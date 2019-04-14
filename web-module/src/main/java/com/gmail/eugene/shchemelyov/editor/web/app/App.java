package com.gmail.eugene.shchemelyov.editor.web.app;

import com.gmail.eugene.shchemelyov.editor.service.model.DocumentDTO;
import com.gmail.eugene.shchemelyov.editor.web.DocumentController;
import com.gmail.eugene.shchemelyov.editor.web.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription("current description");

        DocumentController documentController = context.getBean(DocumentController.class);
        DocumentDTO newDocumentDTO = documentController.add(documentDTO);
        logger.info(newDocumentDTO);

        DocumentDTO foundDocumentDTO = documentController.getDocumentById(newDocumentDTO.getId());
        logger.info(foundDocumentDTO);

        DocumentDTO notFoundDocumentDTO = documentController.getDocumentById(10000L);
        logger.info(notFoundDocumentDTO);

        documentController.deleteDocument(foundDocumentDTO.getId());

        logger.info(documentController.getDocumentById(foundDocumentDTO.getId()));

        documentController.deleteDocument(10000L);
    }
}
