import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallError } from './small-error';

describe('SmallError', () => {
  let component: SmallError;
  let fixture: ComponentFixture<SmallError>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SmallError],
    }).compileComponents();

    fixture = TestBed.createComponent(SmallError);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
