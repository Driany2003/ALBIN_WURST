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

    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            .
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
                                <table id="productoTable" class="table table-sm table-bordered table-striped">
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
        <!-- MODAL PARA REGISTRAR USUARIO -->
        <div class="modal fade" id="crearUsuarioModal" tabindex="-1" aria-labelledby="crearUsuarioModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="crearUsuarioModalLabel">Crear Usuario</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="form-neon FormularioAjax" action="#" method="POST" data-form="save" autocomplete="off">
                            <input type="hidden" name="modulo_usuario" value="registrar">
                            <input type="hidden" name="empresa_id" id="empresa_id" value="${empresaSession.empId}"/>

                            <div class="container">
                                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                    <fieldset>
                                        <legend><i class="fas fa-building"></i> &nbsp; Empresa</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <label for="usuario_empresa" class="bmd-label-floating">Seleccione la empresa</label>
                                                        <select class="form-control" name="usuario_empresa_reg" id="usuario_empresa">

                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </c:if>
                            </div>
                            <fieldset>
                                <legend><i class="far fa-address-card"></i> &nbsp; Información personal</legend>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-12 col-md-4">
                                            <div class="form-group bmd-form-group is-filled">
                                                <label for="usuario_tipo_documento" class="bmd-label-floating">Tipo de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <select class="form-control" name="usuario_tipo_documento_reg" id="usuario_tipo_documento">
                                                    <option value="" selected="">Seleccione una opción</option>
                                                    <option value="DNI">DNI</option>
                                                    <option value="Cedula">Cedula</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-4">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_numero_documento" class="bmd-label-floating">Numero de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="number" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="usuario_numero_documento_reg" id="usuario_numero_documento"
                                                       maxlength="8">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-4">
                                            <div class="form-group bmd-form-group is-filled">
                                                <label for="usuario_cargo" class="bmd-label-floating">Cargo &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="text" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="usuario_cargo" id="usuario_cargo" maxlength="20"
                                                       oninput="this.value = this.value.toUpperCase();">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_nombre" class="bmd-label-floating">Nombres &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="usuario_nombre_reg" id="usuario_nombre" maxlength="35">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_apellido_paterno" class="bmd-label-floating">Apellido Paterno &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="usuario_apellido_paterno" id="usuario_apellido_paterno" maxlength="35">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_apellido_materno" class="bmd-label-floating">Apellido Materno&nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="usuario_apellido_materno" id="usuario_apellido_materno" maxlength="35">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_telefono" class="bmd-label-floating">Teléfono</label>
                                                <input type="number" pattern="[0-9()+]{8,20}" class="form-control" name="usuario_telefono_reg" id="usuario_telefono" maxlength="9">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <!-- Género -->
                            <br><br>
                            <fieldset>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-12 col-md-6">
                                            <legend><i class="fas fa-user-friends"></i> &nbsp; Género</legend>
                                            <div class="form-group bmd-form-group is-filled">
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="usuario_genero" value="M" checked=""><span class="bmd-radio"></span>
                                                        <i class="fas fa-male fa-fw"></i> &nbsp; Masculino
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="usuario_genero" value="F"><span class="bmd-radio"></span>
                                                        <i class="fas fa-female fa-fw"></i> &nbsp; Femenino
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <!-- Información de la cuenta -->
                            <br><br>
                            <fieldset>
                                <legend><i class="fas fa-user-lock"></i> &nbsp; Información de la cuenta</legend>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-12 col-md-6">
                                            <div class="form-group bmd-form-group">
                                                <label for="username_usuario" class="bmd-label-floating">Nombre de usuario &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="text" pattern="[a-zA-Z0-9]{4,25}" class="form-control" name="username_usuario_reg" id="username_usuario" maxlength="25">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_clave_1" class="bmd-label-floating">Contraseña &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="password" class="form-control" name="usuario_clave_1_reg" id="usuario_clave_1" pattern="[a-zA-Z0-9$@.-]{7,100}" maxlength="100">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group bmd-form-group">
                                                <label for="usuario_clave_2" class="bmd-label-floating">Repetir contraseña &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <input type="password" class="form-control" name="usuario_clave_2_reg" id="usuario_clave_2" pattern="[a-zA-Z0-9$@.-]{7,100}" maxlength="100">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group bmd-form-group is-filled">
                                                <label for="usuario_estado" class="bmd-label-floating">Estado de la cuenta &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                <select class="form-control" name="usuario_estado_reg" id="usuario_estado">
                                                    <option value="1" selected="">1 - Activa</option>
                                                    <option value="0">2 - Deshabilitada</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <p class="text-center" style="margin-top: 40px;">
                                <button type="submit" class="btn btn-raised btn-info btn-sm" id="registrarUsuario"><i class="far fa-save"></i> &nbsp; GUARDAR</button>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- MODAL PARA EDITAR USUARIO -->
<div class="modal fade" id="editUsuarioModal" tabindex="-1" aria-labelledby="editarUsuarioModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editarUsuarioModalLabel">Editar Usuario</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editarUsuarioForm">
                    <input type="hidden" name="empresa_id" id="edit_empresa_id" value="${empresaSession.empId}"/>
                    <div class="container">
                        <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                            <fieldset>
                                <legend><i class="fas fa-building"></i> &nbsp; Editar Empresa</legend>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-12 col-md-6">
                                            <div class="form-group bmd-form-group is-filled">
                                                <label for="edit_usuario_empresa" class="bmd-label-floating">Seleccione la empresa</label>
                                                <select class="form-control" name="edit_usuario_empresa" id="edit_usuario_empresa">

                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </c:if>
                    </div>
                    <fieldset>
                        <legend><i class="far fa-address-card"></i> &nbsp; Editar Información personal</legend>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 col-md-4">
                                    <div class="form-group bmd-form-group is-filled">
                                        <label for="edit_usuario_tipo_documento" class="bmd-label-floating">Tipo de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <select class="form-control" name="edit_usuario_tipo_documento" id="edit_usuario_tipo_documento">
                                            <option value="" selected="">Seleccione una opción</option>
                                            <option value="DNI">DNI</option>
                                            <option value="Cedula">Cedula</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-12 col-md-4">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_numero_documento" class="bmd-label-floating">Numero de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="number" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="edit_usuario_numero_documento" id="edit_usuario_numero_documento"
                                               maxlength="8">
                                    </div>
                                </div>
                                <div class="col-12 col-md-4">
                                    <div class="form-group bmd-form-group is-filled">
                                        <label for="edit_usuario_cargo" class="bmd-label-floating">Cargo &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="text" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="edit_usuario_cargo" id="edit_usuario_cargo" maxlength="20"
                                               oninput="this.value = this.value.toUpperCase();">
                                    </div>
                                </div>
                                <div class="col-12 col-md-3">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_nombre" class="bmd-label-floating">Nombres &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="edit_usuario_nombre" id="edit_usuario_nombre" maxlength="35">
                                    </div>
                                </div>
                                <div class="col-12 col-md-3">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_apellido_paterno" class="bmd-label-floating">Apellido Paterno &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="edit_usuario_apellido_paterno" id="edit_usuario_apellido_paterno" maxlength="35">
                                    </div>
                                </div>
                                <div class="col-12 col-md-3">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_apellido_materno" class="bmd-label-floating">Apellido Materno&nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="edit_usuario_apellido_materno" id="edit_usuario_apellido_materno" maxlength="35">
                                    </div>
                                </div>
                                <div class="col-12 col-md-3">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_telefono" class="bmd-label-floating">Teléfono</label>
                                        <input type="number" pattern="[0-9()+]{8,20}" class="form-control" name="edit_usuario_telefono" id="edit_usuario_telefono" maxlength="9">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <!-- Género -->
                    <br><br>
                    <fieldset>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 col-md-6">
                                    <legend><i class="fas fa-user-friends"></i> &nbsp; Editar Género</legend>
                                    <div class="form-group bmd-form-group is-filled">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="edit_usuario_genero" value="M" checked=""><span class="bmd-radio"></span>
                                                <i class="fas fa-male fa-fw"></i> &nbsp; Masculino
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="edit_usuario_genero" value="F"><span class="bmd-radio"></span>
                                                <i class="fas fa-female fa-fw"></i> &nbsp; Femenino
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <!-- Información de la cuenta -->
                    <br><br>
                    <fieldset>
                        <legend><i class="fas fa-user-lock"></i> &nbsp; Editar Información de la cuenta</legend>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 col-md-6">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_username_usuario" class="bmd-label-floating">Nombre de usuario &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="text" pattern="[a-zA-Z0-9]{4,25}" class="form-control" name="edit_username_usuario" id="edit_username_usuario" maxlength="25">
                                    </div>
                                </div>
                                <div class="col-12 col-md-6">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_clave_1" class="bmd-label-floating">Contraseña &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="password" class="form-control" name="edit_usuario_clave_1" id="edit_usuario_clave_1" pattern="[a-zA-Z0-9$@.-]{7,100}" maxlength="100">
                                    </div>
                                </div>
                                <div class="col-12 col-md-6">
                                    <div class="form-group bmd-form-group">
                                        <label for="edit_usuario_clave_2" class="bmd-label-floating">Repetir contraseña &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <input type="password" class="form-control" name="edit_usuario_clave_2" id="edit_usuario_clave_2" pattern="[a-zA-Z0-9$@.-]{7,100}" maxlength="100">
                                    </div>
                                </div>
                                <div class="col-12 col-md-6">
                                    <div class="form-group bmd-form-group is-filled">
                                        <label for="edit_usuario_estado" class="bmd-label-floating">Estado de la cuenta &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                        <select class="form-control" name="edit_usuario_estado" id="edit_usuario_estado">
                                            <option value="1" selected="">1 - Activa</option>
                                            <option value="0">2 - Deshabilitada</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <p class="text-center" style="margin-top: 40px;">
                        <button type="submit" class="btn btn-raised btn-info btn-sm" id="editarUsuario"><i class="far fa-save"></i> &nbsp; Actualizar</button>
                    </p>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<script src="/static/web/dist/js/funciones/usuario.js"></script>
<%@ include file="includes/footer.jspf" %>
<%@ include file="includes/all-jquery.jspf" %>

</body>
</html>