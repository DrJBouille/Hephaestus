import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalWorldCreation } from './modal-world-creation';

describe('ModalWorldCreation', () => {
  let component: ModalWorldCreation;
  let fixture: ComponentFixture<ModalWorldCreation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalWorldCreation],
    }).compileComponents();

    fixture = TestBed.createComponent(ModalWorldCreation);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
