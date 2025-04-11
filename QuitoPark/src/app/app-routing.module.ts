import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { UsuarioGuard } from './guards/usuario.guard';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule),
    resolve: [ UsuarioGuard ]
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'perfil',
    loadChildren: () => import('./pages/perfil/perfil.module').then( m => m.PerfilPageModule),
    resolve: [ UsuarioGuard ]
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'registrar',
    loadChildren: () => import('./pages/registrar/registrar.module').then( m => m.RegistrarPageModule)
  },
  {
    path: 'estacionamiento',
    loadChildren: () => import('./pages/estacionamiento/estacionamiento.module').then( m => m.EstacionamientoPageModule)
  },
  {
    path: 'estacionamiento/:idEstacionamiento',
    loadChildren: () => import('./pages/estacionamiento/estacionamiento.module').then( m => m.EstacionamientoPageModule)
  },
  {
    path: 'pago-parqueo',
    loadChildren: () => import('./pages/pago-parqueo/pago-parqueo.module').then( m => m.PagoParqueoPageModule),
    resolve: [ UsuarioGuard ]
  },
  {
    path: 'historial',
    loadChildren: () => import('./pages/historial/historial.module').then( m => m.HistorialPageModule),
    resolve: [ UsuarioGuard ]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
