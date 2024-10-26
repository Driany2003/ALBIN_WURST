<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            .
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">CLIENTES <span class="label label-rounded label-info">${empresaSession.empNombreComercial}</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-2">
                            <div class="d-flex justify-content-end mb-2">
                                <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#crearClienteModal">Ingresar Cliente</button>
                                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>  <!-- //id de la empresa en session -->
                                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>  <!-- id del usuario -->
                                <input id="usuarioNivel" type="hidden" value="${sessionScope.usuSessionNivel}"/> <!-- rol del usuario en session -->
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <div class="input-group" style="width: 300px;">
                                    <input type="text" class="form-control" id="tableFilter" placeholder="Buscar Cliente...">
                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="fas fa-search"></i>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="card-body p-2">
                        <div class="card-body p-2">
                            <div class="table-responsive">
                                <table id="clienteTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">TELEFONO</th>
                                        <th scope="col">NOMBRE</th>
                                        <th scope="col">CORREO</th>
                                        <th scope="col">NOTIFICACION</th>
                                        <th scope="col">ESTADO</th>
                                        <th scope="col">EMPRESA</th>
                                        <th scope="col">ACCION</th>
                                    </tr>
                                    </thead>
                                    <tbody id="clientesBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal Crear cliente -->
    <div class="modal fade" id="crearClienteModal" tabindex="-1" aria-labelledby="crearClienteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearClienteModalLabel">Registrar Cliente</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="clienteForm" enctype="multipart/form-data">
                        <!-- Navegación en Pestañas -->
                        <ul class="nav nav-tabs" id="crearClienteTabs" role="tablist">
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <li class="nav-item">
                                    <a class="nav-link active" id="empresa-tab" data-toggle="tab" href="#empresa" role="tab" aria-controls="empresa" aria-selected="true">
                                        <i class="fas fa-building" style="margin-right: 5px;"></i>
                                        Empresa
                                    </a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" id="informacionCliente-tab" data-toggle="tab" href="#informacionCliente" role="tab" aria-controls="informacionCliente" aria-selected="false">
                                    <i class="fas fa-id-card" style="margin-right: 5px;"></i>
                                    Información del Cliente
                                </a>
                            </li>
                        </ul>

                        <!-- Contenido de las Pestañas -->
                        <div class="tab-content" id="crearClienteTabsContent">
                            <!-- Empresa Tab -->
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <div class="tab-pane fade show active" id="empresa" role="tabpanel" aria-labelledby="empresa-tab">
                                    <br>
                                    <fieldset>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="cliente_empresa" class="bmd-label-floating">Seleccione la empresa
                                                            <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione la empresa a la que el cliente estará asignado."></i>
                                                        </label>
                                                        <select class="form-control" name="usuario_empresa_reg" id="cliente_empresa" required>
                                                            <!-- Aquí irán las opciones dinámicas -->
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </c:if>

                            <!-- Información del Cliente Tab -->
                            <div class="tab-pane fade show <c:if test='${sessionScope.usuSessionNivel == "PROPIETARIO"}'>active</c:if>" id="informacionCliente" role="tabpanel" aria-labelledby="informacionCliente-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_nombre" class="bmd-label-floating">Nombre del Cliente
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre del cliente."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="cli_nombre" id="cliente_nombre" maxlength="35" placeholder="Ingresar nombre del cliente" required>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_telefono" class="bmd-label-floating">Teléfono
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de teléfono del cliente."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="cli_telefono" id="cliente_telefono" maxlength="9" placeholder="Ingresar teléfono del cliente" required pattern="\d{9}">
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_correo" class="bmd-label-floating">Correo Electrónico
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el correo electrónico del cliente."></i>
                                                    </label>
                                                    <input type="email" class="form-control" name="cli_correo" id="cliente_correo" maxlength="50" placeholder="Ingresar correo electrónico" required>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label class="bmd-label-floating">¿Desea recibir notificaciones?
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione si el cliente desea recibir notificaciones."></i>
                                                    </label>
                                                    <br>
                                                    <div class="d-inline">
                                                        <div class="custom-control custom-radio custom-control-inline">
                                                            <input type="radio" class="custom-control-input" id="cliente_notificacion_si" name="cli_notificacion" value="Sí" required>
                                                            <label class="custom-control-label" for="cliente_notificacion_si">Sí</label>
                                                        </div>
                                                        <div class="custom-control custom-radio custom-control-inline">
                                                            <input type="radio" class="custom-control-input" id="cliente_notificacion_no" name="cli_notificacion" value="No" required>
                                                            <label class="custom-control-label" for="cliente_notificacion_no">No</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary" id="registrarCliente">Guardar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para editar cliente -->
    <div class="modal fade" id="editarClienteModal" tabindex="-1" aria-labelledby="editarClienteModalLabel" aria-hidden="true">
        <input id="editEmpresaIdModal" type="hidden" value="${empresaSession.empId}"/>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editarClienteModalLabel">Editar Usuario</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editarClienteForm" enctype="multipart/form-data">
                        <!-- Navegación en Pestañas -->
                        <ul class="nav nav-tabs" id="editarClienteTabs" role="tablist">
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <li class="nav-item">
                                    <a class="nav-link active" id="empresaEdit-tab" data-toggle="tab" href="#empresa-edit" role="tab" aria-controls="empresa" aria-selected="true">
                                        <i class="fas fa-building" style="margin-right: 5px;"></i>
                                        Empresa
                                    </a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" id="informacionEdit-tab" data-toggle="tab" href="#informacion-edit" role="tab" aria-controls="informacion" aria-selected="false">
                                    <i class="fas fa-info-circle" style="margin-right: 5px;"></i>
                                    Información del Cliente
                                </a>
                            </li>
                        </ul>

                        <!-- Contenido de las Pestañas -->
                        <div class="tab-content" id="editarUsuarioTabsContent">
                            <!-- Tab 1: Información de la Empresa (Solo para Admins) -->
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <div class="tab-pane fade show active" id="empresa-edit" role="tabpanel" aria-labelledby="empresa-edit-tab">
                                    <br>
                                    <fieldset>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="cliente_empresa_edit" class="bmd-label-floating">Seleccione la empresa
                                                            <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione la empresa a la que el cliente estará asignado."></i>
                                                        </label>
                                                        <select class="form-control" name="cliente_empresa_edit" id="cliente_empresa_edit" required>
                                                            <!-- Aquí irán las opciones dinámicas -->
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </c:if>
                            <!-- Tab 2: Información Personal -->
                            <div class="tab-pane fade ${sessionScope.usuSessionNivel != 'ADMINISTRADOR' ? 'show active' : ''}" id="informacion-edit" role="tabpanel" aria-labelledby="informacionEdit-tab">
                                <br>
                                <fieldset>
                                    <input type="hidden" id="cliId">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_nombre_edit" class="bmd-label-floating">Nombre del Cliente
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre del cliente."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="cliente_nombre" id="cliente_nombre_edit" maxlength="35" placeholder="Ingresar nombre del cliente" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_telefono_edit" class="bmd-label-floating">Teléfono
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de teléfono del cliente."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="cliente_telefono" id="cliente_telefono_edit" maxlength="15" placeholder="Ingresar número telefónico" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_correo_edit" class="bmd-label-floating">Correo Electrónico
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el correo electrónico del cliente."></i>
                                                    </label>
                                                    <input type="email" class="form-control" name="cliente_correo" id="cliente_correo_edit" maxlength="50" placeholder="Ingresar correo electrónico" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label class="bmd-label-floating">¿Recibe Notificaciones?
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Indique si el cliente recibe notificaciones."></i>
                                                    </label>
                                                    <br>
                                                    <div class="d-inline">
                                                        <div class="custom-control custom-radio custom-control-inline">
                                                            <input type="radio" class="custom-control-input" id="cliente_notificacion_edit_si" name="cli_notificacion" value="true" required>
                                                            <label class="custom-control-label" for="cliente_notificacion_edit_si">Sí</label>
                                                        </div>
                                                        <div class="custom-control custom-radio custom-control-inline">
                                                            <input type="radio" class="custom-control-input" id="cliente_notificacion_edit_no" name="cli_notificacion" value="false" required>
                                                            <label class="custom-control-label" for="cliente_notificacion_edit_no">No</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="cliente_activo_edit" class="bmd-label-floating">¿Está Activo?
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Indique si el cliente está activo."></i>
                                                    </label>
                                                    <select name="cliente_activo" id="cliente_activo_edit" class="form-control" required>
                                                        <option value="true">Activo</option>
                                                        <option value="false">Inactivo</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                        <!-- Botones de Acción -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-primary" id="editarCliente">Guardar Cambios</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de confirmación para eliminar cliente -->
    <div id="confirmDeleteModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar eliminación</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>¿Estás seguro de que deseas eliminar este cliente?</p>
                    <input type="hidden" id="deleteClienteId">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-danger" id="eliminarCliente">Eliminar</button>
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

<!-- functions -->
<script src="/static/web/dist/js/funciones/cliente.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>

</body>
</html>