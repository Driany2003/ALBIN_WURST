package com.pe.kenpis.model.api.empresa.caja;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCajaDTO {

  private List<EmpresaDTO> sucursales;
  private List<ResponsablesDTO> responsables;


}
