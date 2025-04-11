package ec.gob.epmmop.appparking.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "usuario")
@NamedQuery (name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usu_secuencial")
	private Integer usuSecuencial;
	
	@Column(name = "usu_identificacion")
	private String usuIdentificacion;
	
	@Column(name = "usu_nombres")
	private String usuNombres;
	
	@Column(name = "usu_apellidos")
	private String usuApellidos;
	
	@Column(name = "usu_correo")
	private String usuCorreo;
	
	@Column(name = "usu_telefono")
	private String usuTelefono;
	
	@Column(name = "usu_clave")
	private String usuClave;
	
	@Column(name = "usu_token")
	private Integer  usuToken;
	
	@Column(name = "usu_estadoah")
	private Character usuEstadoAh;

	public Integer getUsuSecuencial() {
		return usuSecuencial;
	}

	public void setUsuSecuencial(Integer usuSecuencial) {
		this.usuSecuencial = usuSecuencial;
	}

	public String getUsuIdentificacion() {
		return usuIdentificacion;
	}

	public void setUsuIdentificacion(String usuIdentificacion) {
		this.usuIdentificacion = usuIdentificacion;
	}

	public String getUsuNombres() {
		return usuNombres;
	}

	public void setUsuNombres(String usuNombres) {
		this.usuNombres = usuNombres;
	}

	public String getUsuApellidos() {
		return usuApellidos;
	}

	public void setUsuApellidos(String usuApellidos) {
		this.usuApellidos = usuApellidos;
	}

	public String getUsuCorreo() {
		return usuCorreo;
	}

	public void setUsuCorreo(String usuCorreo) {
		this.usuCorreo = usuCorreo;
	}

	public String getUsuTelefono() {
		return usuTelefono;
	}

	public void setUsuTelefono(String usuTelefono) {
		this.usuTelefono = usuTelefono;
	}

	public String getUsuClave() {
		return usuClave;
	}

	public void setUsuClave(String usuClave) {
		this.usuClave = usuClave;
	}

	public Integer getUsuToken() {
		return usuToken;
	}

	public void setUsuToken(Integer usuToken) {
		this.usuToken = usuToken;
	}

	public Character getUsuEstadoAh() {
		return usuEstadoAh;
	}

	public void setUsuEstadoAh(Character usuEstadoAh) {
		this.usuEstadoAh = usuEstadoAh;
	}
	
}
