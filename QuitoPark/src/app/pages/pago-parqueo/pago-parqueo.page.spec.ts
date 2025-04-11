import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PagoParqueoPage } from './pago-parqueo.page';

describe('PagoParqueoPage', () => {
  let component: PagoParqueoPage;
  let fixture: ComponentFixture<PagoParqueoPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(PagoParqueoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
