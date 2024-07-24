package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaDetalleService;
import com.pe.kenpis.model.api.venta.detalle.DetalleVentaRequest;
import com.pe.kenpis.model.api.venta.detalle.DetalleVentaResponse;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import org.springframework.beans.BeanUtils;

public class VentaVentaDetalleImpl implements IVentaDetalleService {

  private DetalleVentaResponse convertDetallesVentasEntityToResponse(VentaDetalleEntity in) {
    DetalleVentaResponse out = new DetalleVentaResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private VentaDetalleEntity convertDetallesVentasRequestToEntity(DetalleVentaRequest in) {
    VentaDetalleEntity out = new VentaDetalleEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
