import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { HomePageRoutingModule } from './home-routing.module';

import { HeaderComponent } from '../components/header/header.component';
import { HttpClientModule } from '@angular/common/http';
import { FiltroEstacionamientoPipe } from '../pipes/filtro-estacionamiento.pipe';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    HomePageRoutingModule,
    FiltroEstacionamientoPipe
  ]
})
export class HomePageModule {}
