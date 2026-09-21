import { Component, input, output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { SelectOption } from '../../../types/SelectOption';

@Component({
  selector: 'app-select',
  imports: [ReactiveFormsModule],
  templateUrl: './select.html',
  styleUrl: './select.css',
})
export class Select<T> {
  label = input<string>('');
  options = input<SelectOption<T>[]>([]);
  selected = input<T>();
  control = input.required<FormControl<T>>();
  onChange = output<T>();
}
