import {Component, effect, inject, input} from '@angular/core';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {AuthService} from "../../service/auth/auth.service";
import {SongsService} from "../../service/songs/songs.service";
import {ReadSong} from "../../model/song.model";

@Component({
  selector: 'app-favorite-song-btn',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './favorite-song-btn.component.html',
  styleUrl: './favorite-song-btn.component.scss'
})
export class FavoriteSongBtnComponent {

  song = input.required<ReadSong>();

  authService = inject(AuthService);
  songService = inject(SongsService);

  constructor() {
    effect(() => {
      let favoriteSongState = this.songService.addOrRemoveFavoriteSongSig();
      if(favoriteSongState.status === "OK" && favoriteSongState.value
        && this.song().publicId === favoriteSongState.value.publicId) {
        this.song().favorite = favoriteSongState.value.favorite;
      }
    });
  }

  onFavorite(song: ReadSong) {
    this.songService.addOrRemoveAsFavorite(!song.favorite, song.publicId!);
  }

}
