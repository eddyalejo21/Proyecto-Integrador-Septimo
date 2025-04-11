package ec.gob.epmmop.appparking.app;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import ec.gob.epmmop.appparking.dto.RespuestaToken;
import ec.gob.epmmop.appparking.dto.ValidaToken;
import ec.gob.epmmop.appparking.modelo.Usuario;
import ec.gob.epmmop.appparking.servicio.UsuarioServicio;
import ec.gob.epmmop.utilitarios.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Path("/usuarios")
public class UsuarioController extends Utils implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioServicio usuarioServicio;
	
	private Usuario usuario;
	private String claveBackHash;
	private String claveFront;
	private RespuestaToken respuestaToken;
	private ValidaToken validaToken;

	@POST
	@Path("/registrarUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarUsuario(Usuario usr) {
		try {
			this.usuario = new Usuario();
			this.usuario.setUsuIdentificacion(usr.getUsuIdentificacion());
			this.usuario.setUsuNombres(usr.getUsuNombres());
			this.usuario.setUsuApellidos(usr.getUsuApellidos());
			this.usuario.setUsuCorreo(usr.getUsuCorreo());
			this.usuario.setUsuTelefono(usr.getUsuTelefono());
			this.usuario.setUsuClave(BCrypt.hashpw(usr.getUsuClave(), BCrypt.gensalt()));
			this.usuario.setUsuToken(12345);
			this.usuario.setUsuEstadoAh('A');
			this.usuarioServicio.crearRegistro(this.usuario);
			
			return Response.ok(this.usuario).build();
			
		} catch (Exception e) {
			Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "FALLA CONEXIÓN CON SERVIDOR", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al registrar usuario").build();
		}
	}
	
	@POST
	@Path("/iniciarSesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response iniciarSesion(Usuario usr) {
		try {
			this.respuestaToken = new RespuestaToken();
			this.respuestaToken.setOk(false);
			this.usuario = this.usuarioServicio.buscarUsuario(usr.getUsuIdentificacion());

			if (this.usuario.getUsuSecuencial() != null) {
				this.claveFront = usr.getUsuClave();
				this.claveBackHash = this.usuario.getUsuClave();

				if (BCrypt.checkpw(claveFront, claveBackHash)) {
					this.respuestaToken.setOk(true);
					String token = this.generarToken(this.usuario);
					this.respuestaToken.setToken(token);

					return Response.status(Response.Status.CREATED).entity(this.respuestaToken).build();
				} else {
					return Response.status(Response.Status.OK).entity(this.respuestaToken).build();
				}
			} else {
				return Response.status(Response.Status.OK).entity(this.respuestaToken).build();
			}
		} catch (Exception e) {
			Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "FALLA CONEXIÓN CON SERVIDOR");
			return Response.status(500).entity(this.respuestaToken).build();
		}
	}
	
	@GET
	@Path("/obtenerUsuarioPorToken")
	@Produces("application/json")
	public Response obtenerUsuarioPorToken(@HeaderParam("x-token") String token) {
		try {

			this.validaToken = new ValidaToken();
			Jws<Claims> resultado = validarToken(token);
			System.out.println(resultado.getBody().getSubject());

			this.usuario = this.usuarioServicio.buscarUsuario(resultado.getBody().getSubject());
			this.validaToken.setOk(true);
			this.validaToken.setUsuario(this.usuario);
//
			return Response.status(Response.Status.OK).entity(this.validaToken).build();

		} catch (Exception e) {
			Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "FALLA AUTENTICACIÓN DE TOKEN");
			this.validaToken.setOk(false);
			this.validaToken.setUsuario(new Usuario());
			return Response.status(Response.Status.UNAUTHORIZED).entity("error").build();
		}
	}


	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
