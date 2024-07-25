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
            <div class="card mt-3">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-12">
                            <h3 class="card-title">${empresaSession.empNombreComercial}</h3>
                            <h5 class="card-title">${empresaSession.empDocumentoTipo} : ${empresaSession.empDocumentoNumero}</h5>
                            <input id="empId" type="hidden" value="${empresaSession.empId}"/>
                        </div>
                    </div>
                </div>
                <div class="card mt-3">
                    <div class="card-header">
                        <div class="form-row">
                            <div class="col-md-11">
                                <h5 class="card-title">Datos del Cliente</h5>
                            </div>
                            <div class="text-center col-md-42">
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#clienteModal">
                                    Nuevo
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-4">
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="cliTelefono" class="control-label col-form-label">Número Celular</label>
                                <input id="cliTelefono" type="text" class="form-control form-control-sm" maxlength="9" placeholder="Ingrese el número de celular">
                            </div>
                            <div class="form-group col-md-6">
                                <label for="cliNombre" class="control-label col-form-label">Nombre</label>
                                <input id="cliNombre" type="text" class="form-control form-control-sm" readonly placeholder="---">
                                <input id="cliId" type="hidden"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card mt-3">
                    <div class="card-header">
                        <div class="form-row">
                            <div class="col-md-11">
                                <h5 class="card-title">Detalle Pedido</h5>
                            </div>
                            <div class="text-center col-md-42">
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#ventaModal">
                                    Agregar
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-3">
                        <div class="table-responsive">
                            <table id="ventasTable" class="table table-sm table-bordered table-striped">
                                <thead class="thead-dark">
                                <tr>
                                    <th scope="col">PRODUCTOS</th>
                                    <th scope="col">CANTIDAD</th>
                                    <th scope="col">PRECIO</th>
                                    <th scope="col">TOTAL</th>
                                </tr>
                                </thead>
                                <tbody id="ventasBody">

                                </tbody>
                                <tfoot>
                                <tr>
                                    <th colspan="3" class="text-right">Total a pagar:</th>
                                    <th id="totalPagar">S/ 0.00</th>
                                </tr>
                                <tr>
                                    <th colspan="3"></th>
                                    <th>
                                        <button id="pagarButton" class="btn btn-success btn-sm">Pagar</button>
                                    </th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="ventaModal" tabindex="-1" aria-labelledby="ventaModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="ventaModalLabel">Agregar Producto</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="ventaForm">
                                <div class="form-body">
                                    <!-- Chorizos -->
                                    <div id="chorizos-container">
                                        <div class="form-group">
                                            <label for="tipoChorizo" class="control-label col-form-label">¿Cómo desea su chorizo?</label>
                                            <select id="tipoChorizo" class="form-control form-control-sm chorizo-select">
                                                <option value="">-- Seleccione --</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="chorizo" class="control-label col-form-label">Sabor de Chorizo</label>
                                            <select id="chorizo" class="form-control form-control-sm chorizo-select">
                                                <option value="">-- Seleccione --</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="cantidadChorizos">Cantidad de Chorizos</label>
                                            <input id="cantidadChorizos" type="number" class="form-control form-control-sm cantidad-chorizo" placeholder="Cantidad" min="1">
                                        </div>
                                    </div>

                                    <!-- Bebidas -->
                                    <div id="bebidas-container">
                                        <div class="form-group">
                                            <label for="tipoBebida" class="control-label col-form-label">¿Qué desea tomar?</label>
                                            <select id="tipoBebida" class="form-control form-control-sm bebida-select" data-placeholder="Elige la presentación">
                                                <option value="">-- Seleccione --</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="bebida" class="control-label col-form-label">Bebida</label>
                                            <select id="bebida" class="form-control form-control-sm bebida-select" data-placeholder="Elige la bebida">
                                                <option value="">-- Seleccione --</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="cantidadBebidas">Cantidad de Bebidas</label>
                                            <input id="cantidadBebidas" type="number" class="form-control form-control-sm cantidad-bebida" placeholder="Cantidad" min="1">
                                        </div>
                                    </div>

                                    <div class="form-actions text-center p-3">
                                        <button type="button" id="guardarPedido" class="btn btn-success btn-sm"><i class="fa fa-check"></i> Guardar</button>
                                        <button type="button" class="btn btn-dark btn-sm" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="popupNoRegistrado" tabindex="-1" aria-labelledby="popupNoRegistradoLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="popupNoRegistradoLabel">Usuario No Registrado</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>El número telefónico ingresado no está registrado. Por favor, registre al cliente antes de proceder.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
        </div>
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
    <!-- PARA MODAL -->
    <!--  modal: NUEVA VENTA -->
    <script src="/static/web/dist/js/funciones/nuevaVenta.js"></script>
    <style>
        .card {
            margin-top: 10px;
        }

        .form-body {
            padding: 10px;
        }

        .form-actions {
            padding: 10px;
        }

        .table-responsive {
            margin-top: 10px;
        }

        .table th, .table td {
            text-align: center;
            padding: 5px;
        }

        .btn-sm {
            font-size: 0.8rem;
        }

        .card-header h5 {
            margin-bottom: 0;
        }

        .card {
            margin-top: 10px;
        }

        .form-body {
            padding: 10px;
        }

        .form-actions {
            padding: 10px;
        }

        .table-responsive {
            margin-top: 10px;
        }

        .table th, .table td {
            text-align: center;
            padding: 5px;
        }

        .btn-sm {
            font-size: 0.8rem;
        }

        .card-header h5 {
            margin-bottom: 0;
        }

        .modal-content {
            border-radius: 10px;
        }

        .modal-header {
            background-color: #f5f5f5;
            border-bottom: none;
        }

        .modal-title {
            color: #333;
        }

        .modal-footer {
            border-top: none;
        }
    </style>
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
