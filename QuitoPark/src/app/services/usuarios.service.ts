import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Usuario, UsuarioDto } from '../model/usuarios';

const URL_APP = environment.URL_APP;

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private http = inject(HttpClient)

  constructor() { }

  postRegistrarUsuario(usuario: UsuarioDto) {
    return this.http.post(`${URL_APP}/usuarios/registrarUsuario`, usuario, { observe: 'response' })
  }
}
