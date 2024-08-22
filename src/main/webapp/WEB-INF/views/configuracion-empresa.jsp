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
                    <h4 class="page-title">EMPRESA <span class="label label-rounded label-info">${empresaSession.empNombreComercial}</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <!--
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-9">
                            <h1 class="card-title">${empresaSession.empNombreComercial}</h1>
                            <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                        </div>
                        <div class="form-group col-md-3 text-md-right">
                            <label class="font-10">${empresaSession.empDocumentoTipo} : ${empresaSession.empDocumentoNumero}</label><br>
                            <label class="font-10">Vendedor: ${usuSessionNombre}</label>
                            <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                        </div>
                    </div>
                </div>
                -->
                <div class="container">
                    <form class="form-neon form" action="#" method="POST" data-form="update" autocomplete="off">
                        <input type="hidden" name="empresa_id_up" value="">
                        <input type="hidden" name="modulo_empresa" value="actualizar">
                        <br>
                        <fieldset class ="text-left">
                            <legend>
                                <i class="far fa-address-card">
                                </i> &nbsp; Datos de la empresa
                            </legend>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <figure>
                                            <img class="img-fluid img-product-info" src="/static/web/assets/images/0_logo/logo_kenpis_img.png" alt="no se encontro la imagen de la empresa" height="90px" width="90px">
                                        </figure>
                                        <p class="text-center">
                                            <a href="#" class="btn btn-outline-info" data-toggle="popover" data-trigger="hover"
                                               data-placement="top" data-content="Gestionar logo de empresa" data-original-title="" title="">
                                                <i class="far fa-image"></i> &nbsp; Cambiar logo o imagen
                                            </a>
                                        </p>
                                    </div>
                                    <br>
                                    <br>
                                    <div class="col-12 col-md-8">
                                        <div class="row">
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group is-filled">
                                                    <label for="empresa_tipo_documento" class="bmd-label-floating">Tipo de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                    <input type="text" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="empresa_numero_documento_up" value="RUC"
                                                           id="empresa_tipo_documento" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group is-filled">
                                                    <label for="empresa_numero_documento" class="bmd-label-floating">Numero de documento &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                    <input type="text" pattern="[a-zA-Z0-9-]{7,30}" class="form-control" name="empresa_numero_documento_up" value="155600705-2-2015-2015-469587"
                                                           id="empresa_numero_documento" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group is-filled">
                                                    <label for="empresa_nombre" class="bmd-label-floating">Nombre &nbsp; <i class="fab fa-font-awesome-alt"></i> &nbsp;</label>
                                                    <input type="text" pattern="[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ., ]{4,75}" class="form-control" name="empresa_nombre_up" value="GO CLEANING SERVICES S.A." id="empresa_nombre"
                                                           maxlength="75">
                                                </div>
                                            </div>
                                            <div class="col-12 col-md-6">
                                                <div class="form-group bmd-form-group is-filled">
                                                    <label for="empresa_direccion" class="bmd-label-floating">Dirección</label>
                                                    <input type="text" pattern="[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ().,#\- ]{4,97}" class="form-control" name="empresa_direccion_up"
                                                           value="Panamá Pueblo Nuevo CL. Fernández de Córdoba Plaza Vista Hermosa local 2" id="empresa_direccion" maxlength="97">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <br><br><br>
                        <fieldset class ="text-left">
                            <legend>
                                <i class="fas fa-phone-volume">
                                </i> &nbsp; Información de contacto
                            </legend>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-12 col-md-6">
                                        <div class="form-group bmd-form-group is-filled">
                                            <label for="empresa_telefono" class="bmd-label-floating">Teléfono</label>
                                            <input type="text" pattern="[0-9()+]{8,20}" class="form-control" name="empresa_telefono_up" value="+5073687217" id="empresa_telefono" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6">
                                        <div class="form-group bmd-form-group is-filled">
                                            <label for="empresa_email" class="bmd-label-floating">Email</label>
                                            <input type="email" class="form-control" name="empresa_email_up" value="insumosgoservices@go-cleaning.com" id="empresa_email" maxlength="50">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <p class="text-center" style="margin-top: 40px;">
                            <button type="submit" class="btn btn-raised btn-success btn-sm"><i class="fas fa-sync"></i> &nbsp; ACTUALIZAR</button>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

    /* From Uiverse.io by mi-series */
    .container {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        text-align: center;
    }
    .btn {
        padding: 15px;
        margin: 25px 0px;
        width: 290px;
        font-size: 15px;
        background: #DE5499;
        border-radius: 10px;
        font-weight: 800;
        box-shadow: 3px 3px 0px 0px #E99F4C;
    }

    .btn:hover {
        opacity: .9;
    }
    .card {
        margin-left: auto;
        margin-right: auto;
        max-width: 1200px;
        top: 10px;
    }

    .form-neon .form-group {
        text-align: left;
    }

    @media (max-width: 768px) {
        .card {
            max-width: 100%;
            margin: 0;
        }
    }
</style>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

<%@ include file="includes/footer.jspf" %>
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>