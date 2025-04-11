package ec.gob.epmmop.appparking.accesoDatos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.epmmop.appparking.modelo.Estacionamiento;
import ec.gob.epmmop.utilitarios.ManejoDatosGenerico;

@Stateless
@LocalBean
public class EstacionamientoAccesoDatos extends ManejoDatosGenerico<Estacionamiento> implements Serializable {
	
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

	public EstacionamientoAccesoDatos() {
		super(Estacionamiento.class);
	}
	
	public void modificarRegistro(Estacionamiento estacionamiento) throws Exception {
		this.edit(estacionamiento);
	}

	@SuppressWarnings("unchecked")
	public List<Estacionamiento> listarEstacionamientos() throws Exception {
		List<Estacionamiento> lista = new ArrayList<Estacionamiento>();
		try {
			String consulta = "SELECT e FROM Estacionamiento e WHERE e.estEstadoAh ='A' order by e.estNombre";
			Query query = this.getEntityManager().createQuery(consulta);
			lista = query.getResultList();
		} catch (Exception e) {
			Logger.getLogger(EstacionamientoAccesoDatos.class.getName()).log(Level.SEVERE, "ERROR AL CARGAR LA LISTA");
		}
		return lista;		
	}
	
	public Estacionamiento obtenerEstacionamientoPorId(Integer idEstacionamiento) throws Exception {
		Estacionamiento estacionamiento = new Estacionamiento();
		String consulta = "SELECT e FROM Estacionamiento e WHERE e.estSecuencial = ?1 ";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, idEstacionamiento);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Estacionamiento) query.getResultList().get(0);
		}
		return estacionamiento;
	}
}
