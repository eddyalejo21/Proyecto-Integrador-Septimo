import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { PreguntasFrecuentesComponent } from './preguntas-frecuentes.component';

describe('PreguntasFrecuentesComponent', () => {
  let component: PreguntasFrecuentesComponent;
  let fixture: ComponentFixture<PreguntasFrecuentesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PreguntasFrecuentesComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(PreguntasFrecuentesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
