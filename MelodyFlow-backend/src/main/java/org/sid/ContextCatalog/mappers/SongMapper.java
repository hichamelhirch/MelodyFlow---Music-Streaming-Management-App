package org.sid.ContextCatalog.mappers;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.ContextCatalog.entities.Song;
import org.sid.ContextCatalog.dtos.ReadSongInfoDTO;
import org.sid.ContextCatalog.dtos.SaveSongDTO;
import org.sid.ContextCatalog.vo.SongAuthorVO;
import org.sid.ContextCatalog.vo.SongTitleVO;


@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    Song saveSongDTOToSong(SaveSongDTO saveSongDTO);

    @Mapping(target = "favorite", ignore = true)
    ReadSongInfoDTO songToReadSongInfoDTO(Song song);

    default SongTitleVO stringToSongTitleVO(String title) {
        return new SongTitleVO(title);
    }

    default SongAuthorVO stringToSongAuthorVO(String author) {
        return new SongAuthorVO(author);
    }

    default String songTitleVOToString(SongTitleVO title) {
        return title.value();
    }

    default String songAuthorVOToString(SongAuthorVO author) {
        return author.value();
    }
}
