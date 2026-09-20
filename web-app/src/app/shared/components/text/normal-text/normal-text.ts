import { Component, input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-normal-text',
  imports: [NgClass],
  templateUrl: './normal-text.html',
  styleUrl: './normal-text.css',
})
export class NormalText {
  label = input<string>('');
  color = input<string>('');
}
