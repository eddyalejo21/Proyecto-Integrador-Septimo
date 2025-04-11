package ec.gob.epmmop.utilitarios;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.core.Response.ResponseBuilder;

import ec.gob.epmmop.appparking.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class Utils {

	public static final String KEYTK = "ep#m0p";
	public static final Long ExpMilis = 9650000000L;

	public String generarToken(Usuario usr)
			throws UnsupportedEncodingException {
		
		Date date = new Date();
		Date exp = new Date();
		exp.setTime(new Date().getTime() + ExpMilis);
		
		String jwt = Jwts
				.builder()
				.signWith(SignatureAlgorithm.HS256, KEYTK.getBytes("UTF-8"))
				.setSubject(usr.getUsuIdentificacion())
				.claim("mail", usr.getUsuCorreo())
				.setIssuedAt(date)
				.setExpiration(exp).compact();
		
		return jwt;
	}
	
	public Jws<Claims> validarToken(String token)
			throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException,
			IllegalArgumentException, UnsupportedEncodingException {
		
		Jws<Claims> claims = Jwts.parser()
				.setSigningKey(KEYTK.getBytes("UTF-8")).parseClaimsJws(token);

		return claims;
	}

	public static ResponseBuilder getHeaders(ResponseBuilder rb) {
		return rb.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Headers", "Content-Type")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, POST, DELETE, OPTIONS")
				.header("Access-Control-Max-Age", "86400");
	}

	public static String parseDateToString(Date date, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		String stringDate = df.format(date);

		return stringDate;
	}
	
	public static String cadenaAleatoria() {
	    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    String cadena = "";
	    for (int x = 0; x < 8; x++) {
	        int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
	        char caracterAleatorio = banco.charAt(indiceAleatorio);
	        cadena += caracterAleatorio;
	    }
	    return cadena;
	}
	
	public static int numeroAleatorioEnRango(int minimo, int maximo) {
	    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
	}
	
	public Integer numerosAleatorios() {
		Random random = new Random();
		Integer numero = random.nextInt(90000) + 10000;
		return numero;
	}

}
