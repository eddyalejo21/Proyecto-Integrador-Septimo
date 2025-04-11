import { Component, EventEmitter, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../components/header/header.component';
import { CommonModule } from '@angular/common';
import { IonicModule, NavController } from '@ionic/angular';
import { EstacionamientosService } from '../services/estacionamientos.service';
import { HttpClientModule } from '@angular/common/http';
import { FiltroEstacionamientoPipe } from '../pipes/filtro-estacionamiento.pipe';
import { FiltroSectorPipe } from '../pipes/filtro-sector.pipe';
import { RouterModule } from '@angular/router';
import { Estacionamiento } from '../model/estacionamientos';
import { UsuariosService } from '../services/usuarios.service';
import { Usuario } from '../model/usuarios';
import { interval, Subscription, switchMap } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, HeaderComponent, HttpClientModule, RouterModule, FiltroEstacionamientoPipe, FiltroSectorPipe],
})
export class HomePage implements OnInit {

  private estacionamientoService = inject(EstacionamientosService);
  private navCtrl = inject(NavController);
  private usuarioService = inject(UsuariosService);

  usuario: Usuario;

  estacionamientos: Estacionamiento[] = [];
  estacionamiento: Estacionamiento;
  sectorBuscar: string = 'N';
  estacionamientoBuscar: string = '';

  pollingSub!: Subscription;

  constructor() { 
    this.usuario = this.usuarioService.getUsuario();
    this.validarSesion(this.usuario);
  }

  ngOnInit() {
    this.cargarEstacionamientos();
    this.iniciarPollingEstacionamientos();
  }

  cargarEstacionamientos() {
    this.estacionamientoService.getEstacionamientos().subscribe(data => {
      this.estacionamientos = data;
    });
  }

  iniciarPollingEstacionamientos() {
    this.pollingSub = interval(10000) // cada 5 segundos
      .pipe(
        switchMap(() => this.estacionamientoService.getEstacionamientos())
      )
      .subscribe((data) => {
        this.estacionamientos = data;
      });
  }

  irADetalle(estacionamiento : Estacionamiento) {
    this.navCtrl.navigateForward('/estacionamiento', {
      state: { objeto: estacionamiento }
    });
  }

 onSearchChange(event) {
    this.estacionamientoBuscar = event.detail.value;
  }

  segmentChanged(event) {
    if (event.detail.value === 'todos') {
      return this.sectorBuscar = '';
    }
    return this.sectorBuscar = event.detail.value;
  }

  validarSesion(usuario: Usuario) {
    if (this.usuario.usuSecuencial == null) {
      // this.alertasService.alertaSinSesion('Sesión Requerida', 'Debe tener iniciada una sesión para continuar con este servicio')
      console.log('Sesión no iniciada');
    } else {
      console.log('Sesión iniciada', usuario.usuNombres);
      // this.nombreUsuario = this.persona.perNickname;
      // this.mensajeBienvenida = `Hola ${this.nombreUsuario}, te damos la bienvenida al servicio de pago en línea de estacionamientos; al momento el servicio está disponible en Estacionamiento Carolina 6 y Estacionamiento Cadisan.`;

    }
  }




}
