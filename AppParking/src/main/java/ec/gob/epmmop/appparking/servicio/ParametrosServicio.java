package ec.gob.epmmop.appparking.servicio;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.accesoDatos.ParametrosAccesoDatos;
import ec.gob.epmmop.appparking.modelo.Parametros;

@Stateless
@LocalBean
public class ParametrosServicio {

	@EJB
	private ParametrosAccesoDatos accesoDatos;

	public Parametros obtenerParametros() throws Exception {
		return accesoDatos.obtenerParametros();
	}
	
	
}
