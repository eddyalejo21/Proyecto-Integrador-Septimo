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
@Table(name = "horario_estacionamiento")
@NamedQuery (name = "HorarioEstacionamiento.findAll", query = "SELECT h FROM HorarioEstacionamiento h")
public class HorarioEstacionamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hes_secuencial")
	@JsonIgnore
	private Integer hesSecuencial;
	
	@Column(name = "hes_estadoah")
	@JsonIgnore
	private Character hesEstadoAh;
	
	@ManyToOne()
	@JoinColumn(name = "est_secuencial", referencedColumnName = "est_secuencial")
	@JsonIgnore
	private Estacionamiento estacionamiento;
	
	@ManyToOne()
	@JoinColumn(name = "hor_secuencial", referencedColumnName = "hor_secuencial")
	@JsonIgnore
	private Horario horario;
	
	@Transient
	private String descripcion;
	
	@Transient
	private String detalle;

	public Integer getHesSecuencial() {
		return hesSecuencial;
	}

	public void setHesSecuencial(Integer hesSecuencial) {
		this.hesSecuencial = hesSecuencial;
	}

	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}

	public void setEstacionamiento(Estacionamiento estacionamiento) {
		this.estacionamiento = estacionamiento;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Character getHesEstadoAh() {
		return hesEstadoAh;
	}

	public void setHesEstadoAh(Character hesEstadoAh) {
		this.hesEstadoAh = hesEstadoAh;
	}

	public String getDescripcion() {
		this.descripcion = horario.getHorHorario();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDetalle() {
		this.detalle = horario.getHorHorario();
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
