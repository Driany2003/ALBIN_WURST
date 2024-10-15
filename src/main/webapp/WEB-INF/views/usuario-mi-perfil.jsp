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
        <div class="container mt-4 mb-4">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title text-center">Tu número de cliente: <span style="color: #007bff;">904329</span></h5>
                            <div class="col-md-12 text-center image-wrapper">
                                <canvas id="initialsCanvas" class="rounded-circle" width="300" height="300" style="width: 150px; height: 150px; background-color: #f0f0f0;"></canvas>
                                <!--
                                <div id="buttonContainer">
                                    <a href="#" class="btn btn-link text-primary" style="margin: 2px 0;"><i class="fas fa-pencil-alt"></i> Modificar</a>
                                    <a href="#" class="btn btn-link text-danger" style="margin: 2px 0;"><i class="fas fa-trash"></i> Eliminar</a>
                                </div>
                                -->
                            </div>
                            <br>
                            <form id="ActualizarPerfilForm">
                                <input type="hidden" id="usuId" value=${usuario.usuId}>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="nombre">Nombre</label>
                                        <input type="text" class="form-control" id="nombre" value=${usuario.usuNombre} data-valid="false" style="text-transform: uppercase;">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="apellidoPaterno">Apellido Paterno</label>
                                        <input type="text" class="form-control" id="apellidoPaterno" value=${usuario.usuApePaterno} data-valid="false" style="text-transform: uppercase;">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="apellidoMaterno">Apellido Materno</label>
                                        <input type="text" class="form-control" id="apellidoMaterno" value=${usuario.usuApeMaterno} data-valid="false" style="text-transform: uppercase;">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="telefono">Teléfono</label>
                                        <input type="text" class="form-control" id="telefono" value=${usuario.usuTelefono} data-valid="false">
                                        <div id="telefonoError" style="display:none; color: red;">El número de teléfono debe tener 9 dígitos.</div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="correoUsuario">Correo Electrónico</label>
                                        <input type="text" class="form-control" id="correoUsuario" value=${usuario.usuCorreo} data-valid="false">
                                        <div id="correoError" class="text-danger" style="display: none;">Por favor, ingresa un correo electrónico válido.</div>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="tipoDocumento">Tipo Documento</label>
                                        <input type="text" class="form-control" id="tipoDocumento" disabled value=${usuario.usuTipoDocumento} data-valid="true">
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="numeroDocumento">Número Documento</label>
                                        <input type="text" class="form-control" id="numeroDocumento" disabled value=${usuario.usuNumeroDocumento} data-valid="true">
                                    </div>
                                </div>
                                <br>
                                <div class="text-center">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                    <button type="button" id="actualizarPerfil" class="btn btn-primary" disabled>Actualizar perfil</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="includes/footer.jspf" %>
    </div>
</div>
<style>
    .image-wrapper {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    #buttonContainer {
        display: none;
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        display: flex;
        background: rgba(255, 255, 255, 0.8);
        padding: 5px;
        border-radius: 5px;
        justify-content: center;
        flex-direction: column;
        align-items: center;
    }
</style>
<!--
<script>

    const imageContainer = document.querySelector('.image-wrapper');

    imageContainer.addEventListener('mouseover', function () {
        document.getElementById("buttonContainer").style.display = "flex";
    });

    imageContainer.addEventListener('mouseout', function () {
        document.getElementById("buttonContainer").style.display = "none";
    })

</script>
-->

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


<script src="/static/web/dist/js/funciones/mi-perfil.js"></script>
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>