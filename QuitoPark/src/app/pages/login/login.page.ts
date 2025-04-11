import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IonicModule, LoadingController } from '@ionic/angular';
import { AppComponent } from 'src/app/app.component';
import { HeaderComponent } from 'src/app/components/header/header.component';
import { Usuario } from 'src/app/model/usuarios';
import { AlertasService } from 'src/app/services/alertas.service';
import { UsuariosService } from 'src/app/services/usuarios.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, ReactiveFormsModule]
})
export class LoginPage implements OnInit {

  private usuarioService = inject(UsuariosService);
  private alertaService = inject(AlertasService);
  private loadingController = inject(LoadingController);
  private router = inject(Router);
  private appComponent = inject(AppComponent);

  formLogin: FormGroup;
  usuario: Usuario = {};
  valida: boolean;

  constructor() {
    this.formLogin = new FormGroup({
      identificacion: new FormControl('', [Validators.required]),
      clave: new FormControl('', Validators.required)
    })
  }

  ngOnInit() {
  }

  async iniciarSesion() {
    const usuarioForm: Usuario = {
      usuIdentificacion: this.formLogin.value['identificacion'],
      usuClave: this.formLogin.value['clave']
    }

    const valido: any = await this.usuarioService.postIniciarSesion(usuarioForm);

    const loading = await this.loadingController.create({
      message: 'Cargando'
    });

    await loading.present();

    if (valido) {
      this.usuario = this.usuarioService.getUsuario();
      this.valida = true;
      await loading.dismiss();
      await this.appComponent.actualizarMenu();
      return this.router.navigate(['/home']);
    } else {
      await loading.dismiss();
      this.alertaService.alertaSimple('Credenciales Incorrectas', 'Verificar nombre de usuario o contraseña incorrecta');
    }
    return null;
  }

  probar() {

  }

}
