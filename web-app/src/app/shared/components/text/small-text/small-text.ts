import { Component, input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-small-text',
  imports: [NgClass],
  templateUrl: './small-text.html',
  styleUrl: './small-text.css',
})
export class SmallText {
  label = input<string>('');
  color = input<string>('');
}
