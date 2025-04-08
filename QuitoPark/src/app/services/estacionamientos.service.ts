import { HttpClient } from '@angular/common/http';
import { inject, Inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Estacionamiento } from '../model/estacionamientos';

const URL_APP = environment.URL_APP;

@Injectable({
  providedIn: 'root'
})
export class EstacionamientosService {

  private http = inject(HttpClient)

  constructor() { }

  getEstacionamientos () {
    return this.http.get<Estacionamiento[]>(`${URL_APP}/estacionamientos/listar`);
  }
}
