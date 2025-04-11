package ec.gob.epmmop.appparking.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ticket")
@NamedQuery (name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
public class Ticket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tic_secuencial")
	private Integer ticSecuencial;
	
	@Column(name = "tic_numeracion")
	private Integer ticNumeracion;
	
	@Column(name = "tic_ticket")
	private String ticTicket;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tic_fecha_ingreso")
	private Date ticIngreso;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tic_fecha_salida")
	private Date ticSalida;
	
	@Column(name = "tic_estadoah")
	private Character ticEstadoAh;
	
	@ManyToOne()
	@JoinColumn(name = "est_secuencial", referencedColumnName = "est_secuencial")
	@JsonIgnore
	private Estacionamiento estacionamiento;
	
	@Transient
	private String nombreEstacionamiento;
	
	@Transient
	private String tiempoPermanencia;
	
	@Transient
	private BigDecimal valorTotal;
	
	@Transient
	private BigDecimal valorSubtotal;
	
	@Transient
	private BigDecimal valorIva;
	
	@Transient
	private String ingreso;

	public Integer getTicSecuencial() {
		return ticSecuencial;
	}

	public void setTicSecuencial(Integer ticSecuencial) {
		this.ticSecuencial = ticSecuencial;
	}

	public Integer getTicNumeracion() {
		return ticNumeracion;
	}

	public void setTicNumeracion(Integer ticNumeracion) {
		this.ticNumeracion = ticNumeracion;
	}

	public String getTicTicket() {
		return ticTicket;
	}

	public void setTicTicket(String ticTicket) {
		this.ticTicket = ticTicket;
	}

	public Character getTicEstadoAh() {
		return ticEstadoAh;
	}

	public void setTicEstadoAh(Character ticEstadoAh) {
		this.ticEstadoAh = ticEstadoAh;
	}

//	@JsonIgnore
	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}

	public void setEstacionamiento(Estacionamiento estacionamiento) {
		this.estacionamiento = estacionamiento;
	}

	public String getNombreEstacionamiento() {
		return nombreEstacionamiento;
	}

	public void setNombreEstacionamiento(String nombreEstacionamiento) {
		this.nombreEstacionamiento = nombreEstacionamiento;
	}

	public Date getTicIngreso() {
		return ticIngreso;
	}

	public void setTicIngreso(Date ticIngreso) {
		this.ticIngreso = ticIngreso;
	}

	public Date getTicSalida() {
		return ticSalida;
	}

	public void setTicSalida(Date ticSalida) {
		this.ticSalida = ticSalida;
	}

	public String getTiempoPermanencia() {
		return tiempoPermanencia;
	}

	public void setTiempoPermanencia(String tiempoPermanencia) {
		this.tiempoPermanencia = tiempoPermanencia;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}

	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}

	public BigDecimal getValorIva() {
		return valorIva;
	}

	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

		
}
