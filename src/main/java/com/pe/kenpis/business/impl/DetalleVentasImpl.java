package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IDetalleVentasService;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaRequest;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaResponse;
import com.pe.kenpis.model.entity.DetallesVentasEntity;
import org.springframework.beans.BeanUtils;

public class DetalleVentasImpl implements IDetalleVentasService {

  private DetalleVentaResponse convertDetallesVentasEntityToResponse(DetallesVentasEntity in) {
    DetalleVentaResponse out = new DetalleVentaResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private DetallesVentasEntity convertDetallesVentasRequestToEntity(DetalleVentaRequest in) {
    DetallesVentasEntity out = new DetallesVentasEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
