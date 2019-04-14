package com.gmail.eugene.shchemelyov.editor.service.model;

import java.util.UUID;

public class DocumentDTO {
    private Long id;
    private UUID uniqueNumber;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(UUID uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "id=" + id +
                ", uniqueNumber=" + uniqueNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
