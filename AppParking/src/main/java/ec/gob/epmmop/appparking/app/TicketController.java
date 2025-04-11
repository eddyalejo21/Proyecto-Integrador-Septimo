package ec.gob.epmmop.appparking.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ec.gob.epmmop.appparking.modelo.Ticket;
import ec.gob.epmmop.appparking.servicio.TicketServicio;

@Path("/tickets")
public class TicketController {

	@Inject
	private TicketServicio ticketServicio;

	private Ticket ticket;

	@GET
	@Path("/obtenerTicket/{codigoTicket}")
	@Produces("application/json")
	public Response ObtenerTicket(@PathParam("codigoTicket") String codigoTicket) {
		try {
			this.ticket = this.ticketServicio.obtenerTicketPorId(codigoTicket);

			if (this.ticket.getTicSecuencial() != null) {
				String tiempoPermanencia = this.calcularPermanencia(this.ticket.getTicIngreso());
				Long horasPermanencia = this.calcularHoras(this.ticket.getTicIngreso());

				BigDecimal totalPagar = new BigDecimal(
						this.calcularValorAPagar(this.ticket.getTicIngreso(), new Date()));
				BigDecimal iva = new BigDecimal(calcularIVA(totalPagar.doubleValue())).setScale(2,
						RoundingMode.HALF_UP);
				BigDecimal subtotal = new BigDecimal(calcularSubtotal(totalPagar.doubleValue())).setScale(2,
						RoundingMode.HALF_UP);
				String fechaIngreso = this.convertirFecha(this.ticket.getTicIngreso());

				this.ticket.setValorTotal(totalPagar);
				this.ticket.setValorIva(iva);
				this.ticket.setValorSubtotal(subtotal);
				this.ticket.setTiempoPermanencia(tiempoPermanencia);
				this.ticket.setIngreso(fechaIngreso);
				this.ticket.setNombreEstacionamiento(this.ticket.getEstacionamiento().getEstNombre());
				return Response.ok(this.ticket).build();
			} else {
				return Response.status(Response.Status.NO_CONTENT).entity("El ticket no existe").build();
			}

		} catch (Exception e) {
			Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener ticket").build();
		}
	}

	public String convertirFecha(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaFormateada = sdf.format(fecha);
		return fechaFormateada;
	}

	public String calcularPermanencia(Date fecha) {
		Date fechaIngreso = fecha;
		Date fechaActual = new Date();

		long diferenciaMillis = fechaActual.getTime() - fechaIngreso.getTime();
		long horasTranscurridas = TimeUnit.MILLISECONDS.toHours(diferenciaMillis);
		long minutosTranscurridos = TimeUnit.MILLISECONDS.toMinutes(diferenciaMillis);
		long horas = minutosTranscurridos / 60;
		long minutos = minutosTranscurridos % 60;

		String tiempo = horas + " horas y " + minutos + " minutos";

		return tiempo;
	}

	public static double calcularValorAPagar(Date ingreso, Date salida) {
		long tiempoMillis = ingreso.getTime();

		double total = 0.0;

		while (tiempoMillis < salida.getTime()) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(tiempoMillis);

			int hora = cal.get(Calendar.HOUR_OF_DAY);

			// Determinar si la hora actual es diurna o nocturna
			boolean esDiurna = hora >= 6 && hora < 18;
			double tarifaHora = esDiurna ? 0.75 : 0.90;

			// Sumar una hora
			tiempoMillis += TimeUnit.HOURS.toMillis(1);

			// Si ya pasamos la hora de salida, no se cuenta esa hora
			if (tiempoMillis > salida.getTime()) {
				break;
			}

			total += tarifaHora;
		}

		return total;
	}

	public static double calcularIVA(double total) {
		return total * 15 / 115;
	}

	public static double calcularSubtotal(double total) {
		return total - calcularIVA(total);
	}

	public Long calcularHoras(Date fecha) {
		Date fechaIngreso = fecha;
		Date fechaActual = new Date();

		Long diferenciaMillis = fechaActual.getTime() - fechaIngreso.getTime();
		Long horasTranscurridas = TimeUnit.MILLISECONDS.toHours(diferenciaMillis);

		return horasTranscurridas;
	}
}
