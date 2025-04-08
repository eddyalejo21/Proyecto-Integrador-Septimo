import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EstacionamientoPage } from './estacionamiento.page';

describe('EstacionamientoPage', () => {
  let component: EstacionamientoPage;
  let fixture: ComponentFixture<EstacionamientoPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EstacionamientoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
