package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.business.IVentaEstadoService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.api.venta.estado.VentasEstadoDTO;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WLoginController {

  @Autowired
  IUsuarioService service;

  @Autowired
  IEmpresaService empresaService;

  @Autowired
  IVentaEstadoService
      ventaEstadoService;

  @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
  public String login() {
    return "login";
  }

  @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = {"/kenpis/dashboard"}, method = RequestMethod.GET)
  public String welcome(ModelMap model, HttpServletRequest request) {

    String usuarioLogueado = FxComunes.getLoggedInUserName();
    UsuarioResponse usuarioResponse = service.findUsuarioByAuthUsername(usuarioLogueado);
    UsuarioAuthorityResponse usuarioAuthorityResponse = service.findUsuarioAuthorityByUsuId(usuarioResponse.getUsuId());
    String nombre = usuarioResponse.getUsuNombre() + " " + usuarioResponse.getUsuApePaterno() + " " + usuarioResponse.getUsuApeMaterno();
    model.put("usuSession", usuarioResponse);

    EmpresaResponse empresaResponse = empresaService.findById(usuarioResponse.getEmpresaId());
    log.info("Usuario logueado: {}", usuarioLogueado);
    log.info("Estado de la empresa (activo/inactivo): {}", empresaResponse.getEmpIsActive());

    if (!empresaResponse.getEmpIsActive()) {
      request.getSession().setAttribute("errorMessage", "La empresa a la que pertenece está inactiva");
      return "redirect:/logout";
    }
    request.getSession().setAttribute("usuSessionNivel", usuarioAuthorityResponse.getAuthRoles());
    request.getSession().setAttribute("usuSessionNombre", nombre);
    request.getSession().setAttribute("usuSessionId", usuarioResponse.getUsuId());
    request.getSession().setAttribute("usuarioSession", usuarioResponse);
    request.getSession().setAttribute("empresaSession", empresaResponse);
    request.getSession().setAttribute("empresaSessionID", empresaResponse.getEmpId());

    log.info("emprsa session : {}", empresaResponse.getEmpId());

    //MUESTRA LA CANTIDAD DE PEDIDOSESTADOS EN EL DASHBOARD
    Map<String, Object> pedidosEstado = ventaEstadoService.getCountPedidosXEstado();
    model.put("pedidosEstado", pedidosEstado);
    FxComunes.printJson("PedidosEstado", pedidosEstado);

    List<VentasEstadoDTO> registrado = ventaEstadoService.SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(Constantes.VENTA_ESTADO.REGISTRADO);
    List<VentasEstadoDTO> enProceso = ventaEstadoService.SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(Constantes.VENTA_ESTADO.EN_PROCESO);
    List<VentasEstadoDTO> pagado = ventaEstadoService.SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(Constantes.VENTA_ESTADO.PAGADO);
    List<VentasEstadoDTO> atendido = ventaEstadoService.SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(Constantes.VENTA_ESTADO.ATENDIDO);

    model.put(Constantes.VENTA_ESTADO.REGISTRADO, registrado);
    model.put(Constantes.VENTA_ESTADO.EN_PROCESO, enProceso);
    model.put(Constantes.VENTA_ESTADO.PAGADO, pagado);
    model.put(Constantes.VENTA_ESTADO.ATENDIDO, atendido);

    return "dashboard";
  }

  @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
  public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    String errorMessage = (String) request.getSession().getAttribute("errorMessage");
    if (errorMessage != null) {
      model.put("mensajeError", errorMessage);
      log.info("Mensaje de error: {}", errorMessage);
    }

    request.getSession().invalidate();
    return "login";
  }

}
