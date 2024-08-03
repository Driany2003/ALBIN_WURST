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
                            <div class="form-group col-md-3">
                                <input id="cliNombre" type="text" class="form-control form-control-sm" disabled placeholder="---">
                                <input id="clienteId" type="hidden"/>
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
                                    <div id="productos-container">
                                        <div class="form-row d-flex flex-column flex-md-row">
                                            <div class="form-group col-12 col-md-3">
                                                <label for="categoria" class="control-label col-form-label">¿Cómo desea su chorizo?</label>
                                                <select id="categoria" class="form-control form-control-sm tipo-categoria-select">
                                                    <option value="">-- Seleccione --</option>
                                                </select>
                                            </div>
                                            <div class="form-group col-12 col-md-3">
                                                <label for="subCategoria" class="control-label col-form-label">Sabor</label>
                                                <select id="subCategoria" class="form-control form-control-sm subCategoria-select">
                                                    <option value="">-- Seleccione --</option>
                                                </select>
                                            </div>
                                            <div class="form-group col-12 col-md-3">
                                                <label for="precioProducto" class="control-label col-form-label">Precio</label>
                                                <input id="precioProducto" type="number" class="form-control form-control-sm precio" min="1" disabled>
                                            </div>
                                            <div class="form-group col-12 col-md-3">
                                                <label for="cantidad" class="control-label col-form-label">Cantidad</label>
                                                <input id="cantidad" type="number" class="form-control form-control-sm cantidad" placeholder="Cantidad" min="1">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-actions text-center p-3">
                                    <button type="button" id="guardarPedido" class="btn btn-success btn-sm"><i class="fa fa-check"></i> Guardar</button>
                                    <button type="button" class="btn btn-dark btn-sm" data-dismiss="modal">Cancelar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>



            <!-- Modal -->
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



            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->

            <!-- ============================================================== -->
            <!-- End Page wrapper  -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- All Jquery -->
        <!-- ============================================================== -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- PARA MODAL -->
        <!--  modal: NUEVA VENTA -->
        <script src="/static/web/dist/js/funciones/nuevaVenta.js"></script>
        <%@ include file="includes/all-jquery.jspf" %>
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <%@ include file="includes/footer.jspf" %>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
</body>
</html>