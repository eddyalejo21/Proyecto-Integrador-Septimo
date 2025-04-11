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

import ec.gob.epmmop.appparking.modelo.Transaccion;
import ec.gob.epmmop.utilitarios.ManejoDatosGenerico;

@Stateless
@LocalBean
public class TransaccionAccesoDatos extends ManejoDatosGenerico<Transaccion>
		implements Serializable {

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

	public TransaccionAccesoDatos() {
		super(Transaccion.class);
	}

	public void crearRegistro(Transaccion transaccion) throws Exception {
		this.create(transaccion);
	}

	public void modificarRegistro(Transaccion transaccion) throws Exception {
		this.edit(transaccion);
	}

	@SuppressWarnings("unchecked")
	public List<Transaccion> listarTransaccionesPorUsuario(Integer idCliente) throws Exception {
		List<Transaccion> lista = new ArrayList<Transaccion>();
		try {
			String consulta = "SELECT s FROM Transaccion s WHERE s.usuario.usuSecuencial in (?1) order by s.trxSecuencial desc";
			Query query = this.getEntityManager().createQuery(consulta);
			query.setParameter(1, idCliente);
			lista = query.getResultList();
		} catch (Exception e) {
			Logger.getLogger(TransaccionAccesoDatos.class.getName()).log(Level.SEVERE, "ERROR AL CARGAR LA LISTA");
		}
		return lista;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Transaccion> listarTransaccionesPendientes() throws Exception {
		List<Transaccion> lista = new ArrayList<Transaccion>();
		try {
			String consulta = "SELECT s FROM Transaccion s WHERE s.trxEstado = 'PENDIENTE' order by s.trxSecuencial desc";
			Query query = this.getEntityManager().createQuery(consulta);
			lista = query.getResultList();
		} catch (Exception e) {
			Logger.getLogger(TransaccionAccesoDatos.class.getName()).log(Level.SEVERE, "ERROR AL CARGAR LA LISTA");
		}
		return lista;
	}

	public Transaccion obtenerUltimaTransaccionPorUsuario(Integer idCliente, Integer requestId) throws Exception {
		Transaccion transaccion = new Transaccion();
		String consulta = "SELECT s FROM Transaccion s WHERE s.usuario.usuSecuencial in (?1) and s.trxRequestId in (?2) order by s.trxSecuencial desc";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, idCliente);
		query.setParameter(2, requestId);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Transaccion) query.getResultList().get(0);
		}
		return transaccion;
	}
	
	public Transaccion obtenerUltimaTransaccionPorUsuario(Integer idCliente) throws Exception {
		Transaccion transaccion = new Transaccion();
		String consulta = "SELECT s FROM Transaccion s WHERE s.usuario.usuSecuencial in (?1) order by s.trxSecuencial desc";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, idCliente);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Transaccion) query.getResultList().get(0);
		}
		return transaccion;
	}
	
	public Transaccion verificarUltimaTransaccionPorUsuario(Integer idCliente) throws Exception {
		Transaccion transaccion = new Transaccion();
		String consulta = "SELECT s FROM Transaccion s WHERE s.usuario.usuSecuencial in (?1) order by s.trxSecuencial desc";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, idCliente);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Transaccion) query.getResultList().get(0);
		}
		return transaccion;
	}
	
	public Transaccion obtenerTransaccionPorRequestId(Integer requestId, String referencia) throws Exception {
		Transaccion transaccion = new Transaccion();
		String consulta = "SELECT s FROM Transaccion s WHERE s.trxRequestId in (?1) and s.trxReferencia = ?2 ";
		Query query = this.getEntityManager().createQuery(consulta);
		query.setParameter(1, requestId);
		query.setParameter(2, referencia);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Transaccion) query.getResultList().get(0);
		}
		return transaccion;
	}
}
