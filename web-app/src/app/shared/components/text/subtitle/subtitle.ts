import { Component, input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-subtitle',
  imports: [NgClass],
  templateUrl: './subtitle.html',
  styleUrl: './subtitle.css',
})
export class Subtitle {
  label = input<string>('');
  color = input<string>('');
}
