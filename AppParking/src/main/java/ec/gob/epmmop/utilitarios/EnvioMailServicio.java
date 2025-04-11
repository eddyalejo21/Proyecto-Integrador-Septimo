/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.epmmop.utilitarios;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author omoreno
 */
@Stateless
@LocalBean
public class EnvioMailServicio extends UtilitariosGenerales {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/envioMail", type = javax.mail.Session.class)
	private Session mailSession;

	public void enviarMail(String asunto, String destino, String cuerpo, String remitente) throws Exception {

		String cuerpoHTML = "<!DOCTYPE html>" + "<html lang=\"es\"> " + "<head> "
				+ "<link href=\'//fonts.googleapis.com/css?family=Open+Sans\' rel=\'stylesheet\' type=\'text/css\'>"
				+ "<meta charset=\"utf-8\">" + "</head>" + "<body> " + "<div class=\"panel-default\" >"
				+ " <div class=\"panel-body\" style=\"color:#244187;\"> "
				+ " <h3 style=\"color:#244187;\">Quito a tu Alcance</h3> " + "  <p> " + cuerpo + " </p>" + "</div>"
				+ "</div>" + "</body>" + "</html>";
		
		MimeMessage message = new MimeMessage(this.mailSession);
		message.setFrom(new InternetAddress(remitente));
		message.setSubject(asunto, "utf-8");
		message.setRecipients(Message.RecipientType.BCC, this.getInternetAddress(destino));
		message.setContent(cuerpoHTML,  "text/html; charset=utf-8");

		Transport.send(message);
	}

	private InternetAddress[] getInternetAddress(String destinatarios) throws AddressException, MessagingException {
		InternetAddress[] dirs = null;
		String[] pieces = destinatarios.split(";");
		List<String> list = Arrays.asList(pieces);
		dirs = new InternetAddress[list.size()];
		int i = 0;
		for (String m : list) {
			InternetAddress address = new InternetAddress(m);
			dirs[i++] = address;
		}
		return dirs;
	}

}
