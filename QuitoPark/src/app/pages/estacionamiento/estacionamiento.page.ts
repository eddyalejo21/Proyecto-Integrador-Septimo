import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';
import { Estacionamiento } from 'src/app/model/estacionamientos';
import { Browser } from '@capacitor/browser';

@Component({
  selector: 'app-estacionamiento',
  templateUrl: './estacionamiento.page.html',
  styleUrls: ['./estacionamiento.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class EstacionamientoPage implements OnInit {

  estacionamiento: Estacionamiento;
  tarifas: any = [];
  horarios: any = [];

  constructor() {
    const navigation = window.history.state;
    if (navigation.objeto){
      this.estacionamiento = navigation.objeto;
      this.tarifas = this.estacionamiento.tarifas;
      this.horarios = this.estacionamiento.horarios;
    }
   }

  ngOnInit() {
    // this.estacionamiento = this.activatedRoute.snapshot.paramMap.get('idEstacionamiento')
    console.log('Estacioanamiento ==> ', this.estacionamiento.estNombre);
  }

  async abrirMapa(){
    console.log('abrir', this.estacionamiento.estMaps);
    await Browser.open({ url: this.estacionamiento.estMaps });
  }

}
