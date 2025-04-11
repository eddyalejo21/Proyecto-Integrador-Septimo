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
@Table(name = "horario")
@NamedQuery (name = "Horario.findAll", query = "SELECT h FROM Horario h")
public class Horario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hor_secuencial")
	private Integer horSecuencial;
	
	@Column(name = "hor_horario")
	private String horHorario;
	
	@Column(name = "hor_estadoah")
	private Character horEstadoAh;
	
	@OneToMany(mappedBy = "horario")
	@JsonIgnore
    private List<HorarioEstacionamiento> horarios;

	public Integer getHorSecuencial() {
		return horSecuencial;
	}

	public void setHorSecuencial(Integer horSecuencial) {
		this.horSecuencial = horSecuencial;
	}

	public String getHorHorario() {
		return horHorario;
	}

	public void setHorHorario(String horHorario) {
		this.horHorario = horHorario;
	}

	public Character getHorEstadoAh() {
		return horEstadoAh;
	}

	public void setHorEstadoAh(Character horEstadoAh) {
		this.horEstadoAh = horEstadoAh;
	}

}
