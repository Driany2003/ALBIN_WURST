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
        <!-- ============================================================== -->
        <!-- Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">EMPRESA <span class="label label-rounded label-info">Lista</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                    <div class="card-header">
                        <div class="form-row">
                            <div class="col-md-2">
                                <div class="d-flex justify-content-end mb-2">
                                    <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#empresaModal">Crear Empresa</button>
                                </div>
                            </div>
                            <div class="col-md-10">
                                <div class="d-flex justify-content-end mb-2">
                                    <div class="input-group" style="width: 300px;">
                                        <input type="text" class="form-control" id="tableFilter" placeholder="Filtra Empresas...">
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
                </c:if>
                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                <input id="usuarioNivel" type="hidden" value="${usuSessionNivel}"/>

                <!-- Modal para Crear Empresa -->
                <div class="modal fade" id="empresaModal" tabindex="-1" role="dialog" aria-labelledby="empresaModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="empresaModalLabel"><i class="fas fa-building"></i> Crear Empresa</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="empresaFormulario" enctype="multipart/form-data">
                                    <!-- Pestañas de Navegación -->
                                    <ul class="nav nav-tabs" id="empresaTabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="info-personal-tab" data-toggle="tab" href="#info-personal" role="tab" aria-controls="info-personal" aria-selected="true">
                                                <i class="fas fa-building"></i> Empresa
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="fechas-tab" data-toggle="tab" href="#fechas" role="tab" aria-controls="fechas" aria-selected="false">
                                                <i class="fas fa-calendar"></i> Contrato
                                            </a>
                                        </li>
                                    </ul>

                                    <!-- Contenido de las Pestañas -->
                                    <div class="tab-content" id="empresaTabsContent">
                                        <!-- Pestaña 1: Información Personal -->
                                        <div class="tab-pane fade show active" id="info-personal" role="tabpanel" aria-labelledby="info-personal-tab">
                                            <br>
                                            <fieldset>
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empDocumentoTipo">Tipo de Documento:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el tipo de documento."></i>
                                                                </label>
                                                                <select class="form-control" id="empDocumentoTipo" name="empDocumentoTipo" required>
                                                                    <option value="" disabled selected>Seleccione una opción</option>
                                                                    <option value="DNI">DNI</option>
                                                                    <option value="RUC">RUC</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empDocumentoNumero">Número de Documento:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el numero de documento."></i>
                                                                </label>
                                                                <input type="number" class="form-control" id="empDocumentoNumero" name="empDocumentoNumero" placeholder="Ingresar numero de documento" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empRazonSocial">Razón Social:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la razon social."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="empRazonSocial" name="empRazonSocial" placeholder="Ingresar la razon social" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empResponsable">Responsable:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el responsable."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="empResponsable" name="empResponsable" placeholder="Ingresar responsable" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empTelefono">Teléfono:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el telefono."></i>
                                                                </label>
                                                                <input type="number" class="form-control" id="empTelefono" name="empTelefono" placeholder="Ingresar telefono required" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empEmail">Email:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el email."></i>
                                                                </label>
                                                                <input type="email" class="form-control" id="empEmail" name="empEmail" placeholder="Ingresar email" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empNombreComercial">Nombre Comercial:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre comercial."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="empNombreComercial" name="empNombreComercial" placeholder="Ingresarel nombre comercial" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empImageLogo">Logo de la empresa</label>
                                                                <input type="file" id="empImageLogo" name="empImageLogo" accept="image/png, image/jpeg">
                                                                <img id="logoPreview" src="" alt="Logo de la empresa" style="display: none; width: 100px; height: 100px; margin-top: 10px;">
                                                                <button type="button" id="cargarLogo" class="btn btn-sm btn-primary" style="display: none; margin-top: 10px;">Cargar nuevo logo</button>
                                                                <input type="hidden" id="empImageBase64" name="empImageBase64">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>

                                        <!-- Pestaña 2: Fechas -->
                                        <div class="tab-pane fade" id="fechas" role="tabpanel" aria-labelledby="fechas-tab-edit">
                                            <br>
                                            <fieldset>
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empFechaContratoInicio">Fecha de Contrato Inicio:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la fecha inicio."></i>
                                                                </label>
                                                                <input type="date" class="form-control" id="empFechaContratoInicio" name="empFechaContratoInicio" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="empFechaContratoFin">Fecha de Contrato Fin:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la fecha fin."></i>
                                                                </label>
                                                                <input type="date" class="form-control" id="empFechaContratoFin" name="empFechaContratoFin" required>
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
                                        <button type="submit" class="btn btn-primary" id="guardarEmpresa">Guardar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- MODAL PARA PODER EDITAR -->
                <div class="modal fade" id="editarEmpresaModal" tabindex="-1" role="dialog" aria-labelledby="editarEmpresaModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editarEmpresaModalLabel"><i class="fas fa-building"></i> Editar Empresa</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="editarEmpresaFormulario">
                                    <input id="editarEmpresaId" type="hidden" value="${empresaSession.empId}"/>

                                    <!-- Pestañas -->
                                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="personal-tab" data-toggle="tab" href="#personal" role="tab" aria-controls="personal" aria-selected="true">
                                                <i class="fas fa-building"></i> Empresa
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="fechas-tab-edit" data-toggle="tab" href="#fechas-edit" role="tab" aria-controls="fechas-edit" aria-selected="false">
                                                <i class="fas fa-calendar"></i> Contrato
                                            </a>
                                        </li>
                                    </ul>
                                    <br>
                                    <!-- Contenido de las Pestañas -->
                                    <div class="tab-content" id="myTabContent">
                                        <div class="tab-pane fade show active" id="personal" role="tabpanel" aria-labelledby="personal-tab">
                                            <fieldset>
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpDocumentoTipo">Tipo de Documento:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el tipo de documento."></i>
                                                                </label>
                                                                <select class="form-control" id="editarEmpDocumentoTipo" name="editarEmpDocumentoTipo" required>
                                                                    <option value="" disabled selected>Seleccione una opción</option>
                                                                    <option value="DNI">DNI</option>
                                                                    <option value="RUC">RUC</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpDocumentoNumero">Número de Documento:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el numero de documnento."></i>
                                                                </label>
                                                                <input type="number" class="form-control" id="editarEmpDocumentoNumero" name="editarEmpDocumentoNumero" placeholder="Ingresar numero de documento" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpRazonSocial">Razón Social:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la razon soscial."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="editarEmpRazonSocial" name="editarEmpRazonSocial" placeholder="Ingresar la razon social" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpResponsable">Responsable:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre del responsable de la empresa."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="editarEmpResponsable" name="editarEmpResponsable" placeholder="Ingresar nombre del responsable" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpTelefono">Teléfono:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el telefono de la empresa."></i>
                                                                </label>
                                                                <input type="number" class="form-control" id="editarEmpTelefono" name="editarEmpTelefono" placeholder="Ingresar telefono" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpEmail">Email:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el email de la empresa."></i>
                                                                </label>
                                                                <input type="email" class="form-control" id="editarEmpEmail" name="editarEmpEmail" placeholder="Ingresar email" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpNombreComercial">Nombre Comercial:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre comercial de la empresa."></i>
                                                                </label>
                                                                <input type="text" class="form-control" id="editarEmpNombreComercial" name="editarEmpNombreComercial" placeholder="Ingresar nombre comercial" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpImageLogo">Imagen del Logo:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el logo de la empresa."></i>
                                                                </label>
                                                                <input type="file" class="form-control-file" id="editarEmpImageLogo" name="editarEmpImageLogo" accept="image/png, image/jpeg">
                                                                <img id="logoPreviewEdit" src="" alt="Logo de la empresa" style="display: none; width: 100px; height: 100px; margin-top: 10px;">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>

                                        <div class="tab-pane fade" id="fechas-edit" role="tabpanel" aria-labelledby="fechas-tab-edit">
                                            <fieldset>
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpFechaContratoInicio">Fecha de Contrato Inicio:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la fecha de inicio."></i>
                                                                </label>
                                                                <input type="date" class="form-control" id="editarEmpFechaContratoInicio" name="editarEmpFechaContratoInicio" placeholder="Ingresar fecha de inicio"
                                                                       required>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group">
                                                                <label for="editarEmpFechaContratoFin">Fecha de Contrato Fin:
                                                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la fecha fin."></i>
                                                                </label>
                                                                <input type="date" class="form-control" id="editarEmpFechaContratoFin" name="editarEmpFechaContratoFin" placeholder="Ingresar fecha fin" required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>
                                    </div>

                                    <!-- Botones -->
                                    <div class="text-center mt-4">
                                        <button type="submit" class="btn btn-primary">Guardar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- VISTA PARA PODER VISUALIZAR DESDE NIVEL ADMINISTRADOR -->
                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                    <div class="container-fluid">
                        <div class="card-body p-2">
                            <div class="card-body p-2">
                                <div class="table-responsive">
                                    <table id="empresaTable" class="table table-sm table-bordered table-striped">
                                        <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">NOMBRE COMERCIAL</th>
                                            <th scope="col">RESPONSABLE</th>
                                            <th scope="col">FECHA DE CONTRATO</th>
                                            <th scope="col">ESTADO</th>
                                            <th scope="col">ACCIONES</th>
                                        </tr>
                                        </thead>
                                        <tbody id="empresaBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <!-- VISTA PARA PODER VISUALIZAR DESDE NIVEL PROPIETARIO -->
                <c:if test="${sessionScope.usuSessionNivel == 'PROPIETARIO'}">
                    <div class="container mt-4" style="max-width: 900px;">
                        <div class="container shadow-sm bg-white rounded p-4">
                            <div class="row align-items-center mb-4">
                                <div>
                                    <c:choose>
                                        <c:when test="${not empty empresaSession.empImagenLogo}">
                                            <img id="logoEmpresa" src="${empresaSession.empImagenLogo}" class="rounded-circle shadow" width="100" height="100" style="width: 100px; height: 100px; margin-right: 5px;" alt="Logo de la empresa">
                                        </c:when>
                                        <c:otherwise>
                                            <canvas id="initialsCanvas" class="rounded-circle shadow" width="100" height="100" style="width: 100px; height: 100px; margin-right: 5px;"></canvas>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col text-center" style="margin-left: 10px;">
                                    <h3 class="font-weight-bold mt-3 text-primary" id="NombreComercial"></h3>
                                    <p class="text-center">Número de Documento: <span class="text-primary" id="NumeroDocumento"></span></p>
                                </div>
                            </div>

                            <!-- Separador -->
                            <hr class="my-4" style="border-top: 2px solid #007bff;">
                            <div class="container rounded bg-white p-4">
                                <div class="row">
                                    <!-- Navegación en Pestañas -->
                                    <div class="col-lg-12">
                                        <ul class="nav nav-tabs" id="editarEmpresaTabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link active" id="datosEmpresa-tab" data-toggle="tab" href="#datosEmpresa" role="tab" aria-controls="datosEmpresa" aria-selected="true">
                                                    <i class="fas fa-building mr-2"></i>Empresa
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" id="empresaSucursales-tab" data-toggle="tab" href="#empresaSucursales" role="tab" aria-controls="empresaSucursales" aria-selected="false">
                                                    <i class="fas fa-map-marker-alt mr-2"></i>Sucursales
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                                <div class="tab-content mt-4" id="editarEmpresaTabsContent">
                                    <!-- Datos de la Empresa -->
                                    <div class="tab-pane fade show active" id="datosEmpresa" role="tabpanel" aria-labelledby="datosEmpresa-tab">
                                        <form id="empresaForm" class="form-neon form" action="#" method="POST" data-form="update" autocomplete="off">
                                            <input type="hidden" name="empresa_id_up" value="${empresaSession.empId}">
                                            <input type="hidden" name="modulo_empresa" value="actualizar">
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="empresaNombre">Nombre Comercial</label>
                                                    <input type="text" class="form-control border-secondary" id="empresaNombre"  maxlength="75">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="empresaRazonSocial">Razón Social</label>
                                                    <input type="text" class="form-control border-secondary" id="empresaRazonSocial" maxlength="75">
                                                </div>
                                            </div>
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="empresaTipoDocumento">Tipo de Documento</label>
                                                    <input type="text" class="form-control border-secondary" id="empresaTipoDocumento"  maxlength="30">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="empresaNumeroDocumento">Número de Documento</label>
                                                    <input type="text" class="form-control border-secondary" id="empresaNumeroDocumento"  maxlength="30">
                                                </div>
                                            </div>
                                            <hr class="my-4" style="border-top: 2px solid #007bff;">
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="empresaTelefono">Teléfono</label>
                                                    <input type="text" class="form-control border-secondary" id="empresaTelefono" value="">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="empresaEmail">Correo Electrónico</label>
                                                    <input type="email" class="form-control border-secondary" id="empresaEmail" value="" maxlength="50">
                                                </div>
                                            </div>
                                            <div class="text-center mt-3">
                                                <button type="submit" class="btn btn-primary btn-lg px-4 py-2" id="botonActualizar">Actualizar</button>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="tab-pane fade" id="empresaSucursales" role="tabpanel" aria-labelledby="empresaSucursales-tab">
                                        <div class="container mt-3">
                                            <h5 class="font-weight-bold mb-3">Lista de Sucursales</h5>
                                            <!-- Tabla para mostrar la información de las sucursales -->
                                            <table class="table table-bordered">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th>Nombre Comercial</th>
                                                    <th>Teléfono</th>
                                                    <th>Correo Electrónico</th>
                                                    <th>Activo</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <!-- Aquí se mostrarán las sucursales -->
                                                <c:forEach var="sucursal" items="">
                                                    <tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <div class="text-center">
                                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#agregarSucursalModal">
                                                    Agregar Sucursal
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:if>

            </div>
        </div>
    </div>
</div>


<!-- Modal crear metodo de pago ADMINISTRADOR / PROPIETARIO -->

<!-- Modal para mostrar los métodos de pago -->
<div class="modal fade" id="modalRegistrarMetodoPago" tabindex="-1" role="dialog" aria-labelledby="modalRegistrarMetodoPagoLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalRegistrarMetodoPagos">Métodos de Pago</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-hover" id="tablaMetodoPago">
                    <thead class="thead-light">
                    <tr>
                        <th>Tipo de Pago</th>
                        <th>QR</th>
                        <th>Nombre de Cuenta</th>
                        <th>Número de Cuenta</th>
                        <th>Detalle</th>
                        <th>Accion</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button id="btnAgregarMetodoPago" class="btn btn-primary" data-emp-id="{{empId}}">Agregar Método de Pago</button>
            </div>
        </div>
    </div>
</div>

<!-- MODAL DE REGISTRO PARA UN MÉTODO DE PAGO -->
<div class="modal fade" id="modalRegistrarMetodosPago" tabindex="-1" aria-labelledby="modalRegistrarMetodosPagoLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalRegistrarMetodosPagoLabel">Seleccionar Método de Pago</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="formRegistrarMetodosPago">
                    <table id="tablaMetodosPago" class="table table-bordered table-responsive">
                        <thead class="thead-light">
                        <tr>
                            <th style="width: 15%;">Método de Pago</th>
                            <th style="width: 13%;">QR</th>
                            <th style="width: 15%;">Número de Cuenta</th>
                            <th style="width: 15%;">Nombre de la Cuenta</th>
                            <th style="width: 20%;">Detalle de la Cuenta</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <input type="checkbox" class="metodo-pago-checkbox" data-tipo="Yape">
                                Yape
                            </td>
                            <td>
                                <div id="qrYapeContainer">
                                    <img src="ruta-al-qr-yape" alt="QR Yape" id="qrYape" style="width: 50px; height: 50px; display: none;">
                                    <div id="cargarQrYape">
                                        <input type="file" class="form-control-file" id="metQrYape" accept="image/*" disabled>
                                        <label for="metQrYape" class="form-label" style="cursor: pointer;">Cargar QR</label>
                                    </div>
                                    <div id="editQrYape" style="display:none;">
                                        <button type="button" class="btn btn-secondary mt-2" id="editarQrYape">Editar QR</button>
                                    </div>
                                </div>
                            </td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoCuentaYape" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoNombreYape" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoDetalleYape" disabled></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" class="metodo-pago-checkbox" data-tipo="Plin">
                                Plin
                            </td>
                            <td>
                                <div id="qrPlinContainer">
                                    <img src="ruta-al-qr-plin" alt="QR Plin" id="qrPlin" style="width: 50px; height: 50px; display: none;">
                                    <div id="cargarQrPlin">
                                        <input type="file" class="form-control-file" id="metQrPlin" accept="image/*" disabled>
                                        <label for="metQrPlin" class="form-label" style="cursor: pointer;">Cargar QR</label>
                                    </div>
                                    <div id="editQrPlin" style="display:none;">
                                        <button type="button" class="btn btn-secondary mt-2" id="editarQrPlin">Editar QR</button>
                                    </div>
                                </div>
                            </td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoCuentaPlin" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoNombrePlin" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoDetallePlin" disabled></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" class="metodo-pago-checkbox" data-tipo="Tarjeta">
                                Tarjeta
                            </td>
                            <td>
                                <div>No Aplica</div>
                            </td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoCuentaTarjeta" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoNombreTarjeta" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoDetalleTarjeta" disabled></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" class="metodo-pago-checkbox" data-tipo="Efectivo">
                                Efectivo
                            </td>
                            <td>
                                <div>No Aplica</div>
                            </td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoCuentaEfectivo" value="No Aplica" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoNombreEfectivo" value="No Aplica" disabled></td>
                            <td><input type="text" class="form-control form-control-lg w-100" id="metPagoDetalleEfectivo" disabled></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-emp-id="{{empId}}" id="guardarMetPago">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal de confirmación vista de propietario -->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirmar Actualización</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ¿Estás seguro de que deseas actualizar los datos de la empresa?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="cancelUpdate" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="confirmUpdate">Confirmar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal de eliminacion para empresa -->
<div id="confirmDeleteModalEmpresa" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar eliminación</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas eliminar ?</p>
                <input type="hidden" id="deleteEmpresaId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="eliminarEmpresa">Eliminar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal de eliminacion para sucursal -->
<div id="confirmDeleteModalSucursal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModal">Confirmar eliminación</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas eliminar esta Sucursal ?</p>
                <input type="hidden" id="deleteSucursalId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="eliminarSucursal">Eliminar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal de eliminacion para un metodoPago -->
<div id="confirmDeleteModalMetodoPago" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteMetodoPagoModal">Confirmar eliminación</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas eliminar este Metodo de Pago ?</p>
                <input type="hidden" id="deleteMetodoPagoId">
                <input type="hidden" id="deleteEmpId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="eliminarMetodoPago">Eliminar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal de Sucursales -->
<div class="modal fade" id="sucursalesModal" role="dialog" tabindex="-1" aria-labelledby="sucursalesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="sucursalesModalLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Nombre Comercial</th>
                        <th>Teléfono</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody id="sucursalesBody">
                    </tbody>
                </table>
                <div id="mensajeSinSucursales" class="text-center" style="display: none;">
                    <p>No hay ninguna sucursal registrada para esta empresa.</p>
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnAgregarSucursal" data-emp-id="{{empId}}" class="btn btn-primary">Agregar Sucursal</button>
            </div>
        </div>
    </div>
</div>

<!-- MODAL DE REGISTRO PARA UNA SUCURSAL -->
<div class="modal fade" id="sucursalModal" tabindex="-1" aria-labelledby="sucursalModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="sucursalModalLabel">Nueva sucursal</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="sucursalFormulario">
                    <ul class="nav nav-tabs" id="SucursalTabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="sucursal-tab" data-toggle="tab" href="#sucursal" role="tab" aria-controls="sucursal" aria-selected="true">
                                <i class="fas fa-pencil-alt" style="margin-right: 5px;"></i>
                                Informacion de Sucursal
                            </a>
                        </li>
                    </ul>
                    <br>
                    <div class="mb-3">
                        <label for="sucNombre" class="form-label">Nombre Comercial</label>
                        <input type="text" class="form-control" id="sucNombre" placeholder="Nombre de la sucursal" required>
                    </div>
                    <div class="mb-3">
                        <label for="sucTelefono" class="form-label">Número Telefónico</label>
                        <input type="number" class="form-control" id="sucTelefono" placeholder="Número de teléfono" required>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="guardarSucursal">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- MODAL PARA PODER EDITAR UNA SUCURSAL -->
<div class="modal fade" id="editsucursalModal" tabindex="-1" role="dialog" aria-labelledby="editsucursalModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editsucursalModalLabel"><i class="fas fa-building"></i> Editar Sucursal</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editFormularioSucursal" enctype="multipart/form-data">
                    <ul class="nav nav-tabs" id="editarClienteTabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="sucursalEdit-tab" data-toggle="tab" href="#sucursal-edit" role="tab" aria-controls="sucursal-edit" aria-selected="true">
                                <i class="fas fa-pencil-alt" style="margin-right: 5px;"></i>
                                Informacion de Sucursal
                            </a>
                        </li>
                    </ul>
                    <br>
                    <input id="editSucursalId" type="hidden"/>
                    <fieldset>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 col-md-6">
                                    <div class="form-group">
                                        <label for="editSucursalNombreComercial">Nombre Comercial:</label>
                                        <input type="text" class="form-control" id="editSucursalNombreComercial" name="editempNombreComercial" required>
                                    </div>
                                </div>
                                <div class="col-12 col-md-6">
                                    <div class="form-group">
                                        <label for="editSucursalTelefono">Teléfono:</label>
                                        <input type="tel" class="form-control" id="editSucursalTelefono" name="editempTelefono" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<style>
    .custom-button:focus {
        outline: none;
    }

    .table {
        background-color: white;
    }

    .switch {
        position: relative;
        display: inline-block;
        width: 40px;
        height: 20px;
    }

    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        transition: .4s;
        border-radius: 20px;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 16px;
        width: 16px;
        left: 2px;
        bottom: 2px;
        background-color: white;
        transition: .4s;
        border-radius: 50%;
    }

    input:checked + .slider {
        background-color: #673ab7;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px #673ab7;
    }

    input:checked + .slider:before {
        transform: translateX(20px);
    }

    @media (max-width: 768px) {
        .card {
            max-width: 100%;
            margin: 0;
        }
    }
</style>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<script src="/static/web/dist/js/funciones/empresa.js"></script>
<%@ include file="includes/footer.jspf" %>
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>