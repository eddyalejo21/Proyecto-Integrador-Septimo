package ec.gob.epmmop.appparking.servicio;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.accesoDatos.UsuarioAccesoDatos;
import ec.gob.epmmop.appparking.modelo.Usuario;

@Stateless
@LocalBean
public class UsuarioServicio {

	@EJB
	private UsuarioAccesoDatos accesoDatos;

	public void crearRegistro(Usuario usuario) throws Exception {
		accesoDatos.crearRegistro(usuario);
	}

	public void modificarRegistro(Usuario usuario) throws Exception {
		accesoDatos.modificarRegistro(usuario);
	}
	
	public Usuario validarUsuario(Usuario usuario) throws Exception {
		return accesoDatos.validarUsuario(usuario);
	}

	public Usuario buscarUsuario(String identificacion) throws Exception {
		return accesoDatos.buscarUsuario(identificacion);
	}

	public Usuario buscarUsuarioPorId(Integer idUsuario) throws Exception {
		return accesoDatos.buscarUsuarioPorId(idUsuario);
	}
}
