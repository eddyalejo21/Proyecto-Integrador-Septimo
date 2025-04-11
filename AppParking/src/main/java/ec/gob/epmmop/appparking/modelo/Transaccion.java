package ec.gob.epmmop.appparking.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaccion")
@NamedQuery (name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t")
public class Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trx_secuencial")
	private Integer trxSecuencial;
	
	@Column(name = "trx_request_id")
	private Integer trxRequestId;
	
	@Column(name = "trx_transaccion_id")
	private String trxTransaccionId;
	
	@Column(name = "trx_referencia")
	private String trxReferencia;
	
	@Column(name = "trx_estado")
	private String trxEstado;
	
	@Column(name = "trx_url")
	private String trxUrl;
	
	@Column(name = "trx_monto")
	private BigDecimal trxMonto;
	
	@Column(name = "trx_detalle")
	private String trxDetalle;
	
	@Column(name = "trx_autorizacion")
	private String trxAutorizacion;
	
	@Column(name = "trx_transferencia")
	private String trxTransferencia;
	
	@Column(name = "trx_fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date trxFechaRegistro;

	@JoinColumn(name = "usu_secuencial", referencedColumnName = "usu_secuencial")
	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario usuario;

	public Integer getTrxSecuencial() {
		return trxSecuencial;
	}

	public void setTrxSecuencial(Integer trxSecuencial) {
		this.trxSecuencial = trxSecuencial;
	}

	public Integer getTrxRequestId() {
		return trxRequestId;
	}

	public void setTrxRequestId(Integer trxRequestId) {
		this.trxRequestId = trxRequestId;
	}

	public String getTrxTransaccionId() {
		return trxTransaccionId;
	}

	public void setTrxTransaccionId(String trxTransaccionId) {
		this.trxTransaccionId = trxTransaccionId;
	}

	public String getTrxReferencia() {
		return trxReferencia;
	}

	public void setTrxReferencia(String trxReferencia) {
		this.trxReferencia = trxReferencia;
	}

	public String getTrxEstado() {
		return trxEstado;
	}

	public void setTrxEstado(String trxEstado) {
		this.trxEstado = trxEstado;
	}

	public String getTrxUrl() {
		return trxUrl;
	}

	public void setTrxUrl(String trxUrl) {
		this.trxUrl = trxUrl;
	}

	public BigDecimal getTrxMonto() {
		return trxMonto;
	}

	public void setTrxMonto(BigDecimal trxMonto) {
		this.trxMonto = trxMonto;
	}

	public String getTrxDetalle() {
		return trxDetalle;
	}

	public void setTrxDetalle(String trxDetalle) {
		this.trxDetalle = trxDetalle;
	}

	public String getTrxAutorizacion() {
		return trxAutorizacion;
	}

	public void setTrxAutorizacion(String trxAutorizacion) {
		this.trxAutorizacion = trxAutorizacion;
	}

	public String getTrxTransferencia() {
		return trxTransferencia;
	}

	public void setTrxTransferencia(String trxTransferencia) {
		this.trxTransferencia = trxTransferencia;
	}

	public Date getTrxFechaRegistro() {
		return trxFechaRegistro;
	}

	public void setTrxFechaRegistro(Date trxFechaRegistro) {
		this.trxFechaRegistro = trxFechaRegistro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
