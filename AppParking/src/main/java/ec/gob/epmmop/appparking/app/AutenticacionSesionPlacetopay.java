package ec.gob.epmmop.appparking.app;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.commons.codec.binary.Base64;
//import org.primefaces.json.JSONObject;
import org.json.JSONObject;

import ec.gob.epmmop.appparking.dto.InformacionPago;

@ManagedBean
@ViewScoped
public class AutenticacionSesionPlacetopay {
	
	private InformacionPago informacionPago;
	
	private JsonObject jsonGeneration;
	private JsonObject jsonAuthentication;
	private String nombresCompletos;
	private String apellidosCompletos;
	
	public JsonObject crearSesionPlacetopay(String datosTicket, String login, String secretKey, String returnUrl, String cancelUrl) {
		try {

			this.informacionPago = this.obtenerDatosCrearSesion(datosTicket);
						
			String referencia = this.informacionPago.getCodigoTicket();

			String seed = this.generarSeed();
			String seedExpiration = this.generarFechaExpiracion(new Date());
			String rawNonce = this.generarNonce();
			String rawTranKey = this.generarCadenaTrankey(rawNonce, seed, secretKey);
			String nonce = this.encriptarNonce(rawNonce);
			String tranKey = this.encriptarTrankey(rawTranKey);
			
			this.jsonGeneration = Json.createObjectBuilder().add("locale", "es_EC")
					.add("auth",
							Json.createObjectBuilder().add("login", login).add("tranKey", tranKey).add("nonce", nonce)
									.add("seed", seed))
					.add("payment",
							Json.createObjectBuilder().add("reference", referencia)
									.add("description", "PAGO ESTACIONAMIENTO " + this.informacionPago.getCentroRecaudacion()).add("amount",
											Json.createObjectBuilder().add("taxes", Json.createArrayBuilder()
													.add(Json.createObjectBuilder().add("kind", "valueAddedTax")
															.add("base", this.informacionPago.getBaseImponible()).add("amount", this.informacionPago.getValorIva())))
													.add("currency", "USD").add("total", this.informacionPago.getValorTotal())))
					.add("buyer",
							Json.createObjectBuilder().add("name", this.nombresCompletos).add("surname", this.apellidosCompletos)
									.add("document", this.informacionPago.getCedulaUsuario()).add("email", this.informacionPago.getEmailUsuario()).add("documentType", "CI")
									.add("mobile", this.informacionPago.getTelefonoUsuario()))
					.add("expiration", seedExpiration)
					.add("returnUrl",	returnUrl)
					.add("cancelUrl", cancelUrl)
					.add("ipAddress", this.informacionPago.getDireccionIp()).add("userAgent", "PlacetoPay Sandbox").build();
			
		} catch (Exception e) {
			Logger.getLogger(AutenticacionSesionPlacetopay.class.getName()).log(Level.SEVERE, null, e);
		}
		return this.jsonGeneration;
	}
	
	public JsonObject consultarSesionPlacetopay(String login, String secretKey) {
		try {						
			String seed = this.generarSeed();
			String rawNonce = this.generarNonce();
			String rawTranKey = this.generarCadenaTrankey(rawNonce, seed, secretKey);
			String nonce = this.encriptarNonce(rawNonce);
			String tranKey = this.encriptarTrankey(rawTranKey);
			
			this.jsonAuthentication = Json.createObjectBuilder().add("locale", "es_EC")
					.add("auth",
							Json.createObjectBuilder().add("login", login).add("tranKey", tranKey).add("nonce", nonce)
									.add("seed", seed)).build();
			
		} catch (Exception e) {
			Logger.getLogger(AutenticacionSesionPlacetopay.class.getName()).log(Level.SEVERE, null, e);
		}
		return this.jsonAuthentication;
	}

	public InformacionPago obtenerDatosCrearSesion(String datosTicket) {
		try {
			JSONObject jsonTransform = new JSONObject(datosTicket);
			this.informacionPago = new InformacionPago();
			
			this.informacionPago.setNombreUsuario(jsonTransform.getJSONObject("usuario").getString("nombre"));
			this.informacionPago.setEmailUsuario(jsonTransform.getJSONObject("usuario").getString("email"));
			this.informacionPago.setCedulaUsuario(jsonTransform.getJSONObject("usuario").getString("document"));
			this.informacionPago.setTelefonoUsuario(jsonTransform.getJSONObject("usuario").getString("mobile"));
			this.informacionPago.setCentroRecaudacion(jsonTransform.getJSONObject("ticket").getString("centro"));
			this.informacionPago.setBaseImponible(jsonTransform.getJSONObject("ticket").getDouble("baseImponible"));
			this.informacionPago.setValorIva(jsonTransform.getJSONObject("ticket").getDouble("valorIva"));
			this.informacionPago.setValorTotal(jsonTransform.getJSONObject("ticket").getDouble("valorTotal"));
			this.informacionPago.setCodigoTicket(jsonTransform.getJSONObject("ticket").getString("codigoTicket"));
			
			InetAddress ip = InetAddress.getLocalHost();
			this.informacionPago.setDireccionIp(ip.getHostAddress());
			this.separacionDeNombres(this.informacionPago.getNombreUsuario());

		} catch (Exception e) {
			Logger.getLogger(AutenticacionSesionPlacetopay.class.getName()).log(Level.SEVERE,
					"ERROR EN OBTENCIÓN DE INFORMACIÓN DE PAGO");
		}

		return this.informacionPago;
	}

	public void separacionDeNombres(String nombreDinardap) {

		StringTokenizer st = new StringTokenizer(nombreDinardap);
		int cantidadPalabras = st.countTokens();

		String[] partes = new String[cantidadPalabras];
		this.nombresCompletos = "";
		this.apellidosCompletos = "";
		Integer cortadorInicial = 0;
		Integer cortadorFinal = 0;

		int i = 0;

		while (st.hasMoreTokens()) {
			String str = st.nextToken();
			partes[i] = str;
			i++;
		}

		if (cantidadPalabras == 4) {
			this.apellidosCompletos = partes[0].concat(" ").concat(partes[1]);
			cortadorInicial = this.apellidosCompletos.length();
			cortadorFinal = nombreDinardap.length();
			this.nombresCompletos = nombreDinardap.substring(cortadorInicial, cortadorFinal).trim();
		} else if (cantidadPalabras > 5) {
			this.apellidosCompletos = partes[0].concat(" ").concat(partes[1]).concat(" ").concat(partes[2]).concat(" ")
					.concat(partes[3]);
			cortadorInicial = this.apellidosCompletos.length();
			cortadorFinal = nombreDinardap.length();
			this.nombresCompletos = nombreDinardap.substring(cortadorInicial, cortadorFinal);
		} else if (cantidadPalabras == 5) {
			this.apellidosCompletos = partes[0].concat(" ").concat(partes[1]).concat(" ").concat(partes[2]);
			cortadorInicial = this.apellidosCompletos.length();
			cortadorFinal = nombreDinardap.length();
			this.nombresCompletos = nombreDinardap.substring(cortadorInicial, cortadorFinal);
		}
	}
	
	public String generarSeed() {
		Date fechaISO = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String seed = sdf.format(fechaISO);
		return seed;
	}

	public String generarFechaExpiracion(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		Date fechaTemporal = calendar.getTime();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 10);
		fechaTemporal = calendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String fechaExpiracion = sdf.format(fechaTemporal);

		return fechaExpiracion;
	}
	
	public String generarNonce() {
		int valorRandomico = (int) (Math.random() * 1000000000 + 1);
		String rawNonce = String.valueOf(valorRandomico);
		return rawNonce;
	}
	
	public String generarCadenaTrankey(String valorNonce, String valorSeed, String valorSecretKey) {
		String concatenacion = valorNonce + valorSeed + valorSecretKey;
		return concatenacion;
	}
	
	public String encriptarNonce(String valorRawNonce) {
		Base64 base64 = new Base64();
		String nonce = base64.encodeToString(String.valueOf(valorRawNonce).getBytes());
		return nonce;
	}
	
	public String encriptarTrankey(String concatenacion) {
		try {
			Base64 base64 = new Base64();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(concatenacion.getBytes("UTF-8"));
			String tranKey = base64.encodeToString(hash);
			return tranKey;
		} catch (Exception e) {
			Logger.getLogger(AutenticacionSesionPlacetopay.class.getName()).log(Level.SEVERE,
					"ERROR EN GENERACIÓN DE TRANKEY");
		}
		return new String();
	}
}
