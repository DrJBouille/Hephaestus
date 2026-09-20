import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallText } from './small-text';

describe('SmallText', () => {
  let component: SmallText;
  let fixture: ComponentFixture<SmallText>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SmallText],
    }).compileComponents();

    fixture = TestBed.createComponent(SmallText);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
