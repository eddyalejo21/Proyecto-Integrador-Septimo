import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, CanMatchFn, Resolve, Route, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UsuariosService } from '../services/usuarios.service';
import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root'
})
export class UsuarioGuard implements Resolve<boolean> {

  constructor(
    private usuarioService: UsuariosService,
    private storage: Storage
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
    return this.usuarioService.verificarToken();
  }

  verificarSesion(): Observable<boolean> | Promise<boolean> | boolean {
    return this.usuarioService.verificarToken();
  }
}
