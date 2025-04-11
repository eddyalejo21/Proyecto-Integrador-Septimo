package ec.gob.epmmop.appparking.dto;

public class InformacionPago {
	
	private Integer codigoUsuario;
	private String nombreUsuario;
	private String emailUsuario;
	private String cedulaUsuario;
	private String telefonoUsuario;
	private String centroRecaudacion;
	private Double baseImponible;
	private Double valorIva;
	private Double valorTotal;
//	private String codigoEstacionamiento;
	private String codigoTicket;
	private String direccionIp;
	
	public InformacionPago() {
	
	}
	
	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getCedulaUsuario() {
		return cedulaUsuario;
	}
	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}
	public String getCentroRecaudacion() {
		return centroRecaudacion;
	}
	public void setCentroRecaudacion(String centroRecaudacion) {
		this.centroRecaudacion = centroRecaudacion;
	}
	public Double getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}
	public Double getValorIva() {
		return valorIva;
	}
	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getCodigoTicket() {
		return codigoTicket;
	}
	public void setCodigoTicket(String codigoTicket) {
		this.codigoTicket = codigoTicket;
	}

	public String getDireccionIp() {
		return direccionIp;
	}

	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	@Override
	public String toString() {
		return "InformacionPago [codigoUsuario=" + codigoUsuario + ", nombreUsuario=" + nombreUsuario
				+ ", emailUsuario=" + emailUsuario + ", cedulaUsuario=" + cedulaUsuario + ", telefonoUsuario="
				+ telefonoUsuario + ", centroRecaudacion=" + centroRecaudacion + ", baseImponible=" + baseImponible
				+ ", valorIva=" + valorIva + ", valorTotal=" + valorTotal + ", codigoTicket=" + codigoTicket
				+ ", direccionIp=" + direccionIp + "]";
	}

	

	
	
}
