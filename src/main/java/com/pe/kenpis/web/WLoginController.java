package com.pe.kenpis.web;

import com.pe.kenpis.business.IUsuariosService;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class WLoginController {

  @Autowired
  IUsuariosService service;

  @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
  public String login() {
    return "login";
  }

  @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = {"/sys/nueva"}, method = RequestMethod.GET)
  public String welcome(ModelMap model, HttpServletRequest request) {
    String usuarioLogueado = FxComunes.getLoggedInUserName();
    UsuarioResponse usuarioResponse = service.findUsuarioByAuthUsername(usuarioLogueado);
    UsuarioAuthorityResponse usuarioAuthorityResponse = service.findUsuarioAuthorityByUsuId(usuarioResponse.getUsuId());
    model.put("usuSession", usuarioResponse);

    request.getSession().setAttribute("usuSessionNivel", usuarioAuthorityResponse.getAuthRoles());
    request.getSession().setAttribute("usuSessionNombre", usuarioResponse.getUsuNombre());
    request.getSession().setAttribute("usuSessionId", usuarioResponse.getUsuId());

    // Ejemplo de depuración en el controlador
    System.out.println("Usuario Role: " + usuarioAuthorityResponse.getAuthRoles());
    System.out.println("Usuario NOMBRE : " + usuarioResponse.getUsuNombre());
    System.out.println("Usuario ID: " + usuarioResponse.getUsuId());

    return "nueva-venta";
  }

  @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    return "redirect:/login";
  }

}
