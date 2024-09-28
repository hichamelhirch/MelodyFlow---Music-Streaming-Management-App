package org.sid.ContextCatalog.service;

import org.sid.ContextCatalog.dtos.FavoriteSongDTO;
import org.sid.ContextCatalog.dtos.SongContentDTO;
import org.sid.ContextCatalog.entities.Favorite;
import org.sid.ContextCatalog.entities.FavoriteId;
import org.sid.ContextCatalog.mappers.SongContentMapper;
import org.sid.ContextCatalog.mappers.SongContentMapperImpl;
import org.sid.ContextCatalog.mappers.SongMapper;

import org.sid.ContextCatalog.repository.FavoriteRepository;
import org.sid.ContextCatalog.repository.SongContentRepository;
import org.sid.ContextCatalog.repository.SongRepository;
import org.sid.ContextCatalog.dtos.ReadSongInfoDTO;
import org.sid.ContextCatalog.dtos.SaveSongDTO;
import org.sid.ContextCatalog.entities.Song;
import org.sid.ContextCatalog.entities.SongContent;
import org.sid.ContextUser.dtos.ReadUserDTO;
import org.sid.ContextUser.service.UserService;
import org.sid.infrastructure.dtos.State;
import org.sid.infrastructure.dtos.StateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongService {

    private final SongMapper songMapper;

    private final SongRepository songRepository;

    private final SongContentRepository songContentRepository;

    private final SongContentMapper songContentMapper;

    private final UserService userService;

    private final FavoriteRepository favoriteRepository;

    public SongService(SongMapper songMapper, SongRepository songRepository,
                       SongContentRepository songContentRepository, SongContentMapper songContentMapper, UserService userService, FavoriteRepository favoriteRepository) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.songContentRepository = songContentRepository;
        this.songContentMapper = songContentMapper;
        this.userService = userService;
        this.favoriteRepository = favoriteRepository;
    }

    public ReadSongInfoDTO create(SaveSongDTO saveSongDTO) {
        Song song = songMapper.saveSongDTOToSong(saveSongDTO);
        Song songSaved = songRepository.save(song);

        SongContent songContent = songContentMapper.saveSongDTOToSong(saveSongDTO);
        songContent.setSong(songSaved);

        songContentRepository.save(songContent);
        return songMapper.songToReadSongInfoDTO(songSaved);
    }

    @Transactional(readOnly = true)
    public List<ReadSongInfoDTO> getAll() {

        List<ReadSongInfoDTO> allSongs = songRepository.findAll()
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();

        if(userService.isAuthenticated()) {
            return fetchFavoritesStatusForSongs(allSongs);
        }

        return allSongs;
    }

    public Optional<SongContentDTO> getOneByPublicId(UUID publicId) {
        Optional<SongContent> songByPublicId = songContentRepository.findOneBySongPublicId(publicId);
        return songByPublicId.map(songContentMapper::songContentToSongContentDTO);
    }

    public List<ReadSongInfoDTO> search(String searchTerm) {
        List<ReadSongInfoDTO> searchedSongs = songRepository.findByTitleOrAuthorContaining(searchTerm)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .collect(Collectors.toList());

        if(userService.isAuthenticated()) {
            return fetchFavoritesStatusForSongs(searchedSongs);
        } else {
            return searchedSongs;
        }
    }

    public State<FavoriteSongDTO, String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email) {
        StateBuilder<FavoriteSongDTO, String> builder = State.builder();
        Optional<Song> songToLikeOpt = songRepository.findOneByPublicId(favoriteSongDTO.publicId());
        if (songToLikeOpt.isEmpty()) {
            return builder.forError("Song public id doesn't exist").build();
        }

        Song songToLike = songToLikeOpt.get();

        ReadUserDTO userWhoLikedSong = userService.getByEmail(email).orElseThrow();

        if (favoriteSongDTO.favorite()) {
            Favorite favorite = new Favorite();
            favorite.setSongPublicId(songToLike.getPublicId());
            favorite.setUserEmail(userWhoLikedSong.email());
            favoriteRepository.save(favorite);
        } else {
            FavoriteId favoriteId =new FavoriteId(songToLike.getPublicId(), userWhoLikedSong.email());
            favoriteRepository.deleteById(favoriteId);
            favoriteSongDTO = new FavoriteSongDTO(false, songToLike.getPublicId());
        }

        return builder.forSuccess(favoriteSongDTO).build();
    }

    public List<ReadSongInfoDTO> fetchFavoriteSongs(String email) {
        return songRepository.findAllFavoriteByUserEmail(email)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();
    }

    private List<ReadSongInfoDTO> fetchFavoritesStatusForSongs(List<ReadSongInfoDTO> songs) {
        ReadUserDTO authenticatedUser = userService.getAuthenticatedUserFromSecurityContext();

        List<UUID> songPublicIds = songs.stream().map(ReadSongInfoDTO::getPublicId).toList();

        List<UUID> userFavoriteSongs = favoriteRepository.findAllByUserEmailAndSongPublicIdIn(authenticatedUser.email(), songPublicIds)
                .stream().map(Favorite::getSongPublicId).toList();

        return songs.stream().peek(song -> {
            if (userFavoriteSongs.contains(song.getPublicId())) {
                song.setFavorite(true);
            }
        }).toList();
    }
}
