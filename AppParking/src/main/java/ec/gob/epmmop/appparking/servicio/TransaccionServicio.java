package ec.gob.epmmop.appparking.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.gob.epmmop.appparking.accesoDatos.TransaccionAccesoDatos;
import ec.gob.epmmop.appparking.modelo.Transaccion;

@Stateless
@LocalBean
public class TransaccionServicio {

	@EJB
	private TransaccionAccesoDatos accesoDatos;

	public void crearRegistro(Transaccion transaccion) throws Exception {
		accesoDatos.crearRegistro(transaccion);
	}

	public void modificarRegistro(Transaccion transaccion) throws Exception {
		accesoDatos.modificarRegistro(transaccion);
	}

	public List<Transaccion> listarTransaccionesPorUsuario(Integer idCliente) throws Exception {
		return accesoDatos.listarTransaccionesPorUsuario(idCliente);
	}

	public Transaccion obtenerUltimaTransaccionPorUsuario(Integer idCliente, Integer requestId) throws Exception {
		return accesoDatos.obtenerUltimaTransaccionPorUsuario(idCliente, requestId);
	}

	public Transaccion obtenerTransaccionPorRequestId(Integer requestId, String referencia) throws Exception {
		return accesoDatos.obtenerTransaccionPorRequestId(requestId, referencia);
	}

	public List<Transaccion> listarTransaccionesPendientes() throws Exception {
		return accesoDatos.listarTransaccionesPendientes();
	}

	public Transaccion verificarUltimaTransaccionPorUsuario(Integer idCliente) throws Exception {
		return accesoDatos.verificarUltimaTransaccionPorUsuario(idCliente);
	}

	public Transaccion obtenerUltimaTransaccionPorUsuario(Integer idCliente) throws Exception {
		return accesoDatos.obtenerUltimaTransaccionPorUsuario(idCliente);
	}
	
	
}
