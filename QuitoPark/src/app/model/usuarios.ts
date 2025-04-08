export interface Usuario {
    usuSecuencial?: number;
    usuIdentificacion?: string;
    usuNombres?: string;
    usuApellidos?: string;
    usuCorreo?: string;
    usuTelefono?: string;
    usuClave?: string;
}

export interface UsuarioDto {
    usuIdentificacion?: string;
    usuNombres?: string;
    usuApellidos?: string;
    usuCorreo?: string;
    usuTelefono?: string;
    usuClave?: string;
}