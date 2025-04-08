import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtroEstacionamiento',
  standalone: true,
  pure: false
})
export class FiltroEstacionamientoPipe implements PipeTransform {

  transform(arreglo: any[], texto: string = ''): any[] {
    
    if (texto === '') {
      return arreglo;
    }

    if (!arreglo) {
      return arreglo;
    }

    texto = texto.toLowerCase();

    return arreglo.filter(
      item => item.estNombre.toLowerCase().includes(texto)
    ); 
  }

}
