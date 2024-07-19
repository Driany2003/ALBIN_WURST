package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentasService;
import com.pe.kenpis.model.api.ventas.VentasRequest;
import com.pe.kenpis.model.api.ventas.VentasResponse;
import com.pe.kenpis.model.entity.VentasEntity;
import org.springframework.beans.BeanUtils;

public class VentasImpl implements IVentasService {

  private VentasResponse convertVentasEntityToResponse(VentasEntity in) {
    VentasResponse out = new VentasResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private VentasEntity convertVentasRequestToEntity(VentasRequest in) {
    VentasEntity out = new VentasEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
