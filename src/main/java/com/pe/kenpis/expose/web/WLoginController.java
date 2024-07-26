package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.business.impl.VentaDetalleImpl;
import com.pe.kenpis.business.impl.VentaEstadoImpl;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.api.venta.detalle.VentaDetailDTO;
import com.pe.kenpis.model.api.venta.estado.del_dia.VentaEstadoDelDiaResponse;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import com.pe.kenpis.repository.VentaEstadoRepository;
import com.pe.kenpis.repository.VentaRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  private VentaEstadoImpl ventaEstado;


  @Autowired
  private VentaEstadoRepository ventaEstadoRepository;




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

    request.getSession().setAttribute("usuSessionNivel", usuarioAuthorityResponse.getAuthRoles());
    request.getSession().setAttribute("usuSessionNombre", nombre);
    request.getSession().setAttribute("usuSessionId", usuarioResponse.getUsuId());
    request.getSession().setAttribute("usuarioSession", usuarioResponse);
    request.getSession().setAttribute("empresaSession", empresaResponse);

    // LOGS CONSOLE
    FxComunes.printJson("UsuarioAuthorityResponse", usuarioAuthorityResponse);
    FxComunes.printJson("UsuarioResponse", usuarioResponse);
    FxComunes.printJson("EmpresaSession", empresaResponse);

    //MUESTRA LA CANTIDAD DE PEDIDOSESTADOS EN EL DASHBOARD
    Map<String, Object> pedidosEstado = ventaEstado.getCountPedidosXEstado();
    model.put("pedidosEstado", pedidosEstado);
    FxComunes.printJson("PedidosEstado", pedidosEstado);


    //LISTA PARA LOS DIV REGISTRADO, EN PROCESO, COMPLETADO, ATENDIDO
    List<Map<String, Object>> REGISTRADO = ventaEstadoRepository.findVentaEstadoEntityByVenEstado("REGISTRADO");
    List<Map<String, Object>> EN_PROCESO = ventaEstadoRepository.findVentaEstadoEntityByVenEstado("EN_PROCESO");
    List<Map<String, Object>> PAGADO = ventaEstadoRepository.findVentaEstadoEntityByVenEstado("PAGADO");
    List<Map<String, Object>> ATENDIDO = ventaEstadoRepository.findVentaEstadoEntityByVenEstado("ATENDIDO");

    model.addAttribute("Registrados", REGISTRADO);
    model.addAttribute("EnProcesos", EN_PROCESO);
    model.addAttribute("Pagados", PAGADO);
    model.addAttribute("Atendidos", ATENDIDO);

    FxComunes.printJson("Registrados", REGISTRADO);
    FxComunes.printJson("EnProceso", EN_PROCESO);
    FxComunes.printJson("Pagado", PAGADO);
    FxComunes.printJson("Atendidos", ATENDIDO);


    return "dashboard";
  }

  @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    return "redirect:/login";
  }

}
