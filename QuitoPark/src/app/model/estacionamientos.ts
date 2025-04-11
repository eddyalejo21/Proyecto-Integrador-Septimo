export interface Estacionamiento {
    estSecuencial?: number;
    estIdentificador?: number;
    estNombre?: string;
    estDireccion?: string;
    estCapacidad?: number;
    estGrupo?: string;
    estKalipso?: number;
    estImagen?: string;
    estMaps?: string;
    estEstadoah?: string;
    tarifas?: TarifaEstacionamiento[];
    horarios?: HorarioEstacionamiento[];
}

export interface TarifaEstacionamiento {
    descripcion?:  string;
    valor?:  string;
}

export interface HorarioEstacionamiento {
    descripcion?:  string;
    detalle:  string;
}

