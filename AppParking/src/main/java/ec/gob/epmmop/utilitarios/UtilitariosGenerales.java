/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.epmmop.utilitarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.primefaces.model.UploadedFile;
//
//import ec.gob.epmmop.generales.modelo.ArchivoDigital;
//import ec.gob.epmmop.generales.servicios.ArchivoDigitalServicio;
//import ec.gob.epmmop.seguridades.modelo.SistemaDocumentosAlfresco;

/**
 * 
 * @author Omorenov
 */
public class UtilitariosGenerales extends Mensajes implements Serializable {
	
//	@EJB
//	private AdministrarArchivosServicio administrarArchivosServicio;
//	@EJB
//	private ArchivoDigitalServicio archivoDigitalServicio;

	protected static int calcularEdad(Date fechaNacimiento, Date fechaActual) {
		Calendar fechaAct = Calendar.getInstance();
		fechaAct.setTime(fechaActual);

		Calendar fechaNac = Calendar.getInstance();
		fechaNac.setTime(fechaNacimiento);

		int difAnios = fechaAct.get(Calendar.YEAR)
				- fechaNac.get(Calendar.YEAR);
		int difMeses = fechaAct.get(Calendar.MONTH)
				- fechaNac.get(Calendar.MONTH);
		int difDias = fechaAct.get(Calendar.DAY_OF_MONTH)
				- fechaNac.get(Calendar.DAY_OF_MONTH);

		if (difMeses < 0 || (difMeses == 0 && difDias < 0)) {
			difAnios--;
		}
		return difAnios;
	}

	protected static int fechasDiferenciaEnDias(Date fechaInicial,
			Date fechaFinal) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
			fechaInicial = df.parse(fechaInicioString);
		} catch (ParseException ex) {
		}

		String fechaFinalString = df.format(fechaFinal);
		try {
			fechaFinal = df.parse(fechaFinalString);
		} catch (ParseException ex) {
		}

		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias + 1);
	}

	protected static Date primerDiaDelMes() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.getActualMinimum(Calendar.DAY_OF_MONTH),
				cal.getMinimum(Calendar.HOUR_OF_DAY),
				cal.getMinimum(Calendar.MINUTE),
				cal.getMinimum(Calendar.SECOND));
		return cal.getTime();
	}

	protected static Date ultimoDiaDelMes() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.getActualMaximum(Calendar.DAY_OF_MONTH),
				cal.getMaximum(Calendar.HOUR_OF_DAY),
				cal.getMaximum(Calendar.MINUTE),
				cal.getMaximum(Calendar.SECOND));
		return cal.getTime();
	}

	protected static int validarCedulaValida(String p_cedula) {
		String cedula = p_cedula;
		int d10 = 0;
		int[] d;

		d = new int[10];
		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.parseInt(cedula.charAt(i) + "");
		}

		int imp = 0;
		int par = 0;

		for (int i = 0; i < d.length; i += 2) {
			d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
			imp += d[i];
		}

		for (int i = 1; i < (d.length - 1); i += 2) {
			par += d[i];
		}

		int suma = imp + par;
		d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0")
				- suma;
		d10 = (d10 == 10) ? 0 : d10;
		return d10;
	}

	public static String validarIdentificacion(String p_cedula, String p_tipo,
			int p_provincia) {
		String cedula = p_cedula;
		String respuesta = "N";

		int v_1;
		int v_2;
		int v_3;
		int v_4;
		int v_5;
		int v_6;
		int v_7;
		int v_8;
		int v_9;
		int v_10;
		int v_11;
		int v_12;
		int v_13;

		int total;
		int digito;

		if (p_tipo.equals("C")) {
			if (cedula.length() != 10) {
				respuesta = "N";
				return respuesta;
			}

			if (Integer.parseInt(cedula.substring(0, 2)) == 0) {
				respuesta = "N";
				return respuesta;
			}

			if (Integer.parseInt(cedula.substring(0, 2)) > 24) {
				respuesta = "N";
				return respuesta;
			}

			v_1 = Character.getNumericValue(cedula.charAt(0)) * 2;
			v_2 = Character.getNumericValue(cedula.charAt(1));
			v_3 = Character.getNumericValue(cedula.charAt(2)) * 2;
			v_4 = Character.getNumericValue(cedula.charAt(3));
			v_5 = Character.getNumericValue(cedula.charAt(4)) * 2;
			v_6 = Character.getNumericValue(cedula.charAt(5));
			v_7 = Character.getNumericValue(cedula.charAt(6)) * 2;
			v_8 = Character.getNumericValue(cedula.charAt(7));
			v_9 = Character.getNumericValue(cedula.charAt(8)) * 2;
			v_10 = Character.getNumericValue(cedula.charAt(9));

			if (v_1 >= 10) {
				v_1 = v_1 - 9;
			}
			if (v_3 >= 10) {
				v_3 = v_3 - 9;
			}
			if (v_5 >= 10) {
				v_5 = v_5 - 9;
			}
			if (v_7 >= 10) {
				v_7 = v_7 - 9;
			}
			if (v_9 >= 10) {
				v_9 = v_9 - 9;
			}

			total = v_1 + v_2 + v_3 + v_4 + v_5 + v_6 + v_7 + v_8 + v_9;
			digito = total % 10;

			if (digito > 0) {
				digito = 10 - digito;
			}

			if (digito == v_10) {
				respuesta = "S";
			} else {
				respuesta = "N";
			}

			if (total == 0) {
				respuesta = "N";
			}

			return respuesta;
		}

		if (p_tipo.equals("R")) {
			if (cedula.length() != 13) {
				respuesta = "N";
				return respuesta;
			}

			v_3 = Character.getNumericValue(cedula.charAt(2));

			if (v_3 == 9) { // Ruc cuando el tercer digito es 9

				v_1 = Character.getNumericValue(cedula.charAt(0)) * 4;
				v_2 = Character.getNumericValue(cedula.charAt(1)) * 3;
				v_3 = Character.getNumericValue(cedula.charAt(2)) * 2;
				v_4 = Character.getNumericValue(cedula.charAt(3)) * 7;
				v_5 = Character.getNumericValue(cedula.charAt(4)) * 6;
				v_6 = Character.getNumericValue(cedula.charAt(5)) * 5;
				v_7 = Character.getNumericValue(cedula.charAt(6)) * 4;
				v_8 = Character.getNumericValue(cedula.charAt(7)) * 3;
				v_9 = Character.getNumericValue(cedula.charAt(8)) * 2;
				v_10 = Character.getNumericValue(cedula.charAt(9));
				v_11 = Character.getNumericValue(cedula.charAt(10));
				v_12 = Character.getNumericValue(cedula.charAt(11));
				v_13 = Character.getNumericValue(cedula.charAt(12));

				total = v_1 + v_2 + v_3 + v_4 + v_5 + v_6 + v_7 + v_8 + v_9;
				digito = total % 11;

				if (digito > 0) {
					digito = 11 - digito;
				}

				if (digito == v_10) {
					respuesta = "S";
				} else {
					respuesta = "N";
				}

				if (v_13 == 0) {
					respuesta = "N";
				}

				return respuesta;
			}

			if (v_3 == 6) { // RUC cuando el tercer digito es 6
				v_1 = Character.getNumericValue(cedula.charAt(0)) * 3;
				v_2 = Character.getNumericValue(cedula.charAt(1)) * 2;
				v_3 = Character.getNumericValue(cedula.charAt(2)) * 7;
				v_4 = Character.getNumericValue(cedula.charAt(3)) * 6;
				v_5 = Character.getNumericValue(cedula.charAt(4)) * 5;
				v_6 = Character.getNumericValue(cedula.charAt(5)) * 4;
				v_7 = Character.getNumericValue(cedula.charAt(6)) * 3;
				v_8 = Character.getNumericValue(cedula.charAt(7)) * 2;
				v_9 = Character.getNumericValue(cedula.charAt(8));
				v_10 = Character.getNumericValue(cedula.charAt(9));
				v_11 = Character.getNumericValue(cedula.charAt(10));
				v_12 = Character.getNumericValue(cedula.charAt(11));
				v_13 = Character.getNumericValue(cedula.charAt(12));

				total = v_1 + v_2 + v_3 + v_4 + v_5 + v_6 + v_7 + v_8;
				digito = total % 11;

				if (digito > 0) {
					digito = 11 - digito;
				}

				if (digito == v_9) {
					respuesta = "S";
				} else {
					respuesta = "N";
				}

				if (v_13 == 0) {
					respuesta = "N";
				}

				return respuesta;
			} else {
				v_1 = Character.getNumericValue(cedula.charAt(0)) * 2;
				v_2 = Character.getNumericValue(cedula.charAt(1));
				v_3 = Character.getNumericValue(cedula.charAt(2)) * 2;
				v_4 = Character.getNumericValue(cedula.charAt(3));
				v_5 = Character.getNumericValue(cedula.charAt(4)) * 2;
				v_6 = Character.getNumericValue(cedula.charAt(5));
				v_7 = Character.getNumericValue(cedula.charAt(6)) * 2;
				v_8 = Character.getNumericValue(cedula.charAt(7));
				v_9 = Character.getNumericValue(cedula.charAt(8)) * 2;
				v_10 = Character.getNumericValue(cedula.charAt(9));
				v_11 = Character.getNumericValue(cedula.charAt(10));
				v_12 = Character.getNumericValue(cedula.charAt(11));
				v_13 = Character.getNumericValue(cedula.charAt(12));

				if (v_1 >= 10) {
					v_1 = v_1 - 9;
				}
				if (v_3 >= 10) {
					v_3 = v_3 - 9;
				}
				if (v_5 >= 10) {
					v_5 = v_5 - 9;
				}
				if (v_7 >= 10) {
					v_7 = v_7 - 9;
				}
				if (v_9 >= 10) {
					v_9 = v_9 - 9;
				}

				total = v_1 + v_2 + v_3 + v_4 + v_5 + v_6 + v_7 + v_8 + v_9;
				digito = total % 10;

				if (digito > 0) {
					digito = 10 - digito;
				}

				if (digito == v_10 && v_13 == 1 && v_12 == 0 && v_11 == 0) {
					respuesta = "S";
				} else {
					respuesta = "N";
				}

				return respuesta;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param idPuesto
	 * @param tipo
	 * @param path
	 * @param idAspirante
	 * @return
	 */
	public static String renombrarArchivo(String tipo, String path,
			Long idAspirante, String cedula) {

		String name = path.split("\\.")[0];
		String pathFinal;

		pathFinal = tipo + name + idAspirante + "_" + cedula + "."
				+ retornarTipoDeArchivo(path);

		return pathFinal;
	}
	
	public static String renombrarArchivoAtenCiu(String tipo, String path,
			Long idPedido, String cedPedido) {

		String name = path.split("\\.")[0];
		String pathFinal;

		pathFinal = tipo + name + idPedido + cedPedido + "."
				+ retornarTipoDeArchivo(path);

		return pathFinal;
	}
	
	public static String renombrarArchivoAtenCiuW(String tipo, String path,
			String cedPedido) {

		String name = path.split("\\.")[0];
		String pathFinal;

		pathFinal = tipo + name + cedPedido + "."
				+ retornarTipoDeArchivo(path);

		return pathFinal;
	}

	/**
	 * 
	 * @param pathArchivo
	 * @return
	 */
	protected static String retornarTipoDeArchivo(String pathArchivo) {
		String tipo = "";
		try {

			String[] extencion = pathArchivo.split("\\.");

			tipo = extencion[extencion.length - 1];

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tipo;
	}

	/**
	 * Copia el contenido de un objeto file a otro renombrado
	 * 
	 * @param fuente
	 * @param destino
	 * @throws IOException
	 *             +
	 */
	protected static void copyFile(File fuente, File destino)
			throws IOException {
		InputStream in = new FileInputStream(fuente);
		OutputStream out = new FileOutputStream(destino);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public static String obtenerMimeType(String fileName) {

		String extencion = retornarTipoDeArchivo(fileName);

		if (extencion.trim().equalsIgnoreCase("pdf")) {
			return ("application/pdf");
		} else if (extencion.trim().equalsIgnoreCase("zip")) {
			return ("application/zip");
		} else if (extencion.trim().equalsIgnoreCase("rar")) {
			return ("application/rar");
		} else if (extencion.trim().equalsIgnoreCase("txt")) {
			return ("text/plain");
		} else if (extencion.trim().equalsIgnoreCase("csv")) {
			return ("text/plain");
		} else if (extencion.trim().equalsIgnoreCase("doc")) {
			return ("application/msword");
		} else if (extencion.trim().equalsIgnoreCase("docx")) {
			return ("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		} else if (extencion.trim().equalsIgnoreCase("odt")) {
			return ("application/odt");
		} else if (extencion.trim().equalsIgnoreCase("jpg")
				|| extencion.trim().equalsIgnoreCase("jpeg")) {
			return ("image/JPEG");
		} else if (extencion.trim().equalsIgnoreCase("png")) {
			return ("image/PNG");
		} else if (extencion.trim().equalsIgnoreCase("gif")) {
			return ("image/gif");
		} else if (extencion.trim().equalsIgnoreCase("wmv")) {
			return ("video/x-ms-wmv");
		} else if (extencion.trim().equalsIgnoreCase("avi")) {
			return ("video/avi");
		} else if (extencion.trim().equalsIgnoreCase("mp3")
				|| extencion.trim().equalsIgnoreCase("mp4")) {
			return ("video/mpeg");
		} else if (extencion.trim().equalsIgnoreCase("xls")) {
			return ("application/vnd.ms-excel");
		} else if (extencion.trim().equalsIgnoreCase("xml")) {
			return ("text/xml");
		} else if (extencion.trim().equalsIgnoreCase("xlsx")) {
			return ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} else {
			return null;
		}
	}

	public static byte[] convertirFileAArray(File archivo) throws IOException {
		byte[] archivoBytes = null;
		long tamanoArch = archivo.length();
		archivoBytes = new byte[(int) tamanoArch]; // Le damos al array el
													// tamaño del archivo.
		try {
			// Nos creamos esta variable para poder leer el archivo.
			FileInputStream docu = new FileInputStream(archivo);

			// Leemos los bytes del archivo y a la vez se van insertando en el
			// array de bytes creado.
			int numBytes = docu.read(archivoBytes);
			docu.close(); // Muy importante cerrar tras la lectura.
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return archivoBytes;
	}

	protected static void verArchivos(byte[] bytes, String fileName) {
		ServletOutputStream ouputStream = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.addHeader("Content-disposition", "attachment; filename="
					+ "" + fileName + "");
			response.setContentType(UtilitariosGenerales
					.obtenerMimeType(fileName));
			response.setContentLength(bytes.length);
			ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
			context.responseComplete();

		} catch (IOException ex) {
			Logger.getLogger(UtilitariosGenerales.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			try {
				ouputStream.close();
			} catch (IOException ex) {
				Logger.getLogger(UtilitariosGenerales.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	// <editor-fold defaultstate="collapsed" desc=" Public Methods">
	protected static String ssuDesencriptaClave(String strClave) {
		String strResult = "";
		String strSemillaIni = "$%&()#$!%($%&()#$!%($%&()#$!%(";
		Integer intLongitudClave = strClave.trim().length();
		// System.out.println(strSemillaClave + " ****** datos " +
		// intLongitudClave);
		for (int i = 0; i < intLongitudClave; i++) {
			strResult = strResult
					+ (new Character((char) (strClave.substring(i, i + 1)
							.charAt(0)
							- strSemillaIni.substring(i, i + 1).charAt(0)
							+ intLongitudClave + 15)).toString());
		}
		// System.out.print("****************> retorna " + strResult);
		return strResult;
	}

	protected static String ssuEncriptaClave(String strClave) {
		String strResult = "";
		String strSemillaIni = "$%&()#$!%($%&()#$!%($%&()#$!%(";
		Integer intLongitudClave = strClave.trim().length();
		// System.out.println(strSemillaClave + " ****** datos " +
		// intLongitudClave);
		for (int i = 0; i < intLongitudClave; i++) {
			strResult = strResult
					+ (new Character((char) (strClave.substring(i, i + 1)
							.charAt(0)
							+ strSemillaIni.substring(i, i + 1).charAt(0)
							- intLongitudClave - 15)).toString());
		}
		// System.out.print("****************> retorna " + strResult);
		return strResult;
	}

	// </editor-fold>

	protected void closeSession(String p_nuevaPagina) {
		try {
			getExternalContext().redirect(
					getRequest().getContextPath() + p_nuevaPagina);
			HttpSession session = getSession();
			session.invalidate();
		} catch (Exception e) {
			error(getClass().getName(), "no se puede cerrar la sesión", e);
		}

	}

	protected FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ExternalContext getExternalContext() {
		return getContext().getExternalContext();
	}

	protected void error(String clase, String mensaje, Exception e) {
		Logger.getLogger(clase).log(Level.SEVERE, mensaje.toUpperCase(), e);
	}

	protected Application getApplication() {
		return getContext().getApplication();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getContext().getExternalContext()
				.getResponse();

	}

	protected static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
	}

	protected void ponerMensajeInfo(String mensajeAdicional) {
		getContext().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void ponerMensajeError(String mensajeAdicional) {
		getContext().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void ponerMensajeAlerta(String mensajeAdicional) {
		getContext().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void ponerMensajeInfo(String idMensaje, String mensajeAdicional) {
		getContext().addMessage(
				idMensaje,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void ponerMensajeError(String idMensaje, String mensajeAdicional) {
		getContext().addMessage(
				idMensaje,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void ponerMensajeAlerta(String idMensaje, String mensajeAdicional) {
		getContext().addMessage(
				idMensaje,
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeAdicional,
						""));// new
								// FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)
								// ));
	}

	protected void redirect(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	protected Object obtenerObjetoSesion(String p_atributo) {
		Object control = (Object) getRequest().getSession().getAttribute(
				p_atributo);
		return control;

	}

	protected void ponerObjetoAtributoEnSesion(String p_nombre, Object valor) {
		getRequest().getSession().setAttribute(p_nombre, valor);
	}

	protected Object obtenerAtributoSesion(String p_atributo) {
		Object control = (Object) getRequest().getSession().getAttribute(
				p_atributo);
		getRequest().getSession().removeAttribute(p_atributo);
		return control;
	}

	protected static void ordenarLista(List lista, final String propiedad) {
		Collections.sort(lista, new Comparator() {
			public int compare(Object obj1, Object obj2) {

				Class clase = obj1.getClass();
				String getter = "get"
						+ Character.toUpperCase(propiedad.charAt(0))
						+ propiedad.substring(1);
				try {

					Method getPropiedad = clase.getMethod(getter);

					Object propiedad1 = getPropiedad.invoke(obj1);
					Object propiedad2 = getPropiedad.invoke(obj2);

					if (propiedad1 instanceof Comparable
							&& propiedad2 instanceof Comparable) {
						Comparable prop1 = (Comparable) propiedad1;
						Comparable prop2 = (Comparable) propiedad2;
						return prop1.compareTo(prop2);
					}// CASO DE QUE NO SEA COMPARABLE
					else {
						if (propiedad1.equals(propiedad2)) {
							return 0;
						} else {
							return 1;
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	protected static void ordenarListaDesendente(List lista,
			final String propiedad) {

		Collections.sort(lista, new Comparator() {
			public int compare(Object obj1, Object obj2) {

				Class clase = obj1.getClass();
				String getter = "get"
						+ Character.toUpperCase(propiedad.charAt(0))
						+ propiedad.substring(1);
				try {

					Method getPropiedad = clase.getMethod(getter);

					Object propiedad1 = getPropiedad.invoke(obj1);
					Object propiedad2 = getPropiedad.invoke(obj2);

					if (propiedad1 instanceof Comparable
							&& propiedad2 instanceof Comparable) {
						Comparable prop1 = (Comparable) propiedad1;
						Comparable prop2 = (Comparable) propiedad2;
						return prop2.compareTo(prop1);
					}// CASO DE QUE NO SEA COMPARABLE
					else {
						if (propiedad2.equals(propiedad1)) {
							return 0;
						} else {
							return 1;
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	protected MethodExpression crearActionFormularios(String action) {
		return getApplication().getExpressionFactory().createMethodExpression(
				getElContext(), action, null, new Class<?>[0]);
	}

	protected ELContext getElContext() {
		return getContext().getELContext();
	}

	protected static String gguSubCadena(String strCadena, int intPosIni,
			int intPosFin) {
		String strReturn = null;

		if (strCadena == null) {
			strReturn = null;
		} else {
			if (strCadena.length() > intPosFin) {
				strReturn = strCadena.substring(intPosIni - 1, (intPosFin));
			}
		}
		return strReturn;
	}

	protected static String removerCaracteresEspeciales(String input) {
		// Descomposición canónica
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		// Nos quedamos únicamente con los caracteres ASCII
		Pattern pattern = Pattern.compile("\\P{ASCII}");
		return pattern.matcher(normalized).replaceAll("");
	}// re

//	protected static String removerTildes(String input) {
//		String sinTilde = input.replace('á', 'a');
//		sinTilde = sinTilde.replace('é', 'e');
//		sinTilde = sinTilde.replace('í', 'i');
//		sinTilde = sinTilde.replace('ó', 'o');
//		sinTilde = sinTilde.replace('ú', 'u');
//		sinTilde = sinTilde.replace('Í', 'A');
//		sinTilde = sinTilde.replace('É', 'E');
//		sinTilde = sinTilde.replace('Í', 'I');
//		sinTilde = sinTilde.replace('Ó', 'O');
//		sinTilde = sinTilde.replace('Ú', 'U');
//		return sinTilde;
//	}

	/**
	 * Retorna true si es correcto el numero de cédula
	 * 
	 * @param numeroCedula
	 * @return
	 */
	protected static boolean cedulaValida(String numeroCedula) {

		if (numeroCedula.trim().length() < 10) {
			return false;
		}
		if (numeroCedula.trim().length() > 10
				&& numeroCedula.trim().length() < 13) {
			return false;
		}

		Integer val = validarCedulaValida(numeroCedula);
		Integer ultimoDigitoCedulaIngresada = Integer.parseInt(numeroCedula
				.substring(numeroCedula.length() - 1));

		if (!val.equals(ultimoDigitoCedulaIngresada)) {
			return false;
		}
		return true;
	}

	protected static String devuelveDiaSemana(int dia) {
		switch (dia) {
		case 1:
			return "Domingo ";
		case 2:
			return "Lunes ";
		case 3:
			return "Martes ";
		case 4:
			return "Miércoles ";
		case 5:
			return "Jueves ";
		case 6:
			return "Viernes ";
		case 7:
			return "Sabado ";
		default:
			return "";

		}
	}

	protected static String getRealPath() {
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		return (String) servletContext.getRealPath("/");
	}

	public static byte[] convertInputStreamToByteArray(InputStream is)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();

		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}

		return baos.toByteArray();

	}

	protected static boolean comprobarMayorEdad(Date fechaNacimiento) {
		Calendar cal = Calendar.getInstance();
		int currYear = cal.get(Calendar.YEAR);
		int currMonth = cal.get(Calendar.MONTH);
		int currDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(fechaNacimiento);
		int years = currYear - cal.get(Calendar.YEAR);
		int bornMonth = cal.get(Calendar.MONTH);

		int aniosNac;

		if (bornMonth == currMonth) {
			aniosNac = cal.get(Calendar.DAY_OF_MONTH) <= currDay ? years
					: years - 1;
		} else {
			aniosNac = bornMonth < currMonth ? years : years - 1;
		}

		if (aniosNac >= 18)
			return true;

		return false;
	}

	public static Date parseDate(String date, String format)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(date);
	}

	public static String parseDateToString(Date date, String format)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		String stringDate = df.format(date);
		// System.out.println("**************"+format+" --> "+stringDate);
		return stringDate;
	}

	public static String gguSubCadenaDato(String strCadena, String strCaracter) {
		String strReturn = null;
		String strCharTexto = null;
		Integer intPos = 0;
		Integer intI = 0;

		while (intPos <= 0) {
			strCharTexto = strCadena.substring(intI, intI + 1);
			// System.out.println(intI+" caracter analizado " + strCharTexto);
			if (strCharTexto.equalsIgnoreCase(strCaracter.trim())) {
				intPos = intI;
				if (intPos == 0) {
					// System.out.println(" retorna nulo " );
					return strReturn;
				}
			} else {
				intI++;
			}
			if (intI > strCadena.length()) {
				intPos = 1;
			}
		}
		strReturn = strCadena.substring(0, intPos);
		return strReturn;
	}

	public static String encuentraPrimerasPalabra(String strCadena, Integer i) {
		System.out.println(strCadena + " tamaño cadena " + strCadena.length());
		StringTokenizer st = new StringTokenizer(strCadena, " ", true);
		String palabra = "";
		for (int f = 1; f <= i; f++) {
			palabra = st.nextToken();
			System.out.println(palabra);
			System.out.println(i + " token " + st.nextToken());
		}

		return palabra;
	}

	public String quitaEspacios(String texto) {
		StringTokenizer tokens = new StringTokenizer(texto);
		StringBuilder buff = new StringBuilder();
		while (tokens.hasMoreTokens()) {
			buff.append("").append(tokens.nextToken());
		}
		System.out
				.println(texto + "**quitaEspacios**" + buff.toString().trim());
		return buff.toString().trim();
	}

	public static String gguSubCadenaMenos(String strCadena, String strCaracter) {
		String strReturn = null;
		String strCharTexto = null;
		Integer intPos = 0;
		Integer intI = 0;
		while (intPos <= 0) {
			strCharTexto = strCadena.substring(intI, intI + 1);
			if (strCharTexto.equalsIgnoreCase(strCaracter.trim())) {
				intPos = intI;
			} else {
				intI++;
			}
		}
		strReturn = strCadena.substring(intPos + 1, strCadena.length());
		return strReturn;
	}

	/**
	 * Devuelve el anio de la fecha
	 * 
	 * @param fecha
	 * @return
	 */
	public final int obtenerAnio(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * Devuelve una fecha a partir de la fecha en texto y su formato
	 * 
	 * @param format
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public Date stringToDate(String format, String dateString)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = sdf.parse(dateString);
		return d;
	}

	/**
	 * Devuelve el último día del año enviado como parámetro.
	 * 
	 * @param year
	 * @return
	 */
	public Date getLastDayOfYear() {
		Date currentDate = new Date();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, obtenerAnio(currentDate));
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		// returning the last date
		Date endDate = calendarEnd.getTime();
		return endDate;
	}

	/**
	 * Devuelve el primer día del año en curso
	 * 
	 * @return
	 */
	public Date getFirstDayOfYear() {
		Date currentDate = new Date();
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.YEAR, obtenerAnio(currentDate));
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		// returning the first date
		Date startDate = calendarStart.getTime();
		return startDate;
	}

	/**
	 * Suma o resta años a la fecha que se pase como parámetro
	 * 
	 * @param fecha
	 * @param anios
	 *            a sumar o restar
	 * @return la nueva fecha
	 */
	public static Date sumarRestarAnios(Date fecha, int anios) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.YEAR, anios);
		return calendar.getTime();
	}

	/**
	 * Obtiene el número de días de diferencia entre dos fechas
	 * 
	 * @param fechaFin
	 * @param FechaInicio
	 * @return
	 */
	public static long diferenciaEntreFechas(Date fechaFin, Date FechaInicio) {
		long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
		long diferencia = (fechaFin.getTime() - FechaInicio.getTime())
				/ MILLSECS_PER_DAY;
		return diferencia;

	}

	/**
	 * Compara si 'fecha' está entre la fecha de inicio y la fecha fin.
	 * 
	 * @param fecha
	 * @param fechaFin
	 * @param FechaInicio
	 * @return
	 */
//	public static boolean isBetweenDates(Date fecha, Date FechaInicio, Date fechaFin ) {
//		boolean isBetween=false;
//		if((fecha.after(FechaInicio) | fecha.equals(FechaInicio)) & (fecha.before(fechaFin) | fecha.equals(fechaFin))){ 
//			isBetween=true;
//		}
//		
//		return isBetween;
//
//	}
	
	/**
	 * Suma o resta días a la fecha dada
	 * @param fecha
	 * @param anios
	 * @return
	 */
//	public static Date sumarRestarDias(Date fecha, int dias) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(fecha);
//		calendar.add(Calendar.DAY_OF_YEAR, dias);
//		return calendar.getTime();
//	}
	
//	public Boolean registrarArchivosDigitales(UploadedFile p_archivo,
//			ArchivoDigital p_archiDig,
//			SistemaDocumentosAlfresco p_conexionAlfresco) {
//		Boolean resul = Boolean.TRUE;
//		try {
//			System.out.println(" VOY A INSERTAR EN ALFRESCO");
//			ByteArrayInputStream arch = new ByteArrayInputStream(
//					p_archivo.getContents());
//			administrarArchivosServicio.subirArchivoFtpAlfresco(arch,
//					p_conexionAlfresco, p_archiDig.getDiaIdalfresco());
//		} catch (Exception e) {
//			Logger.getLogger(UtilitariosGenerales.class.getName()).log(
//					Level.SEVERE, null, e);
//			resul = Boolean.FALSE;
//		}
//		if (resul) {
//			try {
//				System.out.println(" VOY A INSERTAR en archivos digitales");
//				archivoDigitalServicio.insertarRegistro(p_archiDig);
//			} catch (Exception e) {
//				Logger.getLogger(UtilitariosGenerales.class.getName()).log(
//						Level.SEVERE, null, e);
//				resul = Boolean.FALSE;
//			}
//
//		}
//		return resul;
//	}
	
//	public Boolean registrarArchivosDigitales(byte[] bytes,
//			ArchivoDigital p_archiDig,
//			SistemaDocumentosAlfresco p_conexionAlfresco) {
//		Boolean resul = Boolean.TRUE;
//		try {
//			System.out.println(" VOY A INSERTAR EN ALFRESCO");
//			ByteArrayInputStream arch = new ByteArrayInputStream(
//					bytes);
//			administrarArchivosServicio.subirArchivoFtpAlfresco(arch,
//					p_conexionAlfresco, p_archiDig.getDiaIdalfresco());
//		} catch (Exception e) {
//			Logger.getLogger(UtilitariosGenerales.class.getName()).log(
//					Level.SEVERE, null, e);
//			resul = Boolean.FALSE;
//		}
//		if (resul) {
//			try {
//				System.out.println(" VOY A INSERTAR en archivos digitales");
//				archivoDigitalServicio.insertarRegistro(p_archiDig);
//			} catch (Exception e) {
//				Logger.getLogger(UtilitariosGenerales.class.getName()).log(
//						Level.SEVERE, null, e);
//				resul = Boolean.FALSE;
//			}
//
//		}
//		return resul;
//	}
}
