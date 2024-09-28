import {Component, EventEmitter, input, Output} from '@angular/core';
import {ReadSong} from "../../model/song.model";

@Component({
  selector: 'app-song-small-card',
  standalone: true,
  imports: [],
  templateUrl: './song-small-card.component.html',
  styleUrl: './song-small-card.component.scss'
})
export class SongSmallCardComponent {

  song = input.required<ReadSong>();

  @Output()
  songToPlay$ = new EventEmitter<ReadSong>();

  play(): void {
    this.songToPlay$.next(this.song())
  }

}
