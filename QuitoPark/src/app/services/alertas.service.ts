import { inject, Injectable } from '@angular/core';
import { AlertController, NavController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class AlertasService {

  private alertController = inject(AlertController);
  private navController = inject(NavController)

  constructor() { }

  async alertaSimple( header : string, message : string ) {
    const alert = await this.alertController.create({
      message,
      header,
      backdropDismiss: false,
      buttons: [
        {
          text: 'OK',
        }],
    });
    await alert.present(); 
  }

  async alertaSinSesion( header : string, message : string ) {
    const alert = await this.alertController.create({
      message,
      header,
      backdropDismiss: false,
      buttons: [
        {
          text: 'OK',
          handler: () => this.navController.navigateForward('/home'),
        }],
    });
    await alert.present();
  }

  async alertaTicketNoValido( header : string, message : string ) {
    const alert = await this.alertController.create({
      message,
      header,
      backdropDismiss: false,
      buttons: [
        {
          text: 'OK',
          role: 'cancel',
        }],
    });
    await alert.present();
  }
}
