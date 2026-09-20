import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { SmallError } from '../small-error/small-error';

@Component({
  selector: 'app-input',
  imports: [ReactiveFormsModule, SmallError],
  templateUrl: './form-input.html',
  styleUrl: './form-input.css',
})
export class FormInput {
  label = input<string>('');
  placeholder = input<string>('');
  type = input<string>('text');
  control = input.required<FormControl>();

  onChange(event: Event) {
    if (this.type() !== 'file') {
      return;
    }

    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    this.control().setValue(file);
    this.control().markAsTouched();
  }
}
