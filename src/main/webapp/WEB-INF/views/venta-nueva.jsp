<%@ page contentType="text/html; charset=UTF-8" %>
<html dir="ltr" lang="en">
<%@ include file="includes/header.jspf" %>
<body>
<!-- ============================================================== -->
<!-- Preloader - style you can find in spinners.css -->
<!-- ============================================================== -->
<%@ include file="includes/preloader.jspf" %>
<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.scss -->
<!-- ============================================================== -->
<div id="main-wrapper">
    <!-- ============================================================== -->
    <!-- Topbar header - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <%@ include file="includes/topbar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Topbar header -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <%@ include file="includes/left-sidebar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Page wrapper  -->
    <!-- ============================================================== -->
    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">VENTA <span class="label label-rounded label-info">Nueva</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-9">
                            <h1 class="card-title">${empresaSession.empNombreComercial}</h1>
                            <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                        </div>
                        <div class="form-group col-md-3 text-md-right">
                            <label class="font-10">${empresaSession.empDocumentoTipo} : ${empresaSession.empDocumentoNumero}</label><br>
                            <label class="font-10">Vendedor: ${usuSessionNombre}</label>
                            <br>
                            <button class="btn btn-outline-primary" data-toggle="modal" data-target="#aperturarCajaModal">Aperturar Caja</button>
                            <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                        </div>
                    </div>
                </div>
                <div class="card-body p-3">
                    <div class="card-body p-1">
                        <div class="form-row">
                            <div class="form-group col-md-2 text-md-right align-middle">
                                <label for="cliTelefono" class="control-label col-form-label"># Celular Cliente</label>
                            </div>
                            <div class="form-group col-md-2">
                                <div class="input-group">
                                    <input id="cliTelefono" type="text" class="form-control form-control-sm" maxlength="9" placeholder="Ingrese número">
                                    <div class="input-group-append">
                                        <span class="input-group-text" id="buscarCliente" style="cursor: pointer;">
                                            <i class="fa fa-search"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-md-1 text-md-right align-middle">
                                <label for="cliNombre" class="control-label col-form-label">Nombre</label>
                            </div>
                            <div class="form-group col-md-1">
                                <input id="cliNombre" type="text" class="form-control form-control-sm" disabled placeholder="---">
                                <input id="clienteId" type="hidden"/>
                            </div>
                            <div class="form-group col-md-1 text-md-right align-middle" id="alias-label">
                                <label for="alias" class="control-label col-form-label">Alias</label>
                            </div>
                            <div class="form-group col-md-1" id="alias-field" style="display: none;">
                                <input id="alias" type="text" class="form-control form-control-sm">
                            </div>

                            <div class="form-group col-md-2 text-md-right align-middle">
                                <label for="venTipoPago" class="control-label col-form-label">Tipo Pago</label>
                            </div>
                            <div class="form-group col-md-2 text-md-right align-middle">
                                <select id="venTipoPago" class="form-control form-control-sm">
                                    <option value="">-- Seleccione --</option>
                                    <option value="EFECTIVO">EFECTIVO</option>
                                    <option value="YAPE">YAPE</option>
                                    <option value="PLIN">PLIN</option>
                                </select>
                            </div>

                        </div>
                    </div>
                    <div class="card-header">
                        <div class="form-row">
                            <div class="col-md-11">
                                <h5 class="card-title">Detalle Pedido</h5>
                            </div>
                            <div class="text-center col-md-1">
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#ventaModal">
                                    Agregar
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-2">
                        <div class="table-responsive">
                            <table id="ventasTable" class="table table-sm table-bordered table-striped">
                                <thead class="thead-dark">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">PRODUCTO</th>
                                    <th scope="col">CANTIDAD</th>
                                    <th scope="col">PRECIO</th>
                                    <th scope="col">TOTAL</th>
                                    <th scope="col"></th>
                                </tr>
                                </thead>
                                <tbody id="ventasBody">

                                </tbody>
                                <tfoot>
                                <tr>
                                    <th colspan="4" class="text-right">Total a pagar:</th>
                                    <th id="totalPagar">S/ 0.00</th>
                                </tr>
                                <tr>
                                    <th colspan="4"></th>
                                    <th class="text-right">
                                        <button id="pagarButton" class="btn btn-success btn-sm">Grabar</button>
                                    </th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Ventas -->
            <div class="modal fade" id="ventaModal" tabindex="-1" role="dialog" aria-labelledby="ventaModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-xl" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="ventaModalLabel">Agregar Pedido</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="ventaForm">
                                <div class="form-body">
                                    <div id="productos-container" class="container-fluid">
                                        <div id="detalle-container" class="d-flex flex-wrap">

                                        </div>
                                    </div>
                                </div>
                                <div class="form-actions text-center p-3">
                                    <button type="button" id="volverSubCategorias" class="btn btn-secondary btn-sm" style="display: none;"><i class="fa fa-arrow-left"></i> Volver</button>
                                    <button type="button" id="volverCategorias" class="btn btn-secondary btn-sm" style="display: none;"><i class="fa fa-arrow-left"></i> Volver</button>
                                    <button type="button" id="guardarPedido" class="btn btn-success btn-sm"><i class="fa fa-check"></i> Guardar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal para registrar cliente-->
            <div class="modal fade" id="clienteModal" tabindex="-1" role="dialog" aria-labelledby="clienteModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="clienteModalLabel">Registrar Cliente</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="cliNombrePopap">Nombre</label>
                                <input type="text" class="form-control" id="cliNombrePopap">
                            </div>
                            <div class="form-group">
                                <label for="cliTelefonoNoRegistrado">Teléfono</label>
                                <input type="text" class="form-control" id="cliTelefonoNoRegistrado">
                            </div>
                            <div class="form-group">
                                <label for="cliCorreoPopap">Correo</label>
                                <input type="email" class="form-control" id="cliCorreoPopap" value="correo@correo.com">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-primary" id="registrarCliente">Registrar Cliente</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- MODAL PARA APERTURAR CAJA -->
        <div class="modal fade" id="aperturarCajaModal" tabindex="-1" role="dialog" aria-labelledby="aperturarCajaModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="aperturarCajaModalLabel">Aperturar Rapida de Caja</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="aperturarCajaForm">
                            <div class="form-group bmd-form-group is-filled">
                                <label for="sucursalNom" class="bmd-label-floating">Seleccione una Sucursal</label>
                                <select class="form-control" name="sucursalNom_reg" id="sucursalNom" required>
                                    <option value="">-- Elegir una Sucursal --</option>
                                    <c:forEach var="sucursal" items="${listaDeSucursales}">
                                        <option value="${sucursal.empId}">${sucursal.empNombreComercial}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="montoInicial">Monto Inicial</label>
                                <input type="number" class="form-control" id="montoInicial" required placeholder="Ingresar un Monto Inicial">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                <button type="submit" class="btn btn-primary">Registrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- customs css -->
<link href="/static/css/custom.css" rel="stylesheet">

<!-- customs -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/dragula/3.7.3/dragula.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<link href="/static/web/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
<link href="/static/web/assets/extra-libs/taskboard/css/lobilist.css" rel="stylesheet" >
<link href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css" rel="stylesheet" >
<link href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet" >
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">

<!--  functions-->
<script src="/static/web/dist/js/funciones/ventaNueva.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>
<!-- End footer -->
</body>
</html>