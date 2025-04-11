package ec.gob.epmmop.appparking.dto;

public class RespuestaVerificacion {

	private Boolean status;
	private String message;
	private String reference;
	private String urlProcess;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getUrlProcess() {
		return urlProcess;
	}
	public void setUrlProcess(String urlProcess) {
		this.urlProcess = urlProcess;
	}
	
	
}
