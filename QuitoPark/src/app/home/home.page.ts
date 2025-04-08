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

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, HeaderComponent, HttpClientModule, RouterModule, FiltroEstacionamientoPipe, FiltroSectorPipe],
})
export class HomePage implements OnInit {

  private estacionamientoService = inject(EstacionamientosService)
  private navCtrl = inject(NavController)

  estacionamientos: Estacionamiento[] = [];
  estacionamiento: Estacionamiento;
  sectorBuscar: string = 'N';
  estacionamientoBuscar: string = '';

  constructor() { }

  ngOnInit() {
    this.cargarEstacionamientos();
  }

  cargarEstacionamientos() {
    this.estacionamientoService.getEstacionamientos().subscribe(data => {
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




}
