package org.sid.ContextCatalog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.ContextCatalog.entities.SongContent;
import org.sid.ContextCatalog.dtos.SaveSongDTO;
import org.sid.ContextCatalog.dtos.SongContentDTO;


@Mapper(componentModel = "spring")
public interface SongContentMapper {

    @Mapping(source = "song.publicId", target = "publicId")
    SongContentDTO songContentToSongContentDTO(SongContent songContent);


    SongContent saveSongDTOToSong(SaveSongDTO songDTO);

}
