import { Component, inject, OnInit, signal } from '@angular/core';
import { WorldService } from '../../../core/services/world/world-service';
import { Button } from '../../../shared/components/forms/button/button';
import { ModalWorldCreation } from '../modal-world-creation/modal-world-creation';
import { NormalText } from '../../../shared/components/text/normal-text/normal-text';
import { DatePipe } from '@angular/common';
import { Title } from '../../../shared/components/text/title/title';
import { SmallText } from '../../../shared/components/text/small-text/small-text';
import { SimpleButton } from '../../../shared/components/forms/simple-button/simple-button';
import { World } from '../../../shared/types/world/World';

@Component({
  selector: 'app-worlds-page',
  imports: [Button, ModalWorldCreation, NormalText, DatePipe, Title, SmallText, SimpleButton],
  templateUrl: './worlds-page.html',
  styleUrl: './worlds-page.css',
})
export class WorldsPage implements OnInit {
  private worldService = inject(WorldService);
  protected data = signal<PageResult<World>>({
    content: [],
    page: 0,
    totalPages: 0,
    size: 0,
    totalElements: 0,
  });

  protected isOpenWorldCreationModal = false;

  ngOnInit() {
    this.worldService.getWorlds(0, 20).subscribe((result) => this.data.set(result));
  }

  toggleWorldCreationModal() {
    this.isOpenWorldCreationModal = !this.isOpenWorldCreationModal;
  }

  addWorld(world: World) {
    const newData = this.data();
    newData.content.push(world)
    this.data.set(newData);
  }

  download(world: World) {
    this.worldService.download(world.id).subscribe({
      next: response => {
        const blob = response.body;

        if (!blob) return;

        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `${world.name}.zip`;
        link.click();

        URL.revokeObjectURL(url);
      },
      error: err => {
        console.log("Download failed", err);
      }
    });
  }
}
