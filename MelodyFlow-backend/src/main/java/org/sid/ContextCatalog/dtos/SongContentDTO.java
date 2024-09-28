package org.sid.ContextCatalog.dtos;

import jakarta.persistence.Lob;

import java.util.UUID;

public record SongContentDTO(UUID publicId, @Lob byte[] file, String fileContentType) {
}
