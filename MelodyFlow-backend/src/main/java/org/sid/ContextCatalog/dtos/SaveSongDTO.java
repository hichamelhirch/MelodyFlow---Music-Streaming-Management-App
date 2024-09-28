package org.sid.ContextCatalog.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.sid.ContextCatalog.vo.SongAuthorVO;
import org.sid.ContextCatalog.vo.SongTitleVO;

public record SaveSongDTO(@Valid SongTitleVO title,
                          @Valid SongAuthorVO author,
                          @NotNull byte[] cover,
                          @NotNull String coverContentType,
                          @NotNull byte[] file,
                          @NotNull String fileContentType) {
}
