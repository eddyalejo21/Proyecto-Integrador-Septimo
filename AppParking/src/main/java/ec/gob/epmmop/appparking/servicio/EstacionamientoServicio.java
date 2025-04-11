package ec.gob.epmmop.appparking.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.accesoDatos.EstacionamientoAccesoDatos;
import ec.gob.epmmop.appparking.modelo.Estacionamiento;

@Stateless
@LocalBean
public class EstacionamientoServicio {

	@EJB
	private EstacionamientoAccesoDatos accesoDatos;
	
	public List<Estacionamiento> listarEstacionamientos() throws Exception {
		return accesoDatos.listarEstacionamientos();
	}
	
	public Estacionamiento obtenerEstacionamientoPorId(Integer idEstacionamiento) throws Exception {
		return accesoDatos.obtenerEstacionamientoPorId(idEstacionamiento);
	}

	public void modificarRegistro(Estacionamiento estacionamiento) throws Exception {
		accesoDatos.modificarRegistro(estacionamiento);
	}
	
	
}
