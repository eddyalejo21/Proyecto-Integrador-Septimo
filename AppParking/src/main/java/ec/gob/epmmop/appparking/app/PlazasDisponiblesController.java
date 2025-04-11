package ec.gob.epmmop.appparking.app;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.modelo.Estacionamiento;
import ec.gob.epmmop.appparking.servicio.EstacionamientoServicio;

@Stateless
public class PlazasDisponiblesController {
	
	@EJB
	private EstacionamientoServicio estacionamientoServicio;
	
	private List<Estacionamiento> listaEstacionamientos;
	
	private Estacionamiento estacionamiento;

	@Schedule(second = "*/15", minute = "*", hour = "*", persistent = false)
	public void actualizarDisponibilidad() {
		try {
			this.listaEstacionamientos = this.estacionamientoServicio.listarEstacionamientos();	
			
			for (Estacionamiento obj : this.listaEstacionamientos) {
				
				Integer valorInicial = 0;
				Integer valorFinal = obj.getEstCapacidad();
				
				int numeroAleatorio = ThreadLocalRandom.current().nextInt(valorInicial, valorFinal + 1);
				
				obj.setEstKalipso(numeroAleatorio);
				this.estacionamientoServicio.modificarRegistro(obj);
				
			}
			
			
			
			
		} catch (Exception e) {
			Logger.getLogger(PlazasDisponiblesController.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		
		
	}
}
