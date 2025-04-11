package ec.gob.epmmop.appparking.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tarifa")
@NamedQuery (name = "Tarifa.findAll", query = "SELECT t FROM Tarifa t")
public class Tarifa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tar_secuencial")
	private Integer tarSecuencial;
	
	@Column(name = "tar_descripcion")
	private String tarDescripcion;
	
	@Column(name = "tar_tarifa")
	private Double tarTarifa;
	
	@Column(name = "tar_estadoah")
	private Character tarEstadoAh;
	
	@OneToMany(mappedBy = "tarifa")
	@JsonIgnore
    private List<TarifaEstacionamiento> tarifas;

	public Integer getTarSecuencial() {
		return tarSecuencial;
	}

	public void setTarSecuencial(Integer tarSecuencial) {
		this.tarSecuencial = tarSecuencial;
	}

	public String getTarDescripcion() {
		return tarDescripcion;
	}

	public void setTarDescripcion(String tarDescripcion) {
		this.tarDescripcion = tarDescripcion;
	}

	public Double getTarTarifa() {
		return tarTarifa;
	}

	public void setTarTarifa(Double tarTarifa) {
		this.tarTarifa = tarTarifa;
	}

	public Character getTarEstadoAh() {
		return tarEstadoAh;
	}

	public void setTarEstadoAh(Character tarEstadoAh) {
		this.tarEstadoAh = tarEstadoAh;
	}

}
