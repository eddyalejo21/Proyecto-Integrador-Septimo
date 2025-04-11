import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Ticket } from '../model/ticket';

const URL_APP = environment.URL_APP;

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private http = inject(HttpClient)

  constructor() { }

  getTicket (ticket: string) {
    return this.http.get<Ticket>(`${URL_APP}/tickets/obtenerTicket/${ticket}`, { observe: 'response'});
  }
}
