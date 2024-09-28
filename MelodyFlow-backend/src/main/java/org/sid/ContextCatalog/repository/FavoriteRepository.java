package org.sid.ContextCatalog.repository;


import org.sid.ContextCatalog.entities.Favorite;
import org.sid.ContextCatalog.entities.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findAllByUserEmailAndSongPublicIdIn(String email, List<UUID> songPublicIds);

}
