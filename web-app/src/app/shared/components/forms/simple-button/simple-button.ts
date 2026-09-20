import { Component, input } from '@angular/core';

@Component({
  selector: 'app-simple-button',
  imports: [],
  templateUrl: './simple-button.html',
  styleUrl: './simple-button.css',
})
export class SimpleButton {
  disabled = input<boolean>(false);
  label = input<string>();
}
