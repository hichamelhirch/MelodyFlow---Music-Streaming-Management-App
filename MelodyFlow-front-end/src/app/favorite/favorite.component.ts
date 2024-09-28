import {Component, effect, inject} from '@angular/core';
import {ReadSong} from "../model/song.model";
import {SongsService} from "../service/songs/songs.service";
import {SongContentService} from "../service/songContent/song-content.service";
import {SongSmallCardComponent} from "../shared/song-small-card/song-small-card.component";
import {FavoriteSongBtnComponent} from "../shared/favorite-song-btn/favorite-song-btn.component";
import {FavoriteSongCardComponent} from "../home/favorite-song-card/favorite-song-card.component";

@Component({
  selector: 'app-favorite',
  standalone: true,
  imports: [
    FavoriteSongBtnComponent,
    SongSmallCardComponent,
    FavoriteSongCardComponent
  ],
  templateUrl: './favorite.component.html',
  styleUrl: './favorite.component.scss'
})
export class FavoriteComponent {
  favoriteSongs: Array<ReadSong> = [];

  songService = inject(SongsService);

  songContentService = inject(SongContentService);

  constructor() {
    effect(() => {
      let addOrRemoveFavoriteSongSig = this.songService.addOrRemoveFavoriteSongSig();
      if(addOrRemoveFavoriteSongSig.status === "OK") {
        this.songService.fetchFavorite();
      }
    });

    effect(() => {
      let favoriteSongState = this.songService.fetchFavoriteSongSig();
      if(favoriteSongState.status === "OK") {
        favoriteSongState.value?.forEach(song => song.favorite = true)
        this.favoriteSongs = favoriteSongState.value!;
      }
    });
  }

  ngOnInit(): void {
    this.songService.fetchFavorite();
  }

  onPlay(firstSong: ReadSong) {
    this.songContentService.createNewQueue(firstSong, this.favoriteSongs);
  }
}
