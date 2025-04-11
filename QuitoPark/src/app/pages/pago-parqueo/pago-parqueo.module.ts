import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PagoParqueoPageRoutingModule } from './pago-parqueo-routing.module';

import { PagoParqueoPage } from './pago-parqueo.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PagoParqueoPageRoutingModule
  ]
})
export class PagoParqueoPageModule {}
