import { inject, Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class AlertasService {

  private alertController = inject(AlertController)

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
}
