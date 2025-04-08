import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false,
})
export class AppComponent {
  public appPages = [
    { title: 'Perfil', url: '/perfil', icon: 'person' },
    { title: 'Mis pagos', url: '/folder/outbox', icon: 'paper-plane' },
    { title: 'Iniciar Sesión', url: '/login', icon: 'key' },
    { title: 'Registrarse', url: '/registrar', icon: 'person-add' },
    { title: 'Cerrar Sesión', icon: 'log-out' }
  ];
  // public labels = ['Family', 'Friends', 'Notes', 'Work', 'Travel', 'Reminders'];
  constructor() {}
}
