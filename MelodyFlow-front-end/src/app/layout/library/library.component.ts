import {Component, effect, inject, OnInit} from '@angular/core';

import {RouterModule} from "@angular/router";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {SongsService} from "../../service/songs/songs.service";
import {SongContentService} from "../../service/songContent/song-content.service";
import {ReadSong} from "../../model/song.model";
import {SongSmallCardComponent} from "../../shared/song-small-card/song-small-card.component";

@Component({
  selector: 'app-library',
  standalone: true,
  imports: [FontAwesomeModule, RouterModule,SongSmallCardComponent],
  templateUrl: './library.component.html',
  styleUrl: './library.component.scss'
})
export class LibraryComponent implements OnInit {

  private songService = inject(SongsService);
  private songContentService = inject(SongContentService);

  songs: Array<ReadSong> = [];

  isLoading = false;


  constructor() {
    effect(() => {
      if(this.songService.getAllSig().status === "OK") {
        this.songs = this.songService.getAllSig().value!;
      }
      this.isLoading = false;
    });
  }

  ngOnInit(): void {
    this.fetchSongs();
  }

  private fetchSongs() {
    this.isLoading = true;
    this.songService.getAll();
  }

  onPlaySong(songToPlayFirst: ReadSong): void {
    this.songContentService.createNewQueue(songToPlayFirst, this.songs!);
  }
}
