import { Component, OnInit } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';

@Component({
  selector: 'app-preguntas-frecuentes',
  templateUrl: './preguntas-frecuentes.component.html',
  styleUrls: ['./preguntas-frecuentes.component.scss'],
  standalone: true,
  imports: [IonicModule]
})
export class PreguntasFrecuentesComponent  implements OnInit {

  constructor(
    private modalController: ModalController
  ) { }

  ngOnInit() {}

  cerrarComponente() {
    return this.modalController.dismiss();
  }

}
