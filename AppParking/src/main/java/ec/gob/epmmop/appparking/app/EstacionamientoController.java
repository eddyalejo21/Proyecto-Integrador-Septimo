package ec.gob.epmmop.appparking.app;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ec.gob.epmmop.appparking.modelo.Estacionamiento;
import ec.gob.epmmop.appparking.servicio.EstacionamientoServicio;

@Path("/estacionamientos")
public class EstacionamientoController {

	@Inject
	private EstacionamientoServicio estacionamientoServicio;

	private List<Estacionamiento> listaEstacionamiento;
	private Estacionamiento estacionamiento;

	@GET
	@Path("/listar")
	@Produces("application/json")
	public Response listarEstacionamientos() {
		try {
			this.listaEstacionamiento = this.estacionamientoServicio.listarEstacionamientos();
			return Response.ok(this.listaEstacionamiento).build();

		} catch (Exception e) {
			Logger.getLogger(EstacionamientoController.class.getName()).log(Level.SEVERE, null, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener estacionamientos").build();
		}
	}
	
	@GET
	@Path("/obtener/{idEstacionamiento}")
	@Produces("application/json")
	public Response ObtenerEstacionamiento(@PathParam("idEstacionamiento") Integer idEstacionamiento) {
		try {
			this.estacionamiento = this.estacionamientoServicio.obtenerEstacionamientoPorId(idEstacionamiento);
			return Response.ok(this.estacionamiento).build();

		} catch (Exception e) {
			Logger.getLogger(EstacionamientoController.class.getName()).log(Level.SEVERE, null, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener estacionamiento").build();
		}
	}

}
