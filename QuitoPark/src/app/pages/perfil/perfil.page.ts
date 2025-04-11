import { Component, inject, OnInit } from '@angular/core';
import { Browser } from '@capacitor/browser';
import { IonicModule, ModalController, ViewDidEnter } from '@ionic/angular';
import { PoliticasProteccionComponent } from 'src/app/components/politicas-proteccion/politicas-proteccion.component';
import { PreguntasFrecuentesComponent } from 'src/app/components/preguntas-frecuentes/preguntas-frecuentes.component';
import { TerminosCondicionesComponent } from 'src/app/components/terminos-condiciones/terminos-condiciones.component';
import { Usuario } from 'src/app/model/usuarios';
import { AlertasService } from 'src/app/services/alertas.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.page.html',
  styleUrls: ['./perfil.page.scss'],
  standalone: true,
  imports: [IonicModule]
})
export class PerfilPage implements OnInit, ViewDidEnter {

  private usuarioService = inject(UsuariosService);
  private alertaService = inject(AlertasService);
  private modalController = inject(ModalController);

  usuario: Usuario = {};


  constructor() { }

  ngOnInit() {
  }

  ionViewDidEnter() {
    this.usuario = this.usuarioService.getUsuario();
  }

  ejecutar() {
    this.alertaService.alertaSimple('Ten Paciencia', 'Este servicio estará disponible proximamente')
  }

  async abrirEpmmop() {
    await Browser.open({ url: 'https://www.epmmop.gob.ec' });
  }

  async abrirPreguntas() {
    const modal = await this.modalController.create({
      component: PreguntasFrecuentesComponent,
    });

    modal.present();
  }

  async abrirPoliticas() {
    const modal = await this.modalController.create({
      component: PoliticasProteccionComponent
    });

    modal.present();
  }

  async abrirTerminos() {
    const modal = await this.modalController.create({
      component: TerminosCondicionesComponent
    });

    modal.present();
  }

}
