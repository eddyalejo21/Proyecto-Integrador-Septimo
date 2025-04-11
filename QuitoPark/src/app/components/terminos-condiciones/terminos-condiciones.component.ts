import { Component, OnInit } from '@angular/core';
import { ModalController, IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-terminos-condiciones',
  templateUrl: './terminos-condiciones.component.html',
  styleUrls: ['./terminos-condiciones.component.scss'],
  standalone: true,
  imports: [IonicModule]
})
export class TerminosCondicionesComponent  implements OnInit {

  constructor(
    private modalController: ModalController
  ) { }

  ngOnInit() {}

  cerrarComponente() {
    return this.modalController.dismiss();
  }

}
