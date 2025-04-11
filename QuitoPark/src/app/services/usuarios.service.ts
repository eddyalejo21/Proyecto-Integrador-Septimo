import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Usuario, UsuarioDto } from '../model/usuarios';
import { NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage-angular';
import { catchError, of } from 'rxjs';

const URL_APP = environment.URL_APP;

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private http = inject(HttpClient)
  private storage = inject(Storage)
  private navController = inject(NavController)

  private usuario: Usuario = {};
  token: string = '';

  constructor() { }

  postRegistrarUsuario(usuario: UsuarioDto) {
    return this.http.post(`${URL_APP}/usuarios/registrarUsuario`, usuario, { observe: 'response' })
  }

  postIniciarSesion(usuario : Usuario) {
    return new Promise(resolve => {

      return this.http.post<Usuario>(`${URL_APP}/usuarios/iniciarSesion`, usuario).subscribe(async resp => {
        if (resp['ok']) {
          await this.almacenarToken(resp['token']);
          resolve(true);
        } else {
          this.token = null;
          this.storage.clear();
          resolve(false);
        }
      });
    });
  }

  async almacenarToken(token: string) {
    this.token = token;
    await this.storage.set('token', token);
    await this.verificarToken();
  }

  async verificarToken(): Promise<boolean> {
    await this.cargarToken();
    if (!this.token) {
      return Promise.resolve(false);
    }
    return new Promise(resolve => {
      const headers = new HttpHeaders({
        'x-token': this.token
      });

      this.http.get(`${URL_APP}/usuarios/obtenerUsuarioPorToken`, { headers }).subscribe(resp => {
        if (resp['ok']) {
          if (resp['usuario'] != null) {
            this.usuario = resp['usuario'];
            resolve(true);
          } else {
            // console.log('De abrir el panel para modificar la clave');
            console.log("persona null");
          }
        } else {
          resolve(false);
        }
      });
    });
  }

  async cargarToken() {
    this.token = await this.storage.get('token') || null;
  }

  getUsuario() {
    if (!this.usuario.usuSecuencial) {
      this.verificarToken();
    }
    return { ...this.usuario };
  }

}
