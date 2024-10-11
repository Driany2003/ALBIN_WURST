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
                <!-- Modal -->
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
                                    <!-- Información Personal -->
                                    <fieldset>
                                        <legend><i class="fas fa-user"></i> Información Personal</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empDocumentoTipo">Tipo de Documento:</label>
                                                        <select class="form-control" id="empDocumentoTipo" name="empDocumentoTipo" required>
                                                            <option value="" disabled selected>Seleccione una opción</option>
                                                            <option value="DNI">DNI</option>
                                                            <option value="RUC">RUC</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empDocumentoNumero">Número de Documento:</label>
                                                        <input type="number" class="form-control" id="empDocumentoNumero" name="empDocumentoNumero" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empRazonSocial">Razón Social:</label>
                                                        <input type="text" class="form-control" id="empRazonSocial" name="empRazonSocial" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empResponsable">Responsable:</label>
                                                        <input type="text" class="form-control" id="empResponsable" name="empResponsable" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empTelefono">Teléfono:</label>
                                                        <input type="number" class="form-control" id="empTelefono" name="empTelefono" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empEmail">Email:</label>
                                                        <input type="email" class="form-control" id="empEmail" name="empEmail" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empNombreComercial">Nombre Comercial:</label>
                                                        <input type="text" class="form-control" id="empNombreComercial" name="empNombreComercial">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empImageLogo">Imagen del Logo:</label>
                                                        <input type="file" class="form-control-file" id="empImageLogo" name="empImageLogo" accept="image/*">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <!-- Fechas -->
                                    <fieldset>
                                        <legend><i class="fas fa-calendar"></i> Fechas</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empFechaContratoInicio">Fecha de Contrato Inicio:</label>
                                                        <input type="date" class="form-control" id="empFechaContratoInicio" name="empFechaContratoInicio">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="empFechaContratoFin">Fecha de Contrato Fin:</label>
                                                        <input type="date" class="form-control" id="empFechaContratoFin" name="empFechaContratoFin">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <!-- Códigos QR -->
                                    <fieldset>
                                        <legend><i class="fas fa-qrcode"></i> Códigos QR</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="empQrYape">Código QR Yape:</label>
                                                        <input type="file" class="form-control-file" id="empQrYape" name="empQrYape" accept="image/*">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="empQrPlin">Código QR Plin:</label>
                                                        <input type="file" class="form-control-file" id="empQrPlin" name="empQrPlin" accept="image/*">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="empQrPagos">Código QR Pagos:</label>
                                                        <input type="file" class="form-control-file" id="empQrPagos" name="empQrPagos" accept="image/*">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <!-- Botones -->
                                    <div class="text-center mt-4">
                                        <button type="reset" class="btn btn-secondary"><i class="fas fa-paint-roller"></i> Limpiar</button>
                                        <button type="submit" class="btn btn-primary" id="guardarEmpresa"><i class="far fa-save"></i> Guardar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- MODAL PARA PODER EDITAR -->
                <div class="modal fade" id="editempresaModal" tabindex="-1" role="dialog" aria-labelledby="editempresaModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editempresaModalLabel"><i class="fas fa-building"></i> Editar Empresa</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="editEmpresaFormulario">
                                    <input id="editempresaId" type="hidden" value="${empresaSession.empId}"/>
                                    <fieldset>
                                        <legend><i class="fas fa-user"></i> Información Personal</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempDocumentoTipo">Tipo de Documento:</label>
                                                        <select class="form-control" id="editempDocumentoTipo" name="editempDocumentoTipo" required>
                                                            <option value="" disabled selected>Seleccione una opción</option>
                                                            <option value="DNI">DNI</option>
                                                            <option value="RUC">RUC</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempDocumentoNumero">Número de Documento:</label>
                                                        <input type="number" class="form-control" id="editempDocumentoNumero" name="editempDocumentoNumero" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempRazonSocial">Razón Social:</label>
                                                        <input type="text" class="form-control" id="editempRazonSocial" name="editempRazonSocial" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempResponsable">Responsable:</label>
                                                        <input type="text" class="form-control" id="editempResponsable" name="editempResponsable" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempTelefono">Teléfono:</label>
                                                        <input type="number" class="form-control" id="editempTelefono" name="editempTelefono" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempEmail">Email:</label>
                                                        <input type="email" class="form-control" id="editempEmail" name="editempEmail" required>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempNombreComercial">Nombre Comercial:</label>
                                                        <input type="text" class="form-control" id="editempNombreComercial" name="editempNombreComercial">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempImageLogo">Imagen del Logo:</label>
                                                        <input type="file" class="form-control-file" id="editempImageLogo" name="editempImageLogo" accept="image/*">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <!-- Fechas -->
                                    <fieldset>
                                        <legend><i class="fas fa-calendar"></i> Fechas</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempFechaContratoInicio">Fecha de Contrato Inicio:</label>
                                                        <input type="date" class="form-control" id="editempFechaContratoInicio" name="editempFechaContratoInicio">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group">
                                                        <label for="editempFechaContratoFin">Fecha de Contrato Fin:</label>
                                                        <input type="date" class="form-control" id="editempFechaContratoFin" name="editempFechaContratoFin">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <!-- Códigos QR -->
                                    <fieldset>
                                        <legend><i class="fas fa-qrcode"></i> Códigos QR</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="editempQrYape">Código QR Yape:</label>
                                                        <input type="file" class="form-control-file" id="editempQrYape" name="editempQrYape" accept="image/*">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="editempQrPlin">Código QR Plin:</label>
                                                        <input type="file" class="form-control-file" id="editempQrPlin" name="editempQrPlin" accept="image/*">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-4">
                                                    <div class="form-group">
                                                        <label for="editempQrPagos">Código QR Pagos:</label>
                                                        <input type="file" class="form-control-file" id="editempQrPagos" name="editempQrPagos" accept="image/*">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <!-- Botones -->
                                    <div class="text-center mt-4">
                                        <button type="submit" class="btn btn-primary"><i class="far fa-save"></i> Guardar Cambios</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                    <div class="container-fluid">
                        <div class="card-body p-2">
                            <div class="card-body p-2">
                                <div class="table-responsive">
                                    <table id="empresaTable" class="table table-sm table-bordered table-striped">
                                        <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">NOMBRE COMERCIAL</th>
                                            <th scope="col">RESPONSABLES</th>
                                            <th scope="col">FECHA DE CONTRATO INICIO</th>
                                            <th scope="col">FECHA DE CONTRATO FIN</th>
                                            <th scope="col">TELEFONO</th>
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

                <c:if test="${sessionScope.usuSessionNivel == 'PROPIETARIO'}">
                    <div class="container">
                        <div class="card mt-1">
                            <div class="container">
                                <form id="empresaForm" class="form-neon form" action="#" method="POST" data-form="update" autocomplete="off">
                                    <input type="hidden" name="empresa_id_up" value="${empresaSession.empId}">
                                    <input type="hidden" name="modulo_empresa" value="actualizar">
                                    <fieldset class="text-left">
                                        <legend>
                                            <i class="far fa-address-card"></i> &nbsp; Datos de la empresa
                                        </legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-4">
                                                    <figure>
                                                        <img id="empresaLogo" class="img-fluid img-product-info" src="/static/web/assets/images/0_logo/logo_kenpis_img.png" alt="no se encontro la imagen de la empresa"
                                                             height="90px" width="90px">
                                                    </figure>
                                                    <p class="text-center">
                                                        <a href="#" class="btn btn-outline-info" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="Gestionar logo de empresa" title="">
                                                            <i class="far fa-image"></i> &nbsp; Cambiar logo o imagen
                                                        </a>
                                                    </p>
                                                </div>
                                                <div class="col-12 col-md-8">
                                                    <div class="row">
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group bmd-form-group is-filled">
                                                                <label for="empresaTipoDocumento" class="bmd-label-floating">Tipo de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                                <input type="text" id="empresaTipoDocumento" class="form-control" name="empresa_numero_documento_up" maxlength="30">
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group bmd-form-group is-filled">
                                                                <label for="empresaNumeroDocumento" class="bmd-label-floating">Número de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                                <input type="text" id="empresaNumeroDocumento" class="form-control" name="empresa_numero_documento_up" maxlength="30">
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group bmd-form-group is-filled">
                                                                <label for="empresaNombre" class="bmd-label-floating">Nombre Comercial &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                                <input type="text" id="empresaNombre" class="form-control" name="empresa_nombre_up" maxlength="75">
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6">
                                                            <div class="form-group bmd-form-group is-filled">
                                                                <label for="empresaRazonSocial" class="bmd-label-floating">Razon Social &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                                <input type="text" id="empresaRazonSocial" class="form-control" name="empresa_razon_social_up" maxlength="75">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <br><br><br>
                                    <fieldset class="text-left">
                                        <legend>
                                            <i class="fas fa-phone-volume"></i> &nbsp; Información de contacto
                                        </legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="empresaTelefono">Teléfono:</label>
                                                        <input type="text" id="empresaTelefono" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="empresaEmail" class="bmd-label-floating">Email</label>
                                                        <input type="email" id="empresaEmail" class="form-control" name="empresa_email_up" maxlength="50">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <p class="text-center" style="margin-top: 40px;">
                                        <button type="submit" id="botonActualizar" class="btn btn-raised btn-success btn-sm" disabled><i class="fas fa-sync"></i> &nbsp; ACTUALIZAR</button>
                                    </p>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>

                <!-- Modal de Confirmación -->
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
                                ¿Estás seguro de que deseas actualizar los datos de la empresa? Se guardarán los cambios realizados.
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" id="cancelUpdate" data-dismiss="modal">Cancelar</button>
                                <button type="button" class="btn btn-primary" id="confirmUpdate">Actualizar</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Modal de Sucursales -->
<div class="modal fade" id="sucursalesModal" tabindex="-1" aria-labelledby="sucursalesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
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

                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <form id="sucursalFormulario">
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
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-primary" id="guardarSucursal">Guardar Sucursal</button>
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
                <form id="editFormularioSucursal">
                    <input id="editSucursalId" type="hidden"/>
                    <fieldset>
                        <legend><i class="fas fa-pencil-alt"></i> Información de Sucursal</legend>
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
                        <button type="submit" class="btn btn-primary"><i class="far fa-save"></i> Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<style>
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