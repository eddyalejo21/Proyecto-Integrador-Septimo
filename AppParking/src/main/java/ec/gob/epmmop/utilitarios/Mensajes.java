/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.epmmop.utilitarios;

/**
 *
 * @author flozano
 */
public class Mensajes {
    
    /**
     * Mensajes de Crud
     */
     protected static final String MENSAJEGUARDADO = "Registro Guardado Correctamente";
     protected static final String MENSAJEMODIFICADO = "Registro Modificado Correctamente";
     protected static final String MENSAJEELIMINADO = "Registro Eliminado Correctamente";
     protected static final String MENSAJEERRORGUARDADO = "Ocurrió un error al guardar";
     protected static final String MENSAJEERRORMODIFICADO = "Ocurrió un error al modificar";
     protected static final String MENSAJEERRORELIMINAR = "Ocurrió un error al eliminar";
     protected static final String MENSAJEERRORCONSULTAR = "Ocurrió un error al consultar el registro";
     
     
     /**
      * Mensajes de Logeo
      */
     protected static final String MENSAJEINGRESOCORRECTO = "Bienvenido al Sistema";
     protected static final String MENSAJEINGRESOINCORRECTO = "Usuario o Clave Incorrecto";
     protected static final String MENSAJEERRORLOGUEO = "Ocurrió un error al loguearse";
     protected static final String MENSAJEERRORDIRECCIONAMIENTO = "Ocurrió un error al redireccionar la aplicación";
     protected static final String MENSAJESINDIRECCIONAMIENTO = "El sistema no tiene una URL para direccionar";
     protected static final String MENSAJESINGRESOINCORRECTO = "Usted no ha ingresado correctamente al sistema";
     
     
     /**
      * Mensajes de validaciones
      */
     protected static final String MENSAJEERRORFECHAINICIAL="La fecha inicial no puede ser mayor a la fecha final";
     protected static final String MENSAJEERRORCORREOELECTRONICO="El correo electrónico no es válido";
     protected static final String MENSAJEERRORCEDULA="La cédula ingresada es incorrecta";
     
     /**
      * Mensajes de error de listado
      */
     
     protected static final String MENSAJEERRORLISTARGENERAL="Ocurrió un error al listar el componente";
     
     
     /**
      * Mensajes del Sistema de Control de Obras
      */
     
     protected static final String MENSAJE_LATITUDLONGITUD_REQUERIDA="Por favor ingrese su geolocalización";
     protected static final String MENSAJE_LOCALIZACION_REQUERIDA="Por favor ingrese la localización de la obra";
     
     /**
      * Mensajes de error de envio de cooreo
      */
     protected static final String MENSAJE_ERROR_ENVIO_MAIL="Ocurrió un error al enviar el correo";
     
}
