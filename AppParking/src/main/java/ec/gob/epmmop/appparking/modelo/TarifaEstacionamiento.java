package ec.gob.epmmop.appparking.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tarifa_estacionamiento")
@NamedQuery(name = "TarifaEstacionamiento.findAll", query = "SELECT e FROM TarifaEstacionamiento e")
public class TarifaEstacionamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tes_secuencial")
	@JsonIgnore
	private Integer tesSecuencial;

	@Column(name = "tes_estadoah")
	@JsonIgnore
	private Character tesEstadoAh;

	@ManyToOne()
	@JoinColumn(name = "est_secuencial", referencedColumnName = "est_secuencial")
	@JsonIgnore
	private Estacionamiento estacionamiento;

	@ManyToOne()
	@JoinColumn(name = "tar_secuencial", referencedColumnName = "tar_secuencial")
	@JsonIgnore
	private Tarifa tarifa;
	
	@Transient
	private String descripcion;
	
	@Transient
	private Double valor;

	public Integer getTesSecuencial() {
		return tesSecuencial;
	}

	public void setTesSecuencial(Integer tesSecuencial) {
		this.tesSecuencial = tesSecuencial;
	}

	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}

	public void setEstacionamiento(Estacionamiento estacionamiento) {
		this.estacionamiento = estacionamiento;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public Character getTesEstadoAh() {
		return tesEstadoAh;
	}

	public void setTesEstadoAh(Character tesEstadoAh) {
		this.tesEstadoAh = tesEstadoAh;
	}

	public String getDescripcion() {
		this.descripcion = tarifa.getTarDescripcion(); 
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getValor() {
		this.valor = tarifa.getTarTarifa();
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
