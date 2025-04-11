package ec.gob.epmmop.appparking.accesoDatos;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.epmmop.appparking.modelo.Parametros;
import ec.gob.epmmop.utilitarios.ManejoDatosGenerico;

@Stateless
@LocalBean
public class ParametrosAccesoDatos extends ManejoDatosGenerico<Parametros> implements Serializable {

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
	
	public ParametrosAccesoDatos() {
		super(Parametros.class);
	}
	
	public Parametros obtenerParametros() throws Exception {
		Parametros parametros = new Parametros();
		String consulta = "SELECT p FROM Parametros p WHERE p.prmSecuencial = 1";
		Query query = this.getEntityManager().createQuery(consulta);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Parametros) query.getResultList().get(0);
		}
		return parametros;
	}
}
