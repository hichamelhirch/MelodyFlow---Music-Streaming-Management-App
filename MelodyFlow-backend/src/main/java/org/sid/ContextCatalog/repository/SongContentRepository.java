package org.sid.ContextCatalog.repository;

import org.sid.ContextCatalog.entities.SongContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SongContentRepository extends JpaRepository<SongContent, Long> {

     Optional<SongContent> findOneBySongPublicId(UUID publicId);

}
