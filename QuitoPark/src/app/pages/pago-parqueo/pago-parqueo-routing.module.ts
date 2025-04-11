import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PagoParqueoPage } from './pago-parqueo.page';

const routes: Routes = [
  {
    path: '',
    component: PagoParqueoPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagoParqueoPageRoutingModule {}
