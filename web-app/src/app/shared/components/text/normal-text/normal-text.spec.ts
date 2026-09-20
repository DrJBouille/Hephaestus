import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NormalText } from './normal-text';

describe('NormalText', () => {
  let component: NormalText;
  let fixture: ComponentFixture<NormalText>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NormalText],
    }).compileComponents();

    fixture = TestBed.createComponent(NormalText);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
