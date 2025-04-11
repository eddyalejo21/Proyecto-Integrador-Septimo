import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InAppBrowser } from '@awesome-cordova-plugins/in-app-browser/ngx';
import { AlertController, IonicModule, LoadingController, NavController } from '@ionic/angular';
import { Ticket } from 'src/app/model/ticket';
import { Usuario } from 'src/app/model/usuarios';
import { AlertasService } from 'src/app/services/alertas.service';
import { PagoService } from 'src/app/services/pago.service';
import { TicketService } from 'src/app/services/ticket.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import * as moment from 'moment';
import { InAppBrowserEvent } from '@awesome-cordova-plugins/in-app-browser';

@Component({
  selector: 'app-pago-parqueo',
  templateUrl: './pago-parqueo.page.html',
  styleUrls: ['./pago-parqueo.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, ReactiveFormsModule],
  providers: [InAppBrowser]
})
export class PagoParqueoPage implements OnInit {

  private usuarioService = inject(UsuariosService);
  private ticketService = inject(TicketService);
  private pagoService = inject(PagoService);
  private alertasService = inject(AlertasService);
  private loadingControler = inject(LoadingController);
  private alertController = inject(AlertController);
  private navController = inject(NavController);
  private iab = inject(InAppBrowser); 

  usuario: Usuario;
  formCodigoQR: FormGroup;
  nombreUsuario: string;
  codigoTicket: string;
  sinDatos: boolean = true;
  ticket: Ticket;
  url: string;
  cabecera: string;
  subtitulo: string;
  mensaje: string;
  autenticacion: {};
  fechaTransaccion: string;
  statusPeticion: string;
  urlProcess: string;

  constructor() {
    this.usuario = this.usuarioService.getUsuario();
    this.validarSesion(this.usuario);

    this.formCodigoQR = new FormGroup({
      codigoTicket: new FormControl('', [Validators.required, Validators.maxLength(16)]),
    })
  }

  ngOnInit() {

  }

  validarSesion(usuario: Usuario) {
    if (this.usuario.usuSecuencial == null) {
      this.alertasService.alertaSinSesion('Sesión Requerida', 'Debe tener iniciada una sesión para continuar con este servicio')
    } else {
      this.nombreUsuario = this.usuario.usuNombres;
    }
  }

  verificarTicket() {
    this.codigoTicket = this.formCodigoQR.value['codigoTicket'];
    console.log('Ticket numero ==>', this.formCodigoQR.value['codigoTicket']);

    this.ticketService.getTicket(this.codigoTicket).subscribe(resp => {

      if (resp.status === 200) {
        this.sinDatos = false;
        this.ticket = resp.body;
      } else {
        this.formCodigoQR.get('codigoTicket')?.reset();
        this.alertasService.alertaTicketNoValido('Error', 'Ticket no válido');
      }
    });
  }

  async procesarPlaceToPay() {
    const loading = await this.loadingControler.create({
      message: 'Cargando'
    });

    this.pagoService.getVerificarTransaccionPendiente(this.usuario.usuSecuencial).subscribe(resp => {
      if (resp['status']) {
        this.url = resp['urlProcess'];
        this.cabecera = 'TRANSACCIÓN PENDIENTE DE PAGO';
        this.subtitulo = `Referencia: ${resp['reference']}`;
        this.mensaje = '¿Desea continuar con la transacción pendiente';

        this.alertaPago(this.cabecera, this.subtitulo, this.mensaje, this.url);

      } else {

        loading.present();

        const json = {
          usuario: {
            codigoUsuario: this.usuario.usuSecuencial,
            nombre: this.usuario.usuNombres.concat(this.usuario.usuApellidos),
            email: this.usuario.usuCorreo,
            document: this.usuario.usuIdentificacion,
            mobile: this.usuario.usuTelefono
          },
          ticket: {
            centro: this.ticket.nombreEstacionamiento,
            baseImponible: this.ticket.valorSubtotal,
            valorIva: this.ticket.valorIva,
            valorTotal: this.ticket.valorTotal,
            // codigoEstacionamiento: this.estacionamientoSicpark.ptoCodigoSri,
            codigoTicket: this.codigoTicket.toString()
          }
        };

        this.pagoService.postCrearSesionPlaceToPay(json).subscribe(resp => {
          console.log('post crear sesion p2p', resp);
          this.autenticacion = resp;
          this.pagoService.postEnviarPlaceToPay(this.autenticacion).subscribe(data => {
            console.log('post enviar p2p', data);
            this.statusPeticion = data['status'].status;
            this.urlProcess = data['processUrl'];

            if (this.statusPeticion == 'OK') {
              this.fechaTransaccion = moment().format('YYYYMMDDHHmmss');

              const obj = {
                pago: {
                  codigoCliente: this.usuario.usuSecuencial,
                  codigoFormaPago: "04",
                  valorTotal: this.ticket.valorTotal,
                  requestId: data['requestId'],
                  processUrl: data['processUrl'],
                  processDate: data['status'].date,
                  description: resp['payment'].description,
                  reference: resp['payment'].reference,
                  codigoTicket: this.codigoTicket.toString(),
                  // idTransaccion: this.idTransaccion
                }
              };

              this.pagoService.postRegistrarTransaccion(obj).subscribe(data => {
              });

              loading.dismiss();

              const browser = this.iab.create(
                this.urlProcess,
                '_blank',
                {
                  hidenavigationbuttons: 'yes',
                  hideurlbar: 'yes',
                  zoom: 'no',
                  fullscreen: 'no',
                  navigationbuttoncolor: '#0073B7'
                },
              )

              browser.on('loadstop').subscribe((event: InAppBrowserEvent) => {
                if (event.url.match('consultas/redirect.html')) {
                  browser.close();

                  const response = {
                    respuesta: {
                      requestId: data['requestId'],
                      usuario: this.usuario.usuSecuencial
                    }
                  }

                  this.pagoService.postModificarTransaccion(response).subscribe(resp => {
                    this.navController.navigateForward('/respuesta-pago');

                    // if (resp['status']) {

                    //   const json = {
                    //     "Accion": "consulta",
                    //     "Origen": this.estacionamientoSicpark.ptoApiKey,
                    //     "Cliente": {
                    //       "Identificacion": this.persona.perCedula,
                    //       "TipoId": "05"
                    //     },
                    //     "Parqueo": {
                    //       "Codigo": this.codigoTicket
                    //     }
                    //   }

                    //   this.sicparkService.postEncriptarTicket(json).subscribe(resp => {

                    //     if (resp['Error'] == 0) {

                    //       const json = {
                    //         "IdTrx": resp['IdTrx'],
                    //         "Accion": "pago",
                    //         "Origen": this.estacionamientoSicpark.ptoApiKey,
                    //         "Cliente": {
                    //           "Identificacion": this.persona.perCedula,
                    //           "Nombre": this.persona.perNombre,
                    //           "TipoId": "05",
                    //           "Direccion": "",
                    //           "Telefono": this.persona.perTelefono,
                    //           "Email": this.persona.perCorreo
                    //         },
                    //         "Parqueo": {
                    //           "Codigo": resp['Parqueo'].Codigo,
                    //           "Placa": "",
                    //           "Valor": resp['Parqueo'].Valor,
                    //           "Recargo": 0.0,
                    //           "Iva": resp['Parqueo'].Iva,
                    //           "Total": resp['Parqueo'].Total,
                    //           "Comprobante": resp['Parqueo'].Codigo
                    //         }
                    //       }

                    //       this.sicparkService.postEncriptarTicket(json).subscribe(resp => {
                    //       });

                    //     }
                    //   });
                    // }
                  });
                }
              });
            }
          });
        });
      }
    });
  }

  async alertaPago(header: string, subHeader: string, message: string, url: string) {
    let urlRedirect = url;
    const alert = await this.alertController.create({
      message,
      header,
      subHeader,
      backdropDismiss: false,
      buttons: [
        {
          text: 'Cancelar',
          handler: () => this.navController.navigateRoot('//home'),
        },
        {
          text: 'Continuar',
          handler: () => this.redireccionar(url)
        }
      ],
    });
    await alert.present();
  }

  redireccionar(url: string) {
    const browser = this.iab.create(
      url,
      '_blank',
      {
        hidenavigationbuttons: 'yes',
        hideurlbar: 'yes',
        zoom: 'no',
        fullscreen: 'no',
        navigationbuttoncolor: '#0073B7'
      }
    )
  }


}
