import { Component, computed, input, output } from '@angular/core';

@Component({
  selector: 'app-checkbox',
  imports: [],
  templateUrl: './checkbox.html',
  styleUrl: './checkbox.css',
})
export class Checkbox {
  checked = input<boolean>(false);
  checkedChange = output<boolean>()
  size = input<number>(4);

  sizeClasses = computed(() => {
    const sizes: Record<number, string> = {
      4: 'w-4 h-4',
      6: 'w-6 h-6',
      8: 'w-8 h-8',
      12: 'w-12 h-12',
    };

    return sizes[this.size()] ?? sizes[4];
  });

  onChange(event: Event) {
    const checked = (event.target as HTMLInputElement).checked
    this.checkedChange.emit(checked);
  }
}
