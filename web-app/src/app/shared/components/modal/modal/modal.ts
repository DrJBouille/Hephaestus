import { Component, input, Input, output } from '@angular/core';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './modal.html',
  styleUrl: './modal.css',
})
export class Modal {
  isOpen = input<boolean>(false);
  close = output<void>();
}
