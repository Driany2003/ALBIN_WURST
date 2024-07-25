package com.pe.kenpis.util.variables;

import com.pe.kenpis.util.funciones.UbicacionJar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Constantes {

  public static String NOMBRE_EMPRESA = "VENTAS";
  public static String USUARIO_SISTEMA = "USUARIO." + NOMBRE_EMPRESA;
  public static String LLAVE_ENCRIPTACION = "ENCRIPTACION." + NOMBRE_EMPRESA;
  public static String CLAVE_POR_DEFECTO = "Lima1234..";

  public String estado(Boolean estado) {
    if (estado) {
      return "Activo";
    } else {
      return "Inactivo";
    }
  }

  public interface RUTA_LOCAL {
    String BASE = UbicacionJar.rutaLocal();
  }

  public interface LOG {
    String ERROR = "ERROR";
    String GRABAR = "GRABAR";
    String ACTUALIZAR = "ACTUALIZAR";
    String ELIMINAR = "ELIMINAR";
  }

  public interface EXTENSION {
    String txt = "txt";
    String pdf = "pdf";
  }

  public interface NIVELES_USUARIO {
    String ADMIN = "ADMIN";
    String MORTAL = "MORTAL";
    String SUPERVISOR = "SUPERVISOR";
    String VENDEDOR = "VENDEDOR";
    String MARKETING = "MARKETING";
    String REGISTRADOR = "REGISTRADOR";
    String SOPORTE = "SOPORTE";
  }

  public interface VENTA_ESTADO {
    String REGISTRADO = "REGISTRADO";
    String PAGADO = "PAGADO";
    String EN_PROCESO = "EN_PROCESO";
    String ATENDIDO = "ATENDIDO";
    String DESCARTADO = "DESCARTADO";
  }

  public interface HORA_TRABAJO {
    String INGRESO = "09:00:00";
    String SALIDA = "18:00:00";
  }

}
