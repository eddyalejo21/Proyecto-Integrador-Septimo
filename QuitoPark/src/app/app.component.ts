import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Storage } from '@ionic/storage-angular';;
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false,
})
export class AppComponent {

  private storage = inject(Storage);
  private router = inject(Router);

  // public appPages = [
  //   { title: 'Perfil', url: '/perfil', icon: 'person' },
  //   { title: 'Mis pagos', url: '/folder/outbox', icon: 'paper-plane' },
  //   { title: 'Iniciar Sesión', url: '/login', icon: 'key' },
  //   { title: 'Registrarse', url: '/registrar', icon: 'person-add' },
  //   { title: 'Cerrar Sesión', icon: 'log-out' }
  // ];

  public appPages = [];

  constructor() {

  }

  async ngOnInit() {
    await this.storage.create();
    await this.actualizarMenu();
  }

  async actualizarMenu() {
    const token = await this.storage.get('token'); // Asume que 'miToken' es la clave del token

    if (token) {
      // Sesión iniciada
      this.appPages = [
        { title: 'Perfil', url: '/perfil', icon: 'person' },
        { title: 'Mis pagos', url: '/historial', icon: 'paper-plane' },
        { title: 'Cerrar Sesión', icon: 'log-out', handler: () => this.cerrarSesion() } // Agrega un handler para cerrar sesión
      ];
    } else {
      // Sesión no iniciada
      this.appPages = [
        { title: 'Iniciar Sesión', url: '/login', icon: 'key' },
        { title: 'Registrarse', url: '/registrar', icon: 'person-add' }
      ];
    }
  }

  async cerrarSesion() {
    await this.storage.remove('token'); // Elimina el token
    await this.actualizarMenu(); // Actualiza el menú
    this.router.navigate(['/home'], { replaceUrl: true }); // Redirige al login
  }



}
