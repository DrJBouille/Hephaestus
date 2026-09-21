import { Component, inject, input, output, signal } from '@angular/core';
import { Button } from '../../../shared/components/forms/button/button';
import { FormInput } from '../../../shared/components/forms/input/form-input';
import { Modal } from '../../../shared/components/modal/modal/modal';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SimpleButton } from '../../../shared/components/forms/simple-button/simple-button';
import { Subtitle } from '../../../shared/components/text/subtitle/subtitle';
import { Title } from '../../../shared/components/text/title/title';
import { World } from '../../../shared/types/world/World';
import { WorldService } from '../../../core/services/world/world-service';
import { ErrorHandlingService } from '../../../core/services/error-handling-service/error-handling-service';

@Component({
  selector: 'app-modal-upload-world',
  imports: [
    Button,
    FormInput,
    Modal,
    ReactiveFormsModule,
    SimpleButton,
    Subtitle,
    Title
  ],
  templateUrl: './modal-upload-world.html',
  styleUrl: './modal-upload-world.css',
})
export class ModalUploadWorld {
  world = input<World | null>(null);
  isOpen = input<boolean>(false);
  close = output<void>();
  worldUploaded = output<World>();

  private readonly formBuilder = inject(FormBuilder);
  private readonly worldService = inject(WorldService);
  private readonly errorHandlingService = inject(ErrorHandlingService);

  loading = signal(false);
  error = signal<string | null>(null);

  form = this.formBuilder.nonNullable.group({
    file: [null as File | null, [Validators.required]],
  });

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const world = this.world();
    if (!world) return;

    const { file } = this.form.getRawValue();
    if (!file) return;

    this.loading.set(true);
    this.error.set(null);

    this.worldService.upload(world.id, file).subscribe({
      next: world => {
        this.worldUploaded.emit(world);
        this.close.emit();
      },
      error: (err) => {
        this.errorHandlingService.set(err.error);
        this.error.set('An error occurred.');
        this.loading.set(false);
      },
    });
  }
}
