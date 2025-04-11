package ec.gob.epmmop.appparking.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import ec.gob.epmmop.appparking.dto.RespuestaModificacion;
import ec.gob.epmmop.appparking.dto.RespuestaNotificacion;
import ec.gob.epmmop.appparking.dto.RespuestaVerificacion;
import ec.gob.epmmop.appparking.modelo.Parametros;
import ec.gob.epmmop.appparking.modelo.Transaccion;
import ec.gob.epmmop.appparking.modelo.Usuario;
import ec.gob.epmmop.appparking.servicio.ParametrosServicio;
import ec.gob.epmmop.appparking.servicio.TransaccionServicio;
import ec.gob.epmmop.appparking.servicio.UsuarioServicio;

@Path("/payments")
public class GestionPagosController {

	@EJB
	private ParametrosServicio parametrosServicio;
	
	@EJB
	private UsuarioServicio usuarioServicio;

	@EJB
	private TransaccionServicio transaccionServicio;

	private Transaccion transaccion;
	private List<Transaccion> listaTransaccion;
	private Usuario usuario;
	private Parametros parametros;

	private JsonObject jsonGeneration;
	private JsonObject jsonConsult;

	private AutenticacionSesionPlacetopay autenticacionSesionPlacetopay;
	private RespuestaNotificacion respuestaNotificacion;
	private RespuestaVerificacion respuestaVerificacion;
	private RespuestaModificacion respuestaModificacion;

	@POST
	@Path("/crearSesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crearSesionPago(String datosTicket) {
		try {
			
			System.out.println("Datos Ticket ==> " + datosTicket);
			
			this.parametros = new Parametros();
			this.parametros = this.parametrosServicio.obtenerParametros();
			
			System.out.println("Parametros ==> " + this.parametros.getPrmSecretKey());
			System.out.println("Parametros ==> " + this.parametros.getPrmLogin());
			System.out.println("Parametros ==> " + this.parametros.getPrmUrlReturn());
			System.out.println("Parametros ==> " + this.parametros.getPrmUrlCancel());

			String login = this.parametros.getPrmLogin();
			String secretKey = this.parametros.getPrmSecretKey();
			String returnUrl = this.parametros.getPrmUrlReturn();
			String cancelUrl = this.parametros.getPrmUrlCancel();

			this.autenticacionSesionPlacetopay = new AutenticacionSesionPlacetopay();
			this.jsonGeneration = this.autenticacionSesionPlacetopay.crearSesionPlacetopay(datosTicket, login,
					secretKey, returnUrl, cancelUrl);
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, "SESIÓN CREADA EXITOSAMENTE");

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, null, e);
		}
		return Response.status(200).entity(this.jsonGeneration.toString()).build();
	}

	@POST
	@Path("/registrarTransaccion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarTransaccion(String obj) {
		try {
			System.out.println("INICIA REGISTRO DE TRANSACCION");
			System.out.println("Se recibe el objeto => " + obj);
			this.transaccion = new Transaccion();

			JSONObject jsonTransform = new JSONObject(obj);

			Integer codigoUsuario = jsonTransform.getJSONObject("pago").getInt("codigoCliente");
			Integer codigoFormaDePago = jsonTransform.getJSONObject("pago").getInt("codigoFormaPago");
			BigDecimal valorTotal = jsonTransform.getJSONObject("pago").getBigDecimal("valorTotal");
			Integer requestId = jsonTransform.getJSONObject("pago").getInt("requestId");
			String processUrl = jsonTransform.getJSONObject("pago").getString("processUrl");
			String fecha = jsonTransform.getJSONObject("pago").getString("processDate");
			String descripcion = jsonTransform.getJSONObject("pago").getString("description");
			String referencia = jsonTransform.getJSONObject("pago").getString("reference");
//			String idTransaccion = jsonTransform.getJSONObject("pago").getString("idTransaccion");
			DateTime dt = new DateTime(fecha);

			this.usuario = this.usuarioServicio.buscarUsuarioPorId(codigoUsuario);

			this.transaccion.setUsuario(this.usuario);
			this.transaccion.setTrxRequestId(requestId);
//			this.transaccion.setTrxTransaccionId(idTransaccion);
			this.transaccion.setTrxUrl(processUrl);
			this.transaccion.setTrxMonto(valorTotal);
			this.transaccion.setTrxFechaRegistro(dt.toDate());
			this.transaccion.setTrxReferencia(referencia);
			this.transaccion.setTrxDetalle(descripcion);
			this.transaccion.setTrxEstado("PENDIENTE");
			this.transaccionServicio.crearRegistro(transaccion);

			return Response.status(200).entity(this.transaccion).build();

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, "ERROR AL GUARDAR TRANSACCION",
					e);
			return Response.status(500).entity(new Transaccion()).build();
		}
	}

	@POST
	@Path("/notificacion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response notificarPago(String respuestaPlacetopay) {
		try {
			JSONObject jsonTransform = new JSONObject(respuestaPlacetopay);
			String status = jsonTransform.getJSONObject("status").getString("status");
			String date = jsonTransform.getJSONObject("status").getString("date");
			String reason = jsonTransform.getJSONObject("status").getString("reason");
			Integer requestId = jsonTransform.getInt("requestId");
			String reference = jsonTransform.getString("reference");
			String signature = jsonTransform.getString("signature");
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.INFO, "RESPUESTA DE WEBHOOK >>> " + status + " - RequestId: " + requestId);

			this.parametros = new Parametros();
			this.parametros = this.parametrosServicio.obtenerParametros();
			String secretKey = this.parametros.getPrmSecretKey();
			String concatenacion = requestId.toString().concat(status).concat(date).concat(secretKey);
			String validateSignature = DigestUtils.sha1Hex(concatenacion);

			if (signature.equals(validateSignature.toString())) {
				Logger.getLogger(GestionPagosController.class.getName()).log(Level.INFO, "FIRMA CORRECTAMENTE VALIDADA DE PLACETOPAY");

				this.transaccion = this.transaccionServicio.obtenerTransaccionPorRequestId(requestId, reference);
				String respuesta = consultaServicio(requestId);

				JSONObject jsonResponse = new JSONObject(respuesta);

				String valorStatus = jsonResponse.getJSONObject("status").getString("status");
				String valorDate = jsonResponse.getJSONObject("status").getString("date");
				DateTime fecha = new DateTime(valorDate);
				String authorization = "";

				if (valorStatus.equalsIgnoreCase("APPROVED")) {
					JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
					authorization = arrayPayment.getJSONObject(0).getString("authorization");
					this.transaccion.setTrxEstado("APROBADO");
					this.transaccion.setTrxFechaRegistro(fecha.toDate());
					this.transaccion.setTrxAutorizacion(authorization);
					this.transaccionServicio.modificarRegistro(this.transaccion);
					
				} else if (valorStatus.equalsIgnoreCase("REJECTED")) {
					try {
						JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
						authorization = arrayPayment.getJSONObject(0).getString("authorization");
						this.transaccion.setTrxEstado("RECHAZADO");						
						this.transaccion.setTrxFechaRegistro(fecha.toDate());
						this.transaccion.setTrxAutorizacion(authorization);
						this.transaccionServicio.modificarRegistro(this.transaccion);
					} catch (Exception e) {
						this.transaccion.setTrxEstado("RECHAZADO");
						this.transaccion.setTrxFechaRegistro(fecha.toDate());
						this.transaccion.setTrxAutorizacion("N/A");
						this.transaccionServicio.modificarRegistro(this.transaccion);
					}
				} else if (valorStatus.equals("PENDING")) {
					if (reason.equals("PT")) {
						JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
						authorization = arrayPayment.getJSONObject(0).getString("authorization");
						this.transaccion.setTrxEstado("PENDIENTE");
						this.transaccion.setTrxFechaRegistro(fecha.toDate());
						this.transaccionServicio.modificarRegistro(this.transaccion);
					}
				}
				
				this.respuestaNotificacion = new RespuestaNotificacion();
				this.respuestaNotificacion.setStatus(true);
				this.respuestaNotificacion.setMessage("Signature fue validado correctamente");

			} else {
				this.respuestaNotificacion.setStatus(false);
				this.respuestaNotificacion.setMessage("Signature no pudo ser validado correctamente");
				Logger.getLogger(GestionPagosController.class.getName()).log(Level.INFO, "LA FIRMA NO PUDO SER VALIDADA DESDE PLACETOPAY");
			}

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, null, e);
		}
		return Response.status(200).entity(this.respuestaNotificacion).build();
	}

	public String consultaServicio(Integer requestId) {
		HttpURLConnection connection = null;
		InputStream is = null;
		JsonObject json;

		try {
			this.parametros = new Parametros();
			this.parametros = this.parametrosServicio.obtenerParametros();

			String login = this.parametros.getPrmLogin();
			String secretKey = this.parametros.getPrmSecretKey();

			// Fecha con Formato
			Date fechaISO = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			String seed = sdf.format(fechaISO);

			// Nonce
			int valorRandomico = (int) (Math.random() * 1000000000 + 1);
			String rawNonce = String.valueOf(valorRandomico);

			String concatenacion = rawNonce + seed + secretKey;
			String tranKey = "";
			String nonce = "";

			Base64 base64 = new Base64();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(concatenacion.getBytes("UTF-8"));

			tranKey = base64.encodeToString(hash);
			nonce = base64.encodeToString(String.valueOf(rawNonce).getBytes());

			json = Json.createObjectBuilder().add("locale", "es_EC").add("auth", Json.createObjectBuilder()
					.add("login", login).add("tranKey", tranKey).add("nonce", nonce).add("seed", seed)).build();

			String requestJSON = json.toString();

			// Create connection
			URL url = new URL(this.parametros.getPrmUrl().concat("/api/session/" + requestId));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", Integer.toString(requestJSON.getBytes().length));
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(requestJSON);
			wr.close();

			// Get Response
			try {
				is = connection.getInputStream();
			} catch (IOException ioe) {
				if (connection instanceof HttpURLConnection) {
					HttpURLConnection httpConn = (HttpURLConnection) connection;
					int statusCode = httpConn.getResponseCode();
					if (statusCode != 200) {
						is = httpConn.getErrorStream();
					}
				}
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); 
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, null, e);
			return null;

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	@GET
	@Path("/consultarTransaccion/{idCliente}")
	@Produces("application/json")
	public Response buscarUltimaTransaccion(@PathParam("idCliente") Integer codigoUsuario) {
		try {
			this.transaccion = this.transaccionServicio.obtenerUltimaTransaccionPorUsuario(codigoUsuario);

		} catch (Exception e) {
			Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "FALLA CONEXIÓN CON SERVIDOR");
		}
		return Response.status(200).entity(this.transaccion).build();
	}

	@GET
	@Path("/consultarHistorial/{idCliente}")
	@Produces("application/json")
	public Response listarTransacciones(@PathParam("idCliente") Integer codigoUsuario) {
		try {
			this.listaTransaccion =  this.transaccionServicio.listarTransaccionesPorUsuario(codigoUsuario);

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, "FALLA CONEXIÓN CON SERVIDOR");

		}
		return Response.status(200).entity(this.listaTransaccion).build();
	}
	
	@GET
	@Path("/verificarPendientes/{idCliente}")
	@Produces("application/json")
	public Response verificarPendientes(@PathParam("idCliente") Integer codigoUsuario) {
		try {
			this.transaccion = this.transaccionServicio.verificarUltimaTransaccionPorUsuario(codigoUsuario);
			this.respuestaVerificacion = new RespuestaVerificacion();
			
			if (this.transaccion.getTrxSecuencial() != null) {
				if (this.transaccion.getTrxEstado().equalsIgnoreCase("PENDIENTE")) {
					this.respuestaVerificacion.setStatus(true);
					this.respuestaVerificacion.setMessage("EXISTE UNA TRANSACCIÓN PENDIENTE");
					this.respuestaVerificacion.setReference(this.transaccion.getTrxReferencia());
					this.respuestaVerificacion.setUrlProcess(this.transaccion.getTrxUrl());
				}else {
					this.respuestaVerificacion.setStatus(false);
					this.respuestaVerificacion.setMessage("NO EXISTEN TRANSACCIONES PENDIENTES");
					this.respuestaVerificacion.setReference(null);
					this.respuestaVerificacion.setUrlProcess(null);
				}
			}else {
				this.respuestaVerificacion.setStatus(false);
				this.respuestaVerificacion.setMessage("NO EXISTEN TRANSACCIONES PARA EL USUARIO");
				this.respuestaVerificacion.setReference(null);
				this.respuestaVerificacion.setUrlProcess(null);
			}
			
		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, "FALLA CONEXIÓN CON SERVIDOR verifica", e);

		}
		return Response.status(200).entity(this.respuestaVerificacion).build();
	}
	
	@GET
	@Path("/consultarSesion")
	@Produces("application/json")
	public Response consultarSesionPago() {
		try {

			System.out.println("Ingresa al metodo");

			this.parametros = new Parametros();
			this.parametros = this.parametrosServicio.obtenerParametros();

			String login = this.parametros.getPrmLogin();
			String secretKey = this.parametros.getPrmSecretKey();

			// Fecha con Formato
			Date fechaISO = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			String seed = sdf.format(fechaISO);

			// Nonce
			int valorRandomico = (int) (Math.random() * 1000000000 + 1);
			String rawNonce = String.valueOf(valorRandomico);

			String concatenacion = rawNonce + seed + secretKey;
			String tranKey = "";
			String nonce = "";

			Base64 base64 = new Base64();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(concatenacion.getBytes("UTF-8"));

			tranKey = base64.encodeToString(hash);
			nonce = base64.encodeToString(String.valueOf(rawNonce).getBytes());

			this.jsonConsult = Json.createObjectBuilder().add("locale", "es_EC").add("auth", Json.createObjectBuilder()
					.add("login", login).add("tranKey", tranKey).add("nonce", nonce).add("seed", seed)).build();

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, null, e);
		}
		return Response.status(200).entity(this.jsonConsult.toString()).build();
	}

	@POST
	@Path("/modificarTransaccion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarSesionPago(String obj) {
		try {						
			this.transaccion = new Transaccion();
			JSONObject jsonTransform = new JSONObject(obj);

			Integer codigoCliente = jsonTransform.getJSONObject("respuesta").getInt("usuario");
			Integer requestId = jsonTransform.getJSONObject("respuesta").getInt("requestId");		
			this.transaccion = this.transaccionServicio.obtenerUltimaTransaccionPorUsuario(codigoCliente, requestId);
			
			String respuesta = consultaServicio(requestId);
			
			JSONObject jsonResponse = new JSONObject(respuesta);

			String valorStatus = jsonResponse.getJSONObject("status").getString("status");
			String valorDate = jsonResponse.getJSONObject("status").getString("date");
			DateTime fecha = new DateTime(valorDate);
			String authorization = "";
			
			this.respuestaModificacion = new RespuestaModificacion();

			if (valorStatus.equalsIgnoreCase("APPROVED")) {
				JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
				authorization = arrayPayment.getJSONObject(0).getString("authorization");
				this.transaccion.setTrxEstado("APROBADO");
				this.transaccion.setTrxFechaRegistro(fecha.toDate());
				this.transaccion.setTrxAutorizacion(authorization);
				this.transaccionServicio.modificarRegistro(this.transaccion);
				this.respuestaModificacion.setStatus(true);
				this.respuestaModificacion.setMensaje("APPROVED");
				
			} else if (valorStatus.equalsIgnoreCase("REJECTED")) {
				try {
					JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
					authorization = arrayPayment.getJSONObject(0).getString("authorization");
					this.transaccion.setTrxEstado("RECHAZADO");					
					this.transaccion.setTrxFechaRegistro(fecha.toDate());
					this.transaccion.setTrxAutorizacion(authorization);
					this.transaccionServicio.modificarRegistro(this.transaccion);
					this.respuestaModificacion.setStatus(false);
					this.respuestaModificacion.setMensaje("REJECTED");
				} catch (Exception e) {
					this.transaccion.setTrxEstado("RECHAZADO");
					this.transaccion.setTrxFechaRegistro(fecha.toDate());
					this.transaccion.setTrxAutorizacion("N/A");
					this.transaccionServicio.modificarRegistro(this.transaccion);
					this.respuestaModificacion.setStatus(false);
					this.respuestaModificacion.setMensaje("REJECTED");
				}
			} else if (valorStatus.equals("PENDING")) {				
					JSONArray arrayPayment = jsonResponse.getJSONArray("payment");
					authorization = arrayPayment.getJSONObject(0).getString("authorization");
					this.transaccion.setTrxEstado("PENDIENTE");
					this.transaccion.setTrxFechaRegistro(fecha.toDate());
					this.transaccionServicio.modificarRegistro(this.transaccion);	
					this.respuestaModificacion.setStatus(false);
					this.respuestaModificacion.setMensaje("PENDING");
			}

		} catch (Exception e) {
			Logger.getLogger(GestionPagosController.class.getName()).log(Level.SEVERE, null, e);
			this.respuestaModificacion.setStatus(false);
			this.respuestaModificacion.setMensaje(e.toString());
			return Response.status(200).entity(this.respuestaModificacion).build();
		}
		return Response.status(200).entity(this.respuestaModificacion).build();
	}

}
