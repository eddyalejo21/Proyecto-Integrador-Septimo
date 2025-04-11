package ec.gob.epmmop.appparking.servicio;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.accesoDatos.TicketAccesoDatos;
import ec.gob.epmmop.appparking.modelo.Ticket;

@Stateless
@LocalBean
public class TicketServicio {

	@EJB
	private TicketAccesoDatos accesoDatos;

	public Ticket obtenerTicketPorId(String idTicket) throws Exception {
		return accesoDatos.obtenerTicketPorId(idTicket);
	}
}
