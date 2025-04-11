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
@Table(name = "parametro")
@NamedQuery (name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
public class Parametros implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prm_secuencial")
	private Integer prmSecuencial;
	
	@Column(name = "prm_usuario")
	private String prmLogin;
	
	@Column(name = "prm_clave")
	private String prmSecretKey;
	
	@Column(name = "prm_url")
	private String prmUrl;
	
	@Column(name = "prm_return")
	private String prmUrlReturn;
	
	@Column(name = "prm_cancel")
	private String prmUrlCancel;

	public Integer getPrmSecuencial() {
		return prmSecuencial;
	}

	public void setPrmSecuencial(Integer prmSecuencial) {
		this.prmSecuencial = prmSecuencial;
	}

	public String getPrmLogin() {
		return prmLogin;
	}

	public void setPrmLogin(String prmLogin) {
		this.prmLogin = prmLogin;
	}

	public String getPrmSecretKey() {
		return prmSecretKey;
	}

	public void setPrmSecretKey(String prmSecretKey) {
		this.prmSecretKey = prmSecretKey;
	}

	public String getPrmUrl() {
		return prmUrl;
	}

	public void setPrmUrl(String prmUrl) {
		this.prmUrl = prmUrl;
	}

	public String getPrmUrlReturn() {
		return prmUrlReturn;
	}

	public void setPrmUrlReturn(String prmUrlReturn) {
		this.prmUrlReturn = prmUrlReturn;
	}

	public String getPrmUrlCancel() {
		return prmUrlCancel;
	}

	public void setPrmUrlCancel(String prmUrlCancel) {
		this.prmUrlCancel = prmUrlCancel;
	}
	
}
