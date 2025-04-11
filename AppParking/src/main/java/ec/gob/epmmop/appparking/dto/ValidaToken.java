package ec.gob.epmmop.appparking.dto;

import ec.gob.epmmop.appparking.modelo.Usuario;

public class ValidaToken {

	private Boolean ok;
	private Usuario usuario;
	
	public Boolean getOk() {
		return ok;
	}
	public void setOk(Boolean ok) {
		this.ok = ok;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
