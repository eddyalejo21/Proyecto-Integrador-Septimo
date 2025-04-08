import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule, ToastController } from '@ionic/angular';
import { UsuarioDto } from 'src/app/model/usuarios';
import { AlertasService } from 'src/app/services/alertas.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import { Router } from '@angular/router';

import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.page.html',
  styleUrls: ['./registrar.page.scss'],
  standalone: true,
  imports: [IonicModule, ReactiveFormsModule]
})
export class RegistrarPage implements OnInit {

  private alertaService = inject(AlertasService)
  private usuarioService = inject(UsuariosService)
  private toastController = inject(ToastController)
  private router = inject(Router)

  formRegistro: FormGroup;

  constructor() {
    this.formRegistro = new FormGroup({
      identificacion: new FormControl('', [Validators.required, Validators.min(13)]),
      nombre: new FormControl('', Validators.required),
      apellido: new FormControl('', Validators.required),
      correo: new FormControl('', [Validators.required, Validators.email, Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')]),
      telefono: new FormControl('', Validators.required),
      clave: new FormControl('', Validators.required),
      claveRepeat: new FormControl('', Validators.required),
    })

  }

  ngOnInit() {
  }

  registrarUsuario() {
    
    if (this.formRegistro.value['clave'] != this.formRegistro.value['claveRepeat']) {
      this.alertaService.alertaSimple('Contraseñas no coinciden', 'Verificar que ambas contraseñas sean iguales')
    } else {

      const usuarioForm: UsuarioDto = {
        usuIdentificacion: this.formRegistro.value['identificacion'],
        usuNombres: this.formRegistro.value['nombre'],
        usuApellidos: this.formRegistro.value['apellido'],
        usuCorreo: this.formRegistro.value['correo'],
        usuTelefono: this.formRegistro.value['telefono'],
        usuClave: this.formRegistro.value['clave'],
      }

      this.usuarioService.postRegistrarUsuario(usuarioForm).subscribe({
        next: async response => {
          if (response.status === 200) {
            const toast = await this.toastController.create({
              message: 'Usuario creado exitosamente',
              duration: 2000,
              color: 'success',
              position: 'top'
            });
            await toast.present();
      
            // Redirige al login después de mostrar el mensaje
            this.router.navigate(['/login']);
          }
        },
        error: async error => {
          const toast = await this.toastController.create({
            message: 'Error al registrar usuario',
            duration: 2000,
            color: 'danger',
            position: 'top'
          });
          await toast.present();
        }
      });

      
    }
  }

}
