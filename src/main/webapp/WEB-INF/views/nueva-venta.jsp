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
                    <h5 class="card-title">Registrar Cliente</h5>
                </div>
                <div class="card-body p-4">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="celular" class="control-label col-form-label">Número telefónico</label>
                            <input id="celular" type="number" class="form-control form-control-sm" placeholder="Número telefónico">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="nombreCliente" class="control-label col-form-label">Nombre del cliente</label>
                            <input id="nombreCliente" type="text" class="form-control form-control-sm" placeholder="Nombre del cliente">
                        </div>
                    </div>
                </div>
            </div>


            <div class="card mt-3">
                <div class="card-header">
                    <h5 class="card-title">Ventas Registradas</h5>
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
                        <h5 class="modal-title" id="ventaModalLabel">Registrar Venta</h5>
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
                                        <label for="chorizo" class="control-label col-form-label">Elige tu Chorizo Fav</label>
                                        <select id="chorizo" class="form-control form-control-sm chorizo-select" data-placeholder="Elige tu chorizo">
                                            <option value="">Elige tu Chorizo Fav</option>
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
                                        <label for="bebida" class="control-label col-form-label">Elige tu Bebida Fav</label>
                                        <select id="bebida" class="form-control form-control-sm bebida-select" data-placeholder="Elige tu bebida">
                                            <option value="">Elige tu Bebida Fav</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cantidadBebidas">Cantidad de Bebidas</label>
                                        <input id="cantidadBebidas" type="number" class="form-control form-control-sm cantidad-bebida" placeholder="Cantidad" min="1">
                                    </div>
                                </div>

                                <div class="form-actions text-center p-3">
                                    <button type="button" id="guardarPedido" class="btn btn-success btn-sm"> <i class="fa fa-check"></i> Guardar</button>
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



        <div class="text-center my-3">
            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#ventaModal">
                Agregar Pedido
            </button>
        </div>

        <!-- ============================================================== -->
        <!-- End Container fluid  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <%@ include file="includes/footer.jspf" %>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
    </div>

    <!-- ============================================================== -->
    <!-- End Page wrapper  -->
    <!-- ============================================================== -->
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="/static/web/dist/js/nuevaVenta.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
</body>
</html>
