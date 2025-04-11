import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Transaccion } from '../model/transaccion';

const URL_APP = environment.URL_APP;
const URL_P2P = environment.URL_P2P;

@Injectable({
  providedIn: 'root'
})
export class PagoService {

  constructor() { }

  private http = inject(HttpClient)

  getVerificarTransaccionPendiente(idUsuario: number){
    return this.http.get(`${URL_APP}/payments/verificarPendientes/${idUsuario}`);
  }

  postCrearSesionPlaceToPay(usuario: any) {
    return this.http.post(`${URL_APP}/payments/crearSesion`, usuario);
  }

  postEnviarPlaceToPay(data : {}) {
    return this.http.post(`${URL_P2P}/api/session`, data);
  }

  postRegistrarTransaccion(data : {}){
    return this.http.post(`${URL_APP}/payments/registrarTransaccion`, data);
  }

  postModificarTransaccion(data:any){
    return this.http.post(`${URL_APP}/payments/modificarTransaccion`, data);
  }

  getConsultarHistorial(idUsuario: number){
    return this.http.get<Transaccion[]>(`${URL_APP}/payments/consultarHistorial/${idUsuario}`);
  }
}
