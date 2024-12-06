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
<%@ page contentType="text/html; charset=UTF-8" %>
<html dir="ltr" lang="es">
<%@ include file="includes/header.jspf" %>
<body>
<%@ include file="includes/preloader.jspf" %>

<div id="main-wrapper">
    <%@ include file="includes/topbar.jspf" %>
    <%@ include file="includes/left-sidebar.jspf" %>

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
                    <div class="form-row d-flex justify-content-between align-items-center">
                        <div class="col-md-9">
                            <h1 class="card-title">${empresaSession.empNombreComercial}</h1>
                            <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                            <input id="empresaNombre" type="hidden" value="${empresaSession.empNombreComercial}"/>
                            <input id="empresalogo" type="hidden" value="${empresaSession.empImagenLogo}">
                            <input id="empresaRuc" type="hidden" value="${empresaSession.empDocumentoNumero}">
                            <input id="empresaTelefono" type="hidden" value="${empresaSession.empTelefono}">
                            <input id="vendedorNombre" type="hidden" value="${usuSessionNombre}">
                        </div>
                        <div class="form-group col-md-3 text-md-right d-flex align-items-center justify-content-end">
                            <span class="font-10 mr-3">${empresaSession.empDocumentoTipo}: <p>${empresaSession.empDocumentoNumero}</p></span>
                            <span id="vendedorNombreLabel" class="font-10 mr-3">Vendedor: <p>${usuSessionNombre}</p></span>
                            <span id="sucursalNombreLabel" class="font-10 mr-3">Sucursal: <p></p></span>
                            <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                        </div>
                    </div>
                </div>

                <div class="card-body p-3">
                    <div class="card-body p-3">
                        <div class="form-row align-items-center">
                            <div class="form-group col-md-3">
                                <label for="cliTelefono" class="control-label col-form-label"># Celular Cliente</label>
                                <div class="input-group">
                                    <input id="cliTelefono" type="text" class="form-control form-control-sm" maxlength="9" placeholder="Ingrese número">
                                    <div class="input-group-append">
                                        <span class="input-group-text" id="buscarCliente" style="cursor: pointer;">
                                            <i class="fa fa-search"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group col-md-3">
                                <label for="cliNombre" class="control-label col-form-label">Nombre</label>
                                <input id="cliNombre" type="text" class="form-control form-control-sm" disabled placeholder="---">
                                <input id="clienteId" type="hidden"/>
                            </div>

                            <div class="form-group col-md-3">
                                <label for="venTipoPago" class="control-label col-form-label">Tipo Pago</label>
                                <select id="venTipoPago" class="form-control form-control-sm">
                                    <option value="">-- Seleccione --</option>
                                    <option value="EFECTIVO">EFECTIVO</option>
                                    <option value="YAPE">YAPE</option>
                                    <option value="PLIN">PLIN</option>
                                    <option value="TARJETA">TARJETA</option>
                                </select>
                            </div>

                            <div class="form-group col-md-3" id="alias-field" style="display: none;">
                                <label for="alias" class="control-label col-form-label">Alias</label>
                                <input id="alias" type="text" class="form-control form-control-sm">
                            </div>
                        </div>
                    </div>

                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0">Detalle del Pedido</h5>
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#ventaModal">Agregar</button>
                    </div>

                    <div class="card-body p-2">
                        <div class="table-responsive">
                            <table id="ventasTable" class="table table-sm table-bordered table-striped">
                                <thead class="thead-dark">
                                <tr>
                                    <th scope="col">ACCION</th>
                                    <th scope="col">PRODUCTO</th>
                                    <th scope="col">CANTIDAD</th>
                                    <th scope="col">PRECIO</th>
                                    <th scope="col">TOTAL</th>
                                </tr>
                                </thead>
                                <tbody id="ventasBody"></tbody>
                                <tfoot>
                                <tr>
                                    <th colspan="4" class="text-right">Total a pagar:</th>
                                    <th id="totalPagar">S/ 0.00</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <div class="d-flex justify-content-end mt-3">
                            <button id="pagarButton" class="btn btn-success">Pagar</button>
                        </div>
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
                                    <div id="detalle-container" class="d-flex flex-wrap"></div>
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

        <!-- Modal de Confirmación de Pago -->
        <div class="modal fade" id="confirmarPagoModal" tabindex="-1" role="dialog" aria-labelledby="confirmarPagoModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmarPagoModalLabel">Confirmar Pago</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        ¿Estás seguro de que deseas confirmar este pago de <strong>S/ <span id="modalTotalPagar"></span></strong>?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" id="confirmarPagoButton">Confirmar Pago</button>
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
</div>
<!-- Modal para seleccionar una caja abierta -->
<div class="modal fade" id="seleccionarCajaModal" tabindex="-1" role="dialog" aria-labelledby="seleccionarCajaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="seleccionarCajaModalLabel">Seleccione una Caja Activa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="formSeleccionCaja">
                    <div class="form-group">
                        <label for="cajaSelect">Caja Disponible</label>
                        <select class="form-control" id="cajaSelect" required>
                            <!-- Opciones de cajas abiertas se cargan dinámicamente aquí -->
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnSeleccionarCaja">Seleccionar Caja</button>
            </div>
        </div>
    </div>
</div>

<div id="detalleVentaModal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Voucher de Venta</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Los detalles de la venta se insertarán aquí -->
            </div>
        </div>
    </div>
</div>


<style>
    .voucher {
        font-family: Arial, sans-serif;
        font-size: 12px;
        line-height: 1.5;
        width: 300px;
        margin: auto;
        text-align: center;
    }

    .voucher img {
        max-width: 100px;
        height: auto;
        margin-bottom: 10px;
    }

    .voucher p {
        margin: 0;
        padding: 0;
    }

    .voucher div {
        margin-bottom: 10px;
    }


    /* Para la sección de complementos, agregamos espacio abajo */
    .complementos-section {
        margin-top: 20px; /* Espaciado superior */
        padding: 10px; /* Espaciado interno */
        background-color: #f8f9fa; /* Fondo suave para los complementos */
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Sombra suave */
    }

    /* Para los complementos principales */
    .complemento-item {
        margin-bottom: 10px; /* Espaciado entre los complementos */
    }

    /* Subcomplementos ocultos inicialmente */
    .subcomplementos-list {
        display: none; /* Los subcomplementos están ocultos hasta que se marque el complemento */
        padding-left: 20px; /* Sangría para subcomplementos */
        margin-top: 10px; /* Espaciado superior para los subcomplementos */
    }

    /* Estilo para cada subcomplemento */
    .subcomplemento-item {
        margin-bottom: 5px; /* Espaciado entre los subcomplementos */
    }

    /* Etiqueta de los subcomplementos */
    .subcomplemento-label {
        font-size: 14px;
        color: #555; /* Color más oscuro para los subcomplementos */
        margin-left: 8px;
    }


    .card {
        border-radius: 10px;
        border: 1px solid #e3e6f0;
        transition: all 0.2s ease-in-out;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);

    }

    /* Header Styling */
    .card-header {
        background-color: #f8f9fc;
        border-bottom: 1px solid #e3e6f0;
        font-weight: bold;
    }

    .card-img-top {
        object-fit: cover;
        height: 150px;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }


    .modal-footer {
        justify-content: center;
    }

    /* Table Styling */
    .table thead th {
        background-color: #007bff;
        color: white;
        text-align: center;
    }

    .table th, .table td {
        padding: 12px;
        vertical-align: middle;
    }

    .btn-success:hover {
        background-color: #218838;
    }

    .input-group-text {
        background-color: #007bff;
        color: white;
        border-radius: 0 5px 5px 0;
    }

    .input-group .form-control {
        border-radius: 5px 0 0 5px;
    }

    /* Align footer button */
    .d-flex {
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .mt-3 {
        margin-top: 1rem;
    }

    .btn-primary:hover {
        background-color: #0056b3;
    }

    .btn-success {
        background-color: #28a745;
        color: white;
        margin-left: 10px;
    }

    #totalPagar {
        font-weight: bold;
        font-size: 1.25em;
        color: #28a745;
        text-align: right;
    }

    /* Button Styling */
    .btn-primary, .btn-success {
        font-size: 14px;
        border-radius: 5px;
        padding: 8px 16px;
        transition: background-color 0.2s ease-in-out;
    }

    /* Estilos para centrar y dar formato a las tarjetas */
    #detalle-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
    }

    .categoria-card {
        width: 200px; /* Ancho de las tarjetas más grande */
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s, box-shadow 0.2s;
        cursor: pointer;
        text-align: center;
    }

    .categoria-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    }

    .categoria-img {
        height: 120px; /* Aumentamos la altura de la imagen */
        width: 100%;
        object-fit: cover;
        border-bottom: 1px solid #e0e0e0;
    }

    .categoria-card .card-body {
        padding: 15px;
    }

    .categoria-card .card-title {
        font-size: 1.1rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 5px;
    }

    .categoria-card .card-text {
        font-size: 0.9rem;
        color: #666;
    }

    /* Centrar título del modal */
    .modal-title {
        font-weight: bold;
        text-align: center;
    }

    /* Estilo para los botones dentro del modal */
    .modal-footer .btn {
        border-radius: 5px;
        padding: 10px 20px;
    }

    /* Contenedor y formato de las tarjetas */
    #detalle-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
    }

    .subcategoria-card {
        width: 250px; /* Ancho aumentado para una mejor visualización */
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s, box-shadow 0.2s;
        cursor: pointer;
        text-align: center;
    }

    .subcategoria-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    }

    /* Imagen de los productos */
    .producto-img {
        height: 180px; /* Aumento de altura para mejor visualización */
        width: 100%;
        object-fit: cover;
        border-bottom: 1px solid #e0e0e0;
    }

    /* Contenido de la tarjeta */
    .subcategoria-card .card-body {
        padding: 15px;
    }

    .subcategoria-card .card-title {
        font-size: 1.1rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 5px;
    }

    .subcategoria-card .card-text {
        font-size: 0.9rem;
        color: #666;
        margin-bottom: 8px;
    }

    .subcategoria-card .card-text.text-primary {
        font-size: 1rem;
        color: #007bff;
        font-weight: bold;
    }

    /* Centrar el título del modal */
    .modal-title {
        font-weight: bold;
        text-align: center;
    }

    /* Estilo para los botones dentro del modal */
    .modal-footer .btn {
        border-radius: 5px;
        padding: 10px 20px;
    }

    /* Contenedor de Detalle */
    #detalle-container {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        gap: 20px;
    }

    /* Tarjeta de Producto */
    .producto-card {
        width: 300px; /* Aumentamos el tamaño de la tarjeta */
        border-radius: 10px;
        border: 1px solid #e3e6f0;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        transition: all 0.2s ease-in-out;
        overflow: hidden;
        text-align: center;
    }

    .producto-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    }

    /* Imagen del Producto */
    .producto-img {
        height: 200px;
        width: 100%;
        object-fit: cover;
        border-bottom: 1px solid #e0e0e0;
    }

    /* Contenido de la Tarjeta del Producto */
    .producto-card .card-body {
        padding: 15px;
    }

    .producto-card .card-title {
        font-size: 1.2rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 10px;
    }

    .producto-card .card-text-descripcion {
        font-size: 0.9rem;
        color: #666;
        margin-bottom: 10px;
    }

    .producto-card .card-text {
        font-size: 1rem;
        color: #007bff;
        font-weight: bold;
    }

    /* Contenedor de Cantidad */
    .quantity-container {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 10px;
        margin-top: 10px;
    }

    .quantity-button {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    .quantity-button:hover {
        background-color: #0056b3;
    }

    .quantity-display {
        font-size: 18px;
        font-weight: bold;
        margin: 0 10px;
        color: #333;
    }

    /* Sección de Complementos */
    .complementos-section {
        max-width: 200px;
        padding: 10px;
        background-color: #f8f9fa;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    }

    .complementos-section h6 {
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
    }

    .complemento-item {
        display: flex;
        align-items: center;
        font-size: 0.9rem;
        color: #555;
    }

    .complemento-item input[type="checkbox"] {
        margin-right: 10px;
    }

    .form-row .form-group {
        margin-bottom: 0;
    }

    #alias-field {
        display: none; /* Escondido por defecto */
    }

    /* Mostrar el campo de alias cuando sea necesario */
    .show-alias #alias-field {
        display: block;
    }

    /* Ajustes adicionales para que se vea mejor en una sola fila */
    @media (min-width: 768px) {
        .form-group.col-md-3 {
            max-width: 24%; /* Ajuste de ancho para que los cuatro campos quepan en una fila */
        }
    }


</style>

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
<link href="/static/web/assets/extra-libs/taskboard/css/lobilist.css" rel="stylesheet">
<link href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css" rel="stylesheet">
<link href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet">
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