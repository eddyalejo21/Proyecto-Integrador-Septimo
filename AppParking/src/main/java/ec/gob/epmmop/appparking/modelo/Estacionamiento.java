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
@Table(name = "estacionamiento")
@NamedQuery(name = "Estacionamiento.findAll", query = "SELECT e FROM Estacionamiento e")
public class Estacionamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "est_secuencial")
	private Integer estSecuencial;

	@Column(name = "est_identificador")
	private Integer estIdentificador;

	@Column(name = "est_nombre")
	private String estNombre;

	@Column(name = "est_direccion")
	private String estDireccion;

	@Column(name = "est_capacidad")
	private Integer estCapacidad;

	@Column(name = "est_grupo")
	private Character estGrupo;

	@Column(name = "est_kalipso")
	private Integer estKalipso;

	@Column(name = "est_imagen")
	private String estImagen;

	@Column(name = "est_mapa")
	private String estMaps;

	@Column(name = "est_estadoah")
	@JsonIgnore
	private Character estEstadoAh;

	@OneToMany(mappedBy = "estacionamiento")
	@JsonIgnore
    private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "estacionamiento")
    private List<HorarioEstacionamiento> horarios;
	
	@OneToMany(mappedBy = "estacionamiento")
    private List<TarifaEstacionamiento> tarifas;

	public Integer getEstSecuencial() {
		return estSecuencial;
	}

	public void setEstSecuencial(Integer estSecuencial) {
		this.estSecuencial = estSecuencial;
	}

	public Integer getEstIdentificador() {
		return estIdentificador;
	}

	public void setEstIdentificador(Integer estIdentificador) {
		this.estIdentificador = estIdentificador;
	}

	public String getEstNombre() {
		return estNombre;
	}

	public void setEstNombre(String estNombre) {
		this.estNombre = estNombre;
	}

	public String getEstDireccion() {
		return estDireccion;
	}

	public void setEstDireccion(String estDireccion) {
		this.estDireccion = estDireccion;
	}

	public Character getEstGrupo() {
		return estGrupo;
	}

	public void setEstGrupo(Character estGrupo) {
		this.estGrupo = estGrupo;
	}

	public Integer getEstKalipso() {
		return estKalipso;
	}

	public void setEstKalipso(Integer estKalipso) {
		this.estKalipso = estKalipso;
	}

	public String getEstImagen() {
		return estImagen;
	}

	public void setEstImagen(String estImagen) {
		this.estImagen = estImagen;
	}

	public String getEstMaps() {
		return estMaps;
	}

	public void setEstMaps(String estMaps) {
		this.estMaps = estMaps;
	}

	public Character getEstEstadoAh() {
		return estEstadoAh;
	}

	public void setEstEstadoAh(Character estEstadoAh) {
		this.estEstadoAh = estEstadoAh;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<HorarioEstacionamiento> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioEstacionamiento> horarios) {
		this.horarios = horarios;
	}

	public List<TarifaEstacionamiento> getTarifas() {
		return tarifas;
	}

	public void setTarifas(List<TarifaEstacionamiento> tarifas) {
		this.tarifas = tarifas;
	}

	public Integer getEstCapacidad() {
		return estCapacidad;
	}

	public void setEstCapacidad(Integer estCapacidad) {
		this.estCapacidad = estCapacidad;
	}

}
