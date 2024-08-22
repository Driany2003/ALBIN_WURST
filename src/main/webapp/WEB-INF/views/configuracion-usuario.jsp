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
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">USUARIO <span class="label label-rounded label-info">Listado</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <section class="full-box page-content scroll">
                <div class="container-fluid">
                    <ul class="full-box list-unstyled page-nav-tabs text-uppercase">
                        <li>
                            <a class="active" href="http://systems.designlopers.com/SVIL/user-new/">
                                <i class="fas fa-user-tie fa-fw"></i> &nbsp; Nuevo usuario
                            </a>
                        </li>
                        <li>
                            <a href="http://systems.designlopers.com/SVIL/user-list/">
                                <i class="fas fa-clipboard-list fa-fw"></i> &nbsp; Lista de usuarios
                            </a>
                        </li>
                        <li>
                            <a href="http://systems.designlopers.com/SVIL/user-search/">
                                <i class="fas fa-search fa-fw"></i> &nbsp; Buscar usuario
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="container-fluid">
                    <form class="form-neon FormularioAjax" action="#" method="POST" data-form="save" autocomplete="off">
                        <input type="hidden" name="modulo_usuario" value="registrar">
                        <fieldset>
                            <legend><i class="far fa-address-card"></i> &nbsp; Información personal</legend>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group is-filled">
                                            <label for="usuario_tipo_documento" class="bmd-label-floating">Tipo de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <select class="form-control" name="usuario_tipo_documento_reg" id="usuario_tipo_documento">
                                                <option value="" selected="">Seleccione una opción</option>
                                                <option value="DUI">1 - DUI</option><option value="DNI">2 - DNI</option><option value="Cedula">3 - Cedula</option><option value="Licencia">4 - Licencia</option><option value="Pasaporte">5 - Pasaporte</option><option value="Otro">6 - Otro</option>                            </select>
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_numero_documento" class="bmd-label-floating">Numero de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <input type="text" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="usuario_numero_documento_reg" id="usuario_numero_documento" maxlength="30">
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group is-filled">
                                            <label for="usuario_cargo" class="bmd-label-floating">Cargo &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <select class="form-control" name="usuario_cargo_reg" id="usuario_cargo">
                                                <option value="" selected="">Seleccione una opción</option>
                                                <option value="Administrador">1 - Administrador</option>
                                                <option value="Cajero">2 - Cajero</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_nombre" class="bmd-label-floating">Nombres &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="usuario_nombre_reg" id="usuario_nombre" maxlength="35">
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_apellido" class="bmd-label-floating">Apellidos &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <input type="text" pattern="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{4,35}" class="form-control" name="usuario_apellido_reg" id="usuario_apellido" maxlength="35">
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_telefono" class="bmd-label-floating">Teléfono</label>
                                            <input type="text" pattern="[0-9()+]{8,20}" class="form-control" name="usuario_telefono_reg" id="usuario_telefono" maxlength="20">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <br><br><br>
                        <fieldset>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12 col-md-6">
                                        <legend><i class="fas fa-user-friends"></i> &nbsp; Genero</legend>
                                        <div class="form-group bmd-form-group is-filled">
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="usuario_genero_reg" value="Masculino" checked=""><span class="bmd-radio"></span>
                                                    <i class="fas fa-male fa-fw"></i> &nbsp; Masculino
                                                </label>
                                            </div>
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="usuario_genero_reg" value="Femenino"><span class="bmd-radio"></span>
                                                    <i class="fas fa-female fa-fw"></i> &nbsp; Femenino
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6">
                                        <legend><i class="fas fa-barcode"></i> &nbsp; Configuración de lector de código de barras</legend>
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="usuario_lector_reg" value="Habilitado" checked=""><span class="bmd-radio"></span>
                                                                <i class="far fa-check-circle fa-fw"></i> &nbsp; Usar lector
                                                            </label>
                                                        </div>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="usuario_lector_reg" value="Deshabilitado"><span class="bmd-radio"></span>
                                                                <i class="far fa-times-circle fa-fw"></i> &nbsp; No usar lector
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-12 col-md-6">
                                                    <div class="form-group bmd-form-group is-filled">
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="usuario_tipo_codigo_reg" value="Barras" checked=""><span class="bmd-radio"></span>
                                                                <i class="fas fa-barcode fa-fw"></i> &nbsp; Código barras
                                                            </label>
                                                        </div>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="usuario_tipo_codigo_reg" value="SKU"><span class="bmd-radio"></span>
                                                                <i class="fas fa-barcode fa-fw"></i> &nbsp; Código SKU
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <br><br><br>
                        <fieldset>
                            <legend><i class="fas fa-cash-register"></i> &nbsp; Caja de ventas</legend>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="form-group bmd-form-group is-filled">
                                            <select class="form-control" name="usuario_caja_reg">
                                                <option value="" selected="">Seleccione una opción</option>
                                                <option value="4">Caja No.4 (prueba3)</option><option value="5">Caja No.5 (TIENDA prueba)</option><option value="6">Caja No.2 (53334)</option><option value="7">Caja No.3 (prueba1)</option><option value="9">Caja No.6 (prueba4)</option><option value="14">Caja No.50 (5433696)</option><option value="16">Caja No.12 (prueba6)</option><option value="19">Caja No.20 (aaaaaaaaaa)</option><option value="20">Caja No.678 (La ventanita)</option><option value="21">Caja No.134 (324)</option><option value="22">Caja No.12344 (1213)</option><option value="23">Caja No.123 (drago)</option><option value="24">Caja No.100 (prueba9)</option><option value="25">Caja No.13 (prueba7)</option><option value="26">Caja No.10000 (ADMINISTRADOR CAFE)</option><option value="27">Caja No.234 (6789)</option><option value="28">Caja No.122 (CAJA)</option><option value="30">Caja No.2003 (Tecnoparque)</option><option value="31">Caja No.505 (LaBella)</option><option value="33">Caja No.1542 (154212)</option><option value="34">Caja No.77777 (4444)</option><option value="35">Caja No.2509 (LUNES 05 09)</option><option value="37">Caja No.111 (123)</option><option value="40">Caja No.3434 (34343)</option><option value="41">Caja No.24234 (CarlangasChango)</option><option value="42">Caja No.9054 (Milix)</option><option value="43">Caja No.34 (rubenarquitecto)</option><option value="44">Caja No.2020 (caja 20)</option><option value="45">Caja No.48 (ligo)</option><option value="46">Caja No.1111 (211212)</option><option value="47">Caja No.999 (nahhue)</option><option value="49">Caja No.15256 (chapu)</option><option value="50">Caja No.9991 (VILLA)</option><option value="51">Caja No.23464 (Usuario)</option><option value="53">Caja No.1001 (Caja 100)</option><option value="54">Caja No.11111 (caja01)</option><option value="55">Caja No.12312 (dfgdfg)</option><option value="56">Caja No.101 (Caja101)</option><option value="57">Caja No.12345 (santa)</option><option value="58">Caja No.49 (PPPPP)</option><option value="59">Caja No.1000 (1234)</option><option value="60">Caja No.2566 (55669)</option><option value="62">Caja No.412 (osmar)</option><option value="63">Caja No.60 (caja60)</option><option value="64">Caja No.24 (Vicky)</option><option value="65">Caja No.228 (Super)</option><option value="66">Caja No.8920 (Cajero1)</option><option value="67">Caja No.51313 (55rr)</option><option value="69">Caja No.500 (500)</option><option value="70">Caja No.3123 (asdadsadasdg1)</option><option value="71">Caja No.2024 (cajaucho)</option><option value="72">Caja No.8 (caja ventas)</option><option value="73">Caja No.10203 (Gamez)</option><option value="74">Caja No.55555 (Pollo)</option><option value="75">Caja No.65464 (toño)</option><option value="76">Caja No.26 (lucasprueba)</option><option value="77">Caja No.2344 (Caja12)</option><option value="78">Caja No.32423 (qweqe)</option><option value="79">Caja No.258 (Luciano)</option><option value="80">Caja No.70 (ok5)</option><option value="81">Caja No.10008 (Gina)</option><option value="82">Caja No.23324 (4r345)</option><option value="84">Caja No.2349 (ESTELI)</option><option value="85">Caja No.1981 (pakicaja)</option><option value="86">Caja No.12321 (12321)</option><option value="90">Caja No.99999 (eeee)</option><option value="91">Caja No.22 (hhh)</option><option value="92">Caja No.110 (casa grande)</option><option value="93">Caja No.2002 (2002)</option><option value="94">Caja No.527 (MiCaja)</option><option value="95">Caja No.15 (Ferney)</option><option value="96">Caja No.33333 (ASDASDASDASD)</option>                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <br><br><br>
                        <fieldset>
                            <legend><i class="fas fa-user-lock"></i> &nbsp; Información de la cuenta</legend>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12 col-md-6">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_usuario" class="bmd-label-floating">Nombre de usuario &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                            <input type="text" pattern="[a-zA-Z0-9]{4,25}" class="form-control" name="usuario_usuario_reg" id="usuario_usuario" maxlength="25">
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6">
                                        <div class="form-group bmd-form-group">
                                            <label for="usuario_email" class="bmd-label-floating">Email</label>
                                            <input type="email" class="form-control" name="usuario_email_reg" id="usuario_email" maxlength="50">
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
                                                <option value="Activa" selected="">1 - Activa</option>
                                                <option value="Deshabilitada">2 - Deshabilitada</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <br><br><br>
                        <fieldset>
                            <div class="container-fluid">
                                <legend><i class="fas fa-portrait"></i> &nbsp; Avatar</legend>
                                <div class="row">

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Male_2.png" checked=""><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Male_2.png" alt="Avatar_Male_2.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_default_female.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_default_female.png" alt="Avatar_default_female.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Male_4.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Male_4.png" alt="Avatar_Male_4.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Male_1.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Male_1.png" alt="Avatar_Male_1.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Female_2.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Female_2.png" alt="Avatar_Female_2.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_default_male.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_default_male.png" alt="Avatar_default_male.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Female_5.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Female_5.png" alt="Avatar_Female_5.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Female_3.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Female_3.png" alt="Avatar_Female_3.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Female_1.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Female_1.png" alt="Avatar_Female_1.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Male_3.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Male_3.png" alt="Avatar_Male_3.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Female_4.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Female_4.png" alt="Avatar_Female_4.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>

                                    <div class="col-6 col-md-4 col-lg-2">
                                        <span class="bmd-form-group is-filled"><div class="radio radio-avatar-form">
                                            <label>
                                                <input type="radio" name="usuario_avatar_reg" value="Avatar_Male_5.png"><span class="bmd-radio"></span>
                                                <img src="http://systems.designlopers.com/SVIL/vistas/assets/avatar/Avatar_Male_5.png" alt="Avatar_Male_5.png" class="img-fluid img-avatar-form">
                                            </label>
                                        </div></span>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <p class="text-center" style="margin-top: 40px;">
                            <button type="reset" class="btn btn-raised btn-secondary btn-sm"><i class="fas fa-paint-roller"></i> &nbsp; LIMPIAR</button>
                            &nbsp; &nbsp;
                            <button type="submit" class="btn btn-raised btn-info btn-sm"><i class="far fa-save"></i> &nbsp; GUARDAR</button>
                        </p>
                        <p class="text-center">
                            <small>Los campos marcados con &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp; son obligatorios</small>
                        </p>
                    </form>
                </div>
            </section>
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

<%@ include file="includes/footer.jspf" %>
<%@ include file="includes/all-jquery.jspf" %>

</body>
</html>