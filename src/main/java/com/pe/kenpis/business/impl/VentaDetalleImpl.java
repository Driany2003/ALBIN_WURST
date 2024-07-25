package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaDetalleService;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleRequest;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import org.springframework.beans.BeanUtils;

public class VentaDetalleImpl implements IVentaDetalleService {

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
