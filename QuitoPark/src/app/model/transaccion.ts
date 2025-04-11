export interface Transaccion {
    trxSecuencial?: number;
    trxRequestId?: number;
    trxTransaccionId?: number;
    trxReferencia?: string;
    trxEstado?: string;
    trxUrl?: string;
    trxMonto?: number;
    trxDetalle?: string;
    trxAutorizacion?: string;
    trxTransferencia?: string;
    trxFechaRegistro?: string;
}