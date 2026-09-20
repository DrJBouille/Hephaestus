import { Component, input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-title',
  imports: [NgClass],
  templateUrl: './title.html',
  styleUrl: './title.css',
})
export class Title {
  label = input<string>('');
  color = input<string>('');
}
