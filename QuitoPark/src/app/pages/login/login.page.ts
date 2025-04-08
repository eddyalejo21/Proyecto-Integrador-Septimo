import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { HeaderComponent } from 'src/app/components/header/header.component';
import { Usuario } from 'src/app/model/usuarios';
import { AlertasService } from 'src/app/services/alertas.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, HeaderComponent, ReactiveFormsModule]
})
export class LoginPage implements OnInit {

  private usuarioService = inject(UsuariosService);
  private alertaService = inject(AlertasService)

  formLogin: FormGroup;

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

    console.log(usuarioForm);
  }

  probar(){
    
  }

}
