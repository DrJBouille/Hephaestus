import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUploadWorld } from './modal-upload-world';

describe('ModalUploadWorld', () => {
  let component: ModalUploadWorld;
  let fixture: ComponentFixture<ModalUploadWorld>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalUploadWorld],
    }).compileComponents();

    fixture = TestBed.createComponent(ModalUploadWorld);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
