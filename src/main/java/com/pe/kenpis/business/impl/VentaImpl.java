package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaCajaService;
import com.pe.kenpis.business.IProductoInventarioService;
import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleRequest;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.api.venta.reporteVentasDTO.ReporteVentas;
import com.pe.kenpis.model.entity.*;
import com.pe.kenpis.repository.*;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaImpl implements IVentaService {

  private final IProductoInventarioService productoInventario;
  private final VentaRepository ventaRepository;
  private final ProductoRepository productoRepository;
  private final VentaDetalleRepository detalleVentaRepository;
  private final VentaEstadoRepository ventaEstadoRepository;
  private final EmpresaCajaRepository cajaRepository;

  @Override
  public VentaResponse findById(Integer venId) {
    log.debug("Implements :: findById :: Inicio");
    Optional<VentaEntity> optionalVenta = ventaRepository.findById(venId);
    return optionalVenta.map(this::convertVentasEntityToResponse).orElse(null);
  }

  @Override
  public List<VentaResponse> findAll() {
    log.debug("Implements :: findAll");
    List<VentaEntity> ventasList = ventaRepository.findAll();
    return ventasList.stream().map(this::convertVentasEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public VentaResponse create(VentaRequest ventaRequest) {
    FxComunes.printJson("VentaRequest", ventaRequest);

    for (VentaDetalleRequest detalle : ventaRequest.getDetallesVentas()) {
      boolean stockSuficiente = productoInventario.verificarStockSuficiente(detalle.getProductoId(), detalle.getVenDetCantidad());

      if (!stockSuficiente) {
        throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + detalle.getProductoId() + ". Cantidad solicitada: " + detalle.getVenDetCantidad());
      }
    }

    // Convertir y guardar la venta
    VentaEntity nuevaVenta = convertVentasRequestToEntity(ventaRequest);
    nuevaVenta.setVenFecha(new Date());
    nuevaVenta.setCajaId(ventaRequest.getCajaId());

    VentaEntity ventaGuardada = ventaRepository.save(nuevaVenta);
    FxComunes.printJson("VentaEntity", ventaGuardada);

    // Crear y guardar los detalles de la venta
    List<VentaDetalleEntity> detallesVentas = new ArrayList<>();
    for (VentaDetalleRequest detalle : ventaRequest.getDetallesVentas()) {
      VentaDetalleEntity nuevoDetalle = new VentaDetalleEntity();
      nuevoDetalle.setVenDetObservaciones(detalle.getVenDetObservaciones());
      nuevoDetalle.setVentaId(ventaGuardada.getVenId());
      nuevoDetalle.setProductoId(detalle.getProductoId());
      nuevoDetalle.setVenDetCantidad(detalle.getVenDetCantidad());
      nuevoDetalle.setVenDetPrecio(detalle.getVenDetPrecio());
      nuevoDetalle.setVenDetSubtotal((float) detalle.getVenDetSubtotal());
      detallesVentas.add(nuevoDetalle);

      // Actualizar el stock
      productoInventario.actualizarStock(detalle.getProductoId(), detalle.getVenDetCantidad());
    }
    detalleVentaRepository.saveAll(detallesVentas);

    FxComunes.printJson("VentaDetalleEntity", detallesVentas);

    // Crear y guardar el estado inicial de la venta
    VentaEstadoEntity estadoInicial = new VentaEstadoEntity();
    estadoInicial.setVentaId(ventaGuardada.getVenId());
    estadoInicial.setVenEstado(Constantes.VENTA_ESTADO.REGISTRADO);
    estadoInicial.setVenEstadoFechaRegistrado(new Date());
    ventaEstadoRepository.save(estadoInicial);

    // Convertir y devolver la respuesta de la venta
    VentaResponse response = convertVentasEntityToResponse(ventaGuardada);
    return response;
  }

  @Override
  public List<VentaDetalleResponse> obtenerDetallesDeVenta() {
    List<VentaDetalleEntity> detalles = detalleVentaRepository.findAll();
    return detalles.stream().map(detalle -> {
      ProductoEntity producto = productoRepository.findById(detalle.getProductoId()).orElse(null);
      ProductoResponse productoResponse = new ProductoResponse();
      if (producto != null) {
        BeanUtils.copyProperties(producto, productoResponse);
      }
      return new VentaDetalleResponse(productoResponse, detalle.getVenDetId(), detalle.getVenDetCantidad(), detalle.getVenDetSubtotal(), detalle.getVentaId());
    }).collect(Collectors.toList());
  }

  // reporte dinamico del dashboard //

  @Override
  public ReporteVentas obtenerReporteVentas(Integer empresaId, Integer cajaId) {
    List<Map<String, Object>> reporte = ventaRepository.obtenerReporteVentasPorCaja(empresaId, cajaId);

    if (reporte.isEmpty()) {
      return new ReporteVentas(0.0, 0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0, Collections.emptyList());
    }

    Map<String, Object> resumen = reporte.get(0);

    Double totalVenta = (Double) resumen.get("precioVentaTotal");
    Double totalCosto = (Double) resumen.get("precioCostoTotal");
    Double gananciaTotal = (Double) resumen.get("ganancia");

    Integer numeroVentas = ((Number) resumen.get("totalVentas")).intValue();
    Double totalYape = (Double) resumen.get("totalYape");
    Double totalPlin = (Double) resumen.get("totalPlin");
    Double totalEfectivo = (Double) resumen.get("totalEfectivo");
    Double totalTarjeta = (Double) resumen.get("totalTarjeta");

    // Productos Más Vendidos
    List<ReporteVentas.ProductoMasVendido> productosMasVendidos = reporte.stream().map(item -> new ReporteVentas.ProductoMasVendido(((Number) item.get("productoId")).intValue(), (String) item.get("productoNombre"), ((Number) item.get("cantidadVendida")).intValue(), ((Number) item.get("popularidad")).doubleValue())).collect(Collectors.toList());
    return new ReporteVentas(totalVenta, totalCosto, gananciaTotal, numeroVentas, totalYape, totalPlin, totalEfectivo, totalTarjeta, productosMasVendidos);
  }

  @Override
  public ReporteVentas obtenerReporteVentasXFecha(LocalDate fechaInicio, LocalDate fechaFin, Integer empresaId) {
    List<Map<String, Object>> result = ventaRepository.obtenerReporteVentasXFecha(fechaInicio, fechaFin, empresaId);

    if (result.isEmpty()) {
      return new ReporteVentas(0.0, 0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0, Collections.emptyList());
    }

    // Extrae el resumen general del primer elemento de la lista
    Map<String, Object> resumen = result.get(0);

    ReporteVentas reporte = new ReporteVentas(convertToDouble(resumen.get("precioVentaTotal")), convertToDouble(resumen.get("precioCostoTotal")), convertToDouble(resumen.get("ganancia")), ((Number) resumen.get("totalVentas")).intValue(), convertToDouble(resumen.get("totalYape")), convertToDouble(resumen.get("totalPlin")), convertToDouble(resumen.get("totalEfectivo")), convertToDouble(resumen.get("totalTarjeta")), new ArrayList<>());

    // Llenar la lista de productos más vendidos
    List<ReporteVentas.ProductoMasVendido> productos = new ArrayList<>();
    for (Map<String, Object> productoRow : result) {
      ReporteVentas.ProductoMasVendido producto = new ReporteVentas.ProductoMasVendido((Integer) productoRow.get("productoId"), (String) productoRow.get("productoNombre"), ((Number) productoRow.get("cantidadVendida")).intValue(), convertToDouble(productoRow.get("popularidad")));
      productos.add(producto);
    }
    reporte.setProductosMasVendidos(productos);

    return reporte;
  }

  // Método de ayuda para convertir BigDecimal a Double, con verificación de null
  private Double convertToDouble(Object value) {
    if (value == null) {
      return 0.0;
    } else if (value instanceof BigDecimal) {
      return ((BigDecimal) value).doubleValue();
    } else if (value instanceof Double) {
      return (Double) value;
    } else if (value instanceof Integer) {
      return ((Integer) value).doubleValue();
    } else {
      throw new IllegalArgumentException("Unsupported number type: " + value.getClass());
    }
  }

  private VentaEntity convertVentasRequestToEntity(VentaRequest request) {
    VentaEntity entity = new VentaEntity();
    BeanUtils.copyProperties(request, entity);
    return entity;
  }

  private VentaResponse convertVentasEntityToResponse(VentaEntity entity) {
    VentaResponse response = new VentaResponse();
    BeanUtils.copyProperties(entity, response);
    return response;
  }
}
