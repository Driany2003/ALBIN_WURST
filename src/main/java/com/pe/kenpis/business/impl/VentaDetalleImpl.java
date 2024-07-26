package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaDetalleService;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleRequest;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import com.pe.kenpis.repository.VentaDetalleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaDetalleImpl implements IVentaDetalleService {

  @Autowired
  private VentaDetalleRepository ventaDetalleRepository;


  private VentaDetalleResponse convertDetallesVentasEntityToResponse(VentaDetalleEntity in) {
    VentaDetalleResponse out = new VentaDetalleResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private VentaDetalleEntity convertDetallesVentasRequestToEntity(VentaDetalleRequest in) {
    VentaDetalleEntity out = new VentaDetalleEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
