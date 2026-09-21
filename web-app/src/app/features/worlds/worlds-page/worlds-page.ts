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
import { ModalUploadWorld } from '../modal-upload-world/modal-upload-world';
import { WorldStatus } from '../../../shared/types/world/WorldStatus';

@Component({
  selector: 'app-worlds-page',
  imports: [
    Button,
    ModalWorldCreation,
    NormalText,
    DatePipe,
    Title,
    SmallText,
    SimpleButton,
    ModalUploadWorld,
  ],
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

  protected selectedWorld: World | null = null;

  protected isOpenWorldCreationModal = false;
  protected isOpenUploadWorldModal = false;

  ngOnInit() {
    this.worldService.getWorlds(0, 20).subscribe((result) => this.data.set(result));
  }

  toggleWorldCreationModal() {
    this.isOpenWorldCreationModal = !this.isOpenWorldCreationModal;
  }

  toggleOpenUploadWorldModal(world: World | null) {
    this.isOpenUploadWorldModal = !this.isOpenUploadWorldModal;
    this.selectedWorld = world;
  }

  addWorld(world: World) {
    const newData = this.data();
    newData.content.push(world);
    this.data.set(newData);
  }

  updateWorld(world: World) {
    const newData = this.data();
    this.data.set({
      ...newData,
      content: newData.content.map(w => w.id === world.id ? world : w)
    });
  }

  download(world: World) {
    this.worldService.download(world.id).subscribe({
      next: (response) => {
        const blob = response.body;

        if (!blob) return;

        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `${world.name}.zip`;
        link.click();

        URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.log('Download failed', err);
      },
    });
  }

  protected readonly WorldStatus = WorldStatus;
}
