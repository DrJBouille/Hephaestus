import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorldsPage } from './worlds-page';

describe('WorldsPage', () => {
  let component: WorldsPage;
  let fixture: ComponentFixture<WorldsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorldsPage],
    }).compileComponents();

    fixture = TestBed.createComponent(WorldsPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
