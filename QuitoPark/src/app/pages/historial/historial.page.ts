import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { IonicModule, ViewDidEnter } from '@ionic/angular';
import { IonHeader } from "@ionic/angular/standalone";
import { Transaccion } from 'src/app/model/transaccion';
import { Usuario } from 'src/app/model/usuarios';
import { PagoService } from 'src/app/services/pago.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import { AlertasService } from '../../services/alertas.service';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.page.html',
  styleUrls: ['./historial.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class HistorialPage implements ViewDidEnter {

  private usuarioService = inject(UsuariosService);
  private pagosService = inject(PagoService);
  private alertasService = inject(AlertasService)

  usuario: Usuario = {};
  listaHistorial: Transaccion[] = [];

  constructor() { }

  ngOnInit() {
  }

  ionViewDidEnter() {
    this.usuario = this.usuarioService.getUsuario();
    console.log(this.usuario.usuSecuencial);

    if (this.usuario.usuSecuencial) {
      this.cargarHistorial();
    } else {
      this.alertasService.alertaSinSesion('Sesión Requerida', 'Debe tener iniciada una sesión para continuar con este servicio')
    }
  }

  cargarHistorial() {
    this.pagosService.getConsultarHistorial(this.usuario.usuSecuencial).subscribe(resp => {
      this.listaHistorial = resp;
    })
  }

}
