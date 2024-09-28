import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SongSmallCardComponent } from './song-small-card.component';

describe('SongSmallCardComponent', () => {
  let component: SongSmallCardComponent;
  let fixture: ComponentFixture<SongSmallCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SongSmallCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SongSmallCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
