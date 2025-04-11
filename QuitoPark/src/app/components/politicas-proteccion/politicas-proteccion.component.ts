import { Component, OnInit } from '@angular/core';
import { Browser } from '@capacitor/browser';
import { IonicModule, ModalController } from '@ionic/angular';
import { IonHeader } from "@ionic/angular/standalone";

@Component({
  selector: 'app-politicas-proteccion',
  templateUrl: './politicas-proteccion.component.html',
  styleUrls: ['./politicas-proteccion.component.scss'],
  standalone: true,
  imports: [IonicModule]
})
export class PoliticasProteccionComponent implements OnInit {

  constructor(private modalController: ModalController) { }

  ngOnInit() { }

  cerrarComponente() {
    return this.modalController.dismiss();
  }

  async abrirPoliticasExterna(){
    await Browser.open({ url: 'https://www.placetopay.com/web/politicas-de-privacidad/' });
  }

}
