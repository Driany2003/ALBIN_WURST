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
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">USUARIO <span class="label label-rounded label-info">${empresaSession.empNombreComercial}</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-2">
                            <div class="d-flex justify-content-end mb-2">
                                <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#crearUsuarioModal">Ingresar Usuario</button>
                                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                                <input type="hidden" id="usuarioNivel" value="${sessionScope.usuSessionNivel}"/>
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <div class="input-group" style="width: 300px;">
                                    <input type="text" class="form-control" id="tableFilter" placeholder="Buscar Usuario...">
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
                                <table id="usuarioTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">TIPO/N° DOCUMENTO</th>
                                        <th scope="col">NOMBRE</th>
                                        <th scope="col">CARGO</th>
                                        <th scope="col">USUARIO</th>
                                        <th scope="col">EMPRESA</th>
                                        <th scope="col">ACCION</th>
                                    </tr>
                                    </thead>
                                    <tbody id="usuariosBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Crear Usuario -->
    <div class="modal fade" id="crearUsuarioModal" tabindex="-1" aria-labelledby="crearUsuarioModalLabel" aria-hidden="true">
        <input id="empresaIdModal" type="hidden" value="${empresaSession.empId}"/>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearUsuarioModalLabel">Crear Usuario</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="usuarioForm" enctype="multipart/form-data">
                        <!-- Navegación en Pestañas -->
                        <ul class="nav nav-tabs" id="crearUsuarioTabs" role="tablist">
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <li class="nav-item">
                                    <a class="nav-link active" id="empresa-tab" data-toggle="tab" href="#empresa" role="tab" aria-controls="empresa" aria-selected="true">
                                        <i class="fas fa-building" style="margin-right: 5px;"></i>
                                        Empresa
                                    </a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" id="documento-tab" data-toggle="tab" href="#documento" role="tab" aria-controls="documento" aria-selected="false">
                                    <i class="fas fa-id-card" style="margin-right: 5px;"></i>
                                    Documento
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${sessionScope.usuSessionNivel != 'ADMINISTRADOR' ? 'active' : ''}" id="personal-tab" data-toggle="tab" href="#personal" role="tab" aria-controls="personal"
                                   aria-selected="false">
                                    <i class="fas fa-id-card" style="margin-right: 5px;"></i>
                                    Información Personal
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" id="cuenta-tab" data-toggle="tab" href="#cuenta" role="tab" aria-controls="cuenta" aria-selected="false">
                                    <i class="fas fa-user-lock" style="margin-right: 5px;"></i>
                                    Cuenta
                                </a>
                            </li>
                        </ul>

                        <!-- Contenido de las Pestañas -->
                        <div class="tab-content" id="crearUsuarioTabsContent">
                            <!-- Tab 1: Información de la Empresa (Solo para Admins) -->
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <div class="tab-pane fade show active" id="empresa" role="tabpanel" aria-labelledby="empresa-tab">
                                    <br>
                                    <fieldset>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="usuario_empresa" class="bmd-label-floating">Seleccione la empresa
                                                            <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione la empresa a la que el usuario estará asignado."></i>
                                                        </label>
                                                        <select class="form-control" name="usuario_empresa_reg" id="usuario_empresa" required>
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
                            <div class="tab-pane fade ${sessionScope.usuSessionNivel != 'ADMINISTRADOR' ? 'show active' : ''}" id="personal" role="tabpanel" aria-labelledby="personal-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_nombre" class="bmd-label-floating">Nombres
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_nombre_reg" id="usuario_nombre" maxlength="35" placeholder="Ingresar nombre del usuario" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_apellido_paterno" class="bmd-label-floating">Apellido Paterno
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el apellido paterno del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_apellido_paterno" id="usuario_apellido_paterno" maxlength="35" placeholder="Ingresar apellido paterno" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_apellido_materno" class="bmd-label-floating">Apellido Materno
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el apellido materno del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_apellido_materno" id="usuario_apellido_materno" maxlength="35" placeholder="Ingresar apellido materno" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_telefono" class="bmd-label-floating">Teléfono
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de teléfono del usuario."></i>
                                                    </label>
                                                    <input type="number" class="form-control" name="usuario_telefono_reg" id="usuario_telefono" maxlength="9" placeholder="Ingresar numero telefonico" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_cargo" class="bmd-label-floating">Cargo
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el cargo o posición del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_cargo" id="usuario_cargo" maxlength="20" placeholder="Ingresar el cargo" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_genero" class="bmd-label-floating">Género
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione el género del usuario."></i>
                                                    </label>
                                                    <select name="usuario_genero" id="usuario_genero" class="form-control" required>
                                                        <option value="">Seleccione</option>
                                                        <option value="M">Masculino</option>
                                                        <option value="F">Femenino</option>
                                                        <option value="N">Prefiero no decirlo</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>

                            <!-- Tab 3: Información de Documento -->
                            <div class="tab-pane fade" id="documento" role="tabpanel" aria-labelledby="documento-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_tipo_documento" class="bmd-label-floating">Tipo de Documento
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione el tipo de documento del usuario."></i>
                                                    </label>
                                                    <select class="form-control" name="usuario_tipo_documento_reg" id="usuario_tipo_documento" required>
                                                        <option value="" selected="">Seleccione una opción</option>
                                                        <option value="DNI">DNI</option>
                                                        <option value="Cedula">Cédula</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_numero_documento" class="bmd-label-floating">Número de Documento
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de documento del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_numero_documento_reg" id="usuario_numero_documento" maxlength="15" placeholder="Ingresar numero del documento"
                                                           required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>

                            <!-- Tab 4: Información de la Cuenta -->
                            <div class="tab-pane fade" id="cuenta" role="tabpanel" aria-labelledby="cuenta-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">

                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="username_usuario" class="bmd-label-floating">Nombre de Usuario
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese un nombre de usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="username_usuario_reg" id="username_usuario" maxlength="25" placeholder="Ingresar nombre de usuario" required>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_correo" class="bmd-label-floating">Correo Electrónico
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el correo electrónico del usuario."></i>
                                                    </label>
                                                    <input type="email" class="form-control" name="usuario_correo_reg" id="usuario_correo" maxlength="50" placeholder="Ingresar correo electronico" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_clave_1" class="bmd-label-floating">Contraseña <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right"
                                                                                                                          title="Ingrese una contraseña segura."></i>
                                                    </label>
                                                    <input type="password" class="form-control" name="usuario_password_reg" id="usuario_clave_1" maxlength="30" placeholder="Ingresar Contraseña" required>
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
                    <button type="submit" class="btn btn-primary" id="registrarUsuario">Guardar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para editar Usuario -->
    <div class="modal fade" id="editUsuarioModal" tabindex="-1" aria-labelledby="editarUsuarioModalLabel" aria-hidden="true">
        <input id="editEmpresaIdModal" type="hidden" value="${empresaSession.empId}"/>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editarUsuarioModalLabel">Editar Usuario</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editarUsuarioForm" enctype="multipart/form-data">
                        <!-- Navegación en Pestañas -->
                        <ul class="nav nav-tabs" id="editarUsuarioTabs" role="tablist">
                            <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                <li class="nav-item">
                                    <a class="nav-link active" id="empresa-edit-tab" data-toggle="tab" href="#empresa-edit" role="tab" aria-controls="empresa-edit" aria-selected="true">
                                        <i class="fas fa-building" style="margin-right: 5px;"></i>
                                        Empresa
                                    </a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" id="documento-edit-tab" data-toggle="tab" href="#documento-edit" role="tab" aria-controls="documento-edit" aria-selected="false">
                                    <i class="fas fa-id-card" style="margin-right: 5px;"></i>
                                    Documento
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${sessionScope.usuSessionNivel != 'ADMINISTRADOR' ? 'active' : ''}" id="personal-edit-tab" data-toggle="tab" href="#personal-edit" role="tab"
                                   aria-controls="personal-edit"
                                   aria-selected="false">
                                    <i class="fas fa-id-card" style="margin-right: 5px;"></i>
                                    Información Personal
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="cuenta-edit-tab" data-toggle="tab" href="#cuenta-edit" role="tab" aria-controls="cuenta-edit" aria-selected="false">
                                    <i class="fas fa-user-lock" style="margin-right: 5px;"></i>
                                    Cuenta
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
                                                        <label for="usuario_empresa_edit" class="bmd-label-floating">Seleccione la empresa
                                                            <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione la empresa a la que el usuario está asignado."></i>
                                                        </label>
                                                        <select class="form-control" name="usuario_empresa_edit" id="usuario_empresa_edit" required>
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
                            <div class="tab-pane fade ${sessionScope.usuSessionNivel != 'ADMINISTRADOR' ? 'show active' : ''}" id="personal-edit" role="tabpanel" aria-labelledby="personal-edit-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_nombre_edit" class="bmd-label-floating">Nombres
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el nombre del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_nombre_edit" id="usuario_nombre_edit" maxlength="35" placeholder="Ingresar nombre del usuario" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_apellido_paterno_edit" class="bmd-label-floating">Apellido Paterno
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el apellido paterno del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_apellido_paterno_edit" id="usuario_apellido_paterno_edit" maxlength="35" placeholder="Ingresar apellido paterno"
                                                           required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_apellido_materno_edit" class="bmd-label-floating">Apellido Materno
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el apellido materno del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_apellido_materno_edit" id="usuario_apellido_materno_edit" maxlength="35" placeholder="Ingresar apellido materno"
                                                           required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_telefono_edit" class="bmd-label-floating">Teléfono
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de teléfono del usuario."></i>
                                                    </label>
                                                    <input type="number" class="form-control" name="usuario_telefono_edit" id="usuario_telefono_edit" maxlength="9" placeholder="Ingresar número telefónico" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_cargo_edit" class="bmd-label-floating">Cargo
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el cargo o posición del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_cargo_edit" id="usuario_cargo_edit" maxlength="20" placeholder="Ingresar el cargo" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-4">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_genero_edit" class="bmd-label-floating">Género
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione el género del usuario."></i>
                                                    </label>
                                                    <select name="usuario_genero_edit" id="usuario_genero_edit" class="form-control" required>
                                                        <option value="">Seleccione</option>
                                                        <option value="M">Masculino</option>
                                                        <option value="F">Femenino</option>
                                                        <option value="N">Prefiero no decirlo</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>

                            <!-- Tab 3: Información de Documento -->
                            <div class="tab-pane fade" id="documento-edit" role="tabpanel" aria-labelledby="documento-edit-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_tipo_documento_edit" class="bmd-label-floating">Tipo de Documento
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Seleccione el tipo de documento del usuario."></i>
                                                    </label>
                                                    <select class="form-control" name="usuario_tipo_documento_edit" id="usuario_tipo_documento_edit" required>
                                                        <option value="" selected="">Seleccione una opción</option>
                                                        <option value="DNI">DNI</option>
                                                        <option value="Cedula">Cédula</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_numero_documento_edit" class="bmd-label-floating">Número de Documento
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el número de documento del usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="usuario_numero_documento_edit" id="usuario_numero_documento_edit" placeholder="Ingresar número de documento" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>

                            <!-- Tab 4: Cuenta -->
                            <div class="tab-pane fade" id="cuenta-edit" role="tabpanel" aria-labelledby="cuenta-edit-tab">
                                <br>
                                <fieldset>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="username_usuario_edit" class="bmd-label-floating">Nombre de Usuario
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese un nombre de usuario."></i>
                                                    </label>
                                                    <input type="text" class="form-control" name="username_usuario_edit" id="username_usuario_edit" maxlength="25" placeholder="Ingresar nombre de usuario" required>
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group">
                                                    <label for="usuario_email_edit" class="bmd-label-floating">Correo Electrónico
                                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese el correo electrónico del usuario."></i>
                                                    </label>
                                                    <input type="email" class="form-control" name="usuario_email_edit" id="usuario_email_edit" placeholder="Ingresar correo electrónico" required>
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
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de confirmación para eliminar usuario -->
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
                    <p>¿Estás seguro de que deseas eliminar este usuario?</p>
                    <input type="hidden" id="deleteUsuarioId">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-danger" id="eliminarUsuario">Eliminar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Resetear Contraseña -->
    <div class="modal fade" id="resetPasswordModal" tabindex="-1" aria-labelledby="resetPasswordLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="resetPasswordLabel">
                        <i class="fas fa-user-lock" style="margin-right: 5px;"></i>
                        Actualizar Contraseña
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="resetPasswordForm">
                        <ul class="nav nav-tabs" id="resetPassword" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="resetPassword-modal" data-toggle="tab" href="#empresa-edit" role="tab" aria-controls="empresa-edit" aria-selected="true">
                                    <i class="fas fa-key" style="margin-right: 5px;"></i>
                                    Contraseña
                                </a>
                            </li>
                        </ul>
                        <br>
                        <input type="hidden" id="resetUsuarioId">
                        <div class="form-group">
                            <label for="nuevaPassword" class="bmd-label-floating">Nueva Contraseña
                                <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese la nueva contraseña."></i>
                            </label>
                            <input type="password" class="form-control" id="nuevaPassword" placeholder="Introduce la nueva contraseña" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmarPassword" class="bmd-label-floating">Confirmar Contraseña
                                <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Confirmar la contraseña a actualizar."></i>
                            </label>
                            <input type="password" class="form-control" id="confirmarPassword" placeholder="Confirma la nueva contraseña" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" id="confirmarContraseña">Actualizar</button>
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
<script src="/static/web/dist/js/funciones/usuario.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>
<!-- End footer -->
</body>
</html>