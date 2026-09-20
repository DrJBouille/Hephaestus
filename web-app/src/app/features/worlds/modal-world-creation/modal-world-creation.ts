import { Component, inject, input, output, signal } from '@angular/core';
import { Modal } from '../../../shared/components/modal/modal/modal';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from '../../../shared/components/forms/button/button';
import { FormInput } from '../../../shared/components/forms/input/form-input';
import { Subtitle } from '../../../shared/components/text/subtitle/subtitle';
import { Title } from '../../../shared/components/text/title/title';
import { WorldService } from '../../../core/services/world/world-service';
import { ErrorHandlingService } from '../../../core/services/error-handling-service/error-handling-service';
import { SimpleButton } from '../../../shared/components/forms/simple-button/simple-button';

@Component({
  selector: 'app-modal-world-creation',
  imports: [
    Modal,
    FormsModule,
    ReactiveFormsModule,
    Button,
    FormInput,
    Subtitle,
    Title,
    SimpleButton,
  ],
  templateUrl: './modal-world-creation.html',
  styleUrl: './modal-world-creation.css',
})
export class ModalWorldCreation {
  isOpen = input<boolean>(false);
  close = output<void>();
  worldCreated = output<World>();

  private readonly formBuilder = inject(FormBuilder);
  private readonly worldService = inject(WorldService);
  private readonly errorHandlingService = inject(ErrorHandlingService);

  loading = signal(false);
  error = signal<string | null>(null);

  form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required]],
    file: [null as File | null, [Validators.required]],
  });

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    const { file, name } = this.form.getRawValue();
    if (!file) return;

    this.worldService.createWorld({ name: name }).subscribe({
      next: (world) => {
        this.worldService.upload(world.id, file).subscribe(() => {
          this.worldCreated.emit(world);
          this.close.emit();
        });
      },
      error: (err) => {
        this.errorHandlingService.set(err.error);
        this.error.set('An error occurred.');
        this.loading.set(false);
      },
    });
  }
}
