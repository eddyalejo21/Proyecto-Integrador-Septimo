import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtroSector',
  standalone: true
})
export class FiltroSectorPipe implements PipeTransform {

  transform(arreglo: any[], grupo: string = ''): any[] {
    if (grupo === '') {
      return arreglo;
    }

    if (!arreglo) {
      return arreglo;
    }

    grupo = grupo.toLocaleLowerCase();

    return arreglo.filter(
      item => item.estGrupo.toLowerCase().includes(grupo)
    );
  }

}
