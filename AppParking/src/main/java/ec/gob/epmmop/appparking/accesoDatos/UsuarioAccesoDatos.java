package ec.gob.epmmop.appparking.accesoDatos;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.epmmop.appparking.modelo.Usuario;
import ec.gob.epmmop.utilitarios.ManejoDatosGenerico;

@Stateless
@LocalBean
public class UsuarioAccesoDatos  extends ManejoDatosGenerico<Usuario> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "bd_app_parking")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public UsuarioAccesoDatos() {
		super(Usuario.class);
	}
	
	public void crearRegistro(Usuario usuario) throws Exception {
		this.create(usuario);
	}

	public void modificarRegistro(Usuario usuario) throws Exception {
		this.edit(usuario);
	}
	
	public Usuario validarUsuario(Usuario usuario) throws Exception {
		String consulta = "SELECT u FROM Usuario u WHERE u.usuIdentificacion = '"+usuario.getUsuIdentificacion()+"' AND u.usuEstadoAh ='A'";
		Query query = this.getEntityManager().createQuery(consulta);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Usuario) query.getResultList().get(0);
		}
		return usuario;
	}
	
	public Usuario buscarUsuario(String identificacion) throws Exception{
		Usuario usuario = new Usuario();
		String consulta = "SELECT p FROM Usuario p WHERE p.usuIdentificacion = '"+identificacion+"' and p.usuEstadoAh = 'A' ";
		Query query = this.getEntityManager().createQuery(consulta);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Usuario) query.getResultList().get(0);
		}
		return usuario;
	}
	
	public Usuario buscarUsuarioPorId(Integer idUsuario) throws Exception{
		Usuario usuario = new Usuario();
		String consulta = "SELECT p FROM Usuario p WHERE p.usuSecuencial =  ?1 ";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, idUsuario);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Usuario) query.getResultList().get(0);
		}
		return usuario;
	}
	
}
