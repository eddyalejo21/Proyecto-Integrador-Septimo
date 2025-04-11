package ec.gob.epmmop.appparking.accesoDatos;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.omg.CosNaming.IstringHelper;

import ec.gob.epmmop.appparking.modelo.Ticket;
import ec.gob.epmmop.utilitarios.ManejoDatosGenerico;

@Stateless
@LocalBean
public class TicketAccesoDatos extends ManejoDatosGenerico<Ticket> implements Serializable {

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

	public TicketAccesoDatos() {
		super(Ticket.class);
	}
	
	public Ticket obtenerTicketPorId(String idTicket) throws Exception {
		Ticket ticket = new Ticket();
		String consulta = "SELECT e FROM Ticket e WHERE e.ticTicket = '"+idTicket+"' AND e.ticEstadoAh = 'A' ";
		Query query = this.getEntityManager().createQuery(consulta);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			return (Ticket) query.getResultList().get(0);
		}
		return ticket;
	}
}
