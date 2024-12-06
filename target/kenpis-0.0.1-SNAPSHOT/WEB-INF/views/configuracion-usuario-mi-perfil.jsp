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
                <div class="container"> <!-- Reducido el tamaño -->
                    <div class="container shadow-sm bg-white rounded p-4">
                        <div class="row align-items-center mb-4">
                            <canvas id="initialsCanvas" class="rounded-circle shadow" width="100" height="100" style="width: 100px; height: 100px; margin-right: 5px;"></canvas>
                            <!-- Texto centrado -->
                            <div class="col text-center" style="margin-left: 10px;">
                                <h3 class="font-weight-bold mt-3 text-primary">${usuario.usuNombre} ${usuario.usuApePaterno}</h3>
                                <p class="text-center">Tu número de cliente: <span class="text-primary">${usuario.usuId}</span></p>
                            </div>
                        </div>
                        <!-- Separador -->
                        <hr class="my-4" style="border-top: 2px solid #007bff;">
                        <div class="container rounded bg-white p-4">
                            <div class="row">
                                <!-- Navegación en Pestañas -->
                                <div class="col-lg-12">
                                    <ul class="nav nav-tabs" id="editarPerfilTabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="personal-tab" data-toggle="tab" href="#personal" role="tab" aria-controls="personal" aria-selected="true">
                                                <i class="fas fa-user mr-2"></i>Información Personal
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link " id="contrasena-tab" data-toggle="tab" href="#contrasena" role="tab" aria-controls="contrasena" aria-selected="false">
                                                <i class="fas fa-lock mr-2"></i>Cambiar Contraseña
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="tab-content mt-4" id="editarPerfilTabsContent">
                                <!-- Información Personal -->
                                <div class="tab-pane fade show active" id="personal" role="tabpanel" aria-labelledby="personal-tab">
                                    <form id="editarPerfilForm" enctype="multipart/form-data">
                                        <input type="hidden" id="usuId" value="${usuario.usuId}">
                                        <input type="hidden" id="empresaId" value="${usuario.empresaId}">
                                        <div class="form-row">
                                            <div class="form-group col-md-6">
                                                <label for="nombre">Nombre</label>
                                                <input type="text" class="form-control border-secondary" id="nombre" value="${usuario.usuNombre}" style="text-transform: uppercase;">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="apellidoPaterno">Apellido Paterno</label>
                                                <input type="text" class="form-control border-secondary" id="apellidoPaterno" value="${usuario.usuApePaterno}" style="text-transform: uppercase;">
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-md-6">
                                                <label for="apellidoMaterno">Apellido Materno</label>
                                                <input type="text" class="form-control border-secondary" id="apellidoMaterno" value="${usuario.usuApeMaterno}" style="text-transform: uppercase;">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="telefono">Teléfono</label>
                                                <input type="text" class="form-control border-secondary" id="telefono" value="${usuario.usuTelefono}">
                                            </div>
                                        </div>
                                        <!-- Agregar línea separadora -->
                                        <hr class="my-4">
                                        <div class="form-row">
                                            <div class="form-group col-md-4">
                                                <label for="correoUsuario">Correo Electrónico</label>
                                                <input type="email" class="form-control border-secondary" id="correoUsuario" value="${usuario.usuCorreo}">
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label for="tipoDocumento">Tipo Documento</label>
                                                <input type="text" class="form-control border-secondary" id="tipoDocumento" disabled value="${usuario.usuTipoDocumento}">
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label for="numeroDocumento">Número Documento</label>
                                                <input type="text" class="form-control border-secondary" id="numeroDocumento" disabled value="${usuario.usuNumeroDocumento}">
                                            </div>
                                        </div>
                                        <div class="text-center mt-3">
                                            <button type="button" id="actualizarPerfil" class="btn btn-primary btn-lg px-4 py-2" disabled>Actualizar perfil</button>
                                        </div>
                                    </form>
                                </div>

                                <!-- Cambiar Contraseña -->
                                <div class="tab-pane fade" id="contrasena" role="tabpanel" aria-labelledby="contrasena-tab">
                                    <form id="resetClaveForm" class="mx-auto">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="claveActual">Clave Actual</label>
                                                    <div class="input-group">
                                                        <input type="password" class="form-control border-secondary" id="claveActual" placeholder="Ingrese su clave actual" required>
                                                        <div class="input-group-append">
                                                            <button id="validarClave" type="button" class="btn btn-outline-secondary" title="Validar Clave">
                                                                <i class="fas fa-search"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr class="my-4">
                                            <div class="col-md-6" id="nuevaClaveSection" style="display: none;">
                                                <div class="form-group">
                                                    <label for="nuevaClave">Nueva Clave</label>
                                                    <div class="input-group">
                                                        <input type="password" class="form-control border-secondary" id="nuevaClave" placeholder="Ingrese su nueva clave" required>
                                                        <div class="input-group-append">
                                                            <button id="guardarNuevaClave" type="button" class="btn btn-outline-secondary" title="Guardar Nueva Clave">
                                                                <i class="fas fa-save"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
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
<link href="/static/web/assets/extra-libs/taskboard/css/lobilist.css" rel="stylesheet">
<link href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css" rel="stylesheet">
<link href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">

<!--  functions-->
<script src="/static/web/dist/js/funciones/usuarioMiPerfil.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>
<!-- End footer -->
</body>
</html>